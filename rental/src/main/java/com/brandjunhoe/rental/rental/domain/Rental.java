package com.brandjunhoe.rental.rental.domain;

import com.brandjunhoe.rental.common.doamin.BaseDateEntity;
import com.brandjunhoe.rental.rental.application.RentUnavailableException;
import com.brandjunhoe.rental.rental.domain.enums.RentalStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Create by DJH on 2021/09/28.
 */

@Entity
@Table(name = "rental")
@Where(clause = "deleted_at IS NULL")
@NoArgsConstructor
@Getter
public class Rental extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rental_status")
    private RentalStatus rentalStatus;

    @Column(name = "late_fee")
    private int lateFee;

    private LocalDateTime deletedAt;

    //고아 객체 제거 -> rental에서 컬렉션의 객체 삭제시, 해당 컬렉션의 entity삭제
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RentedItem> rentedItems = new HashSet<>();

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OverdueItem> overdueItems = new HashSet<>();

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReturnedItem> returnedItems = new HashSet<>();


    @Builder
    public Rental(Long userId, RentalStatus rentalStatus, int lateFee) {
        this.userId = userId;
        this.rentalStatus = rentalStatus;
        this.lateFee = lateFee;
    }

    public Rental(Long id) {
        this.id = id;
    }

    /*public Rental Rental(Long userId) {
        return createRental(userId);
    }*/

    public void update(Long userId, RentalStatus rentalStatus, int lateFee) {
        this.userId = userId;
        this.rentalStatus = rentalStatus;
        this.lateFee = lateFee;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }


    /*public Rental createRental(Long userId) {
        return Rental.builder()
                .userId(userId)
                .rentalStatus(RentalStatus.RENT_AVAILABLE)
                .lateFee(0)
                .build();

    }*/

    // 대출 가능 여부 체크
    public void checkRentalAvailable() {
        if (this.rentalStatus.equals(RentalStatus.RENT_UNAVAILABLE) || this.lateFee != 0)
            throw new RentUnavailableException("연체 상태입니다. 연체료를 정산 후, 도서를 대출하실 수 있습니다.");
        if (this.rentedItems.size() >= 5)
            throw new RentUnavailableException("대출 가능한 도서의 수는 " + (5 - this.rentedItems.size()) + "권 입니다.");
    }

    public void rentBook(Long bookId, String bookTitle) {
        this.addRentedItem(RentedItem.builder()
                .bookId(bookId)
                .bookTitle(bookTitle)
                .rentedDate(LocalDate.now())
                .build());
    }


    public void returnBook(Long bookId) {
        RentedItem rentedItem = this.rentedItems.stream().filter(item -> item.getBookId().equals(bookId)).findFirst().get();
        this.addReturnedItem(ReturnedItem.builder()
                .bookId(rentedItem.getBookId())
                .bookTitle(rentedItem.getBookTitle())
                .returnedDate(LocalDate.now())
                .build());

        this.removeRentedItem(rentedItem);
    }

    public void addReturnedItem(ReturnedItem returnedItem) {
        this.returnedItems.add(returnedItem);
    }

    public void removeReturnedItem(ReturnedItem returnedItem) {
        this.returnedItems.remove(returnedItem);
    }


    public void addRentedItem(RentedItem rentedItem) {
        this.rentedItems.add(rentedItem);
    }

    public void removeRentedItem(RentedItem rentedItem) {
        this.rentedItems.remove(rentedItem);
    }

    // 연체처리
    public void overdueBook(Long bookId) {
        RentedItem rentedItem = this.rentedItems.stream().filter(item -> item.getBookId().equals(bookId)).findFirst().get();
        this.addOverdueItem(OverdueItem.builder()
                .bookId(rentedItem.getBookId())
                .bookTitle(rentedItem.getBookTitle())
                .dueDate(rentedItem.getDueDate()).build());
        this.removeRentedItem(rentedItem);
    }

    // 대출 불가 처리
    public void makeRentUnable() {
        this.rentalStatus = RentalStatus.RENT_UNAVAILABLE;
        this.lateFee = this.lateFee + 30; //연체시 연체비 30포인트 누적
    }

    public void addOverdueItem(OverdueItem overdueItem) {
        this.overdueItems.add(overdueItem);
    }

    public void removeOverdueItem(OverdueItem overdueItem) {
        this.overdueItems.remove(overdueItem);
    }

    // 연체된 책 반납
    public void returnOverdueBook(Long bookId) {
        OverdueItem overdueItem = this.overdueItems
                .stream().filter(item -> item.getBookId().equals(bookId)).findFirst().get();
        this.addReturnedItem(ReturnedItem.builder()
                .bookId(overdueItem.getBookId())
                .bookTitle(overdueItem.getBookTitle())
                .returnedDate(LocalDate.now())
                .build());

        this.removeOverdueItem(overdueItem);
    }

    // 대출불가 해제
    public void releaseOverdue() {
        this.lateFee = 0;
        this.rentalStatus = RentalStatus.RENT_AVAILABLE;
    }

}

package com.brandjunhoe.user.user.domain;

import com.brandjunhoe.user.common.doamin.BaseDateEntity;
import com.brandjunhoe.user.config.Constants;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(length = 254, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean activated = false;

    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Column(name = "activation_key", length = 20)
    private String activationKey;

    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @Column(name = "point")
    private int point;

    @ManyToMany
    @JoinTable(name = "authority", // 조인 테이블명
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, // 현재 엔티티를 참조하는 외래키
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    // 반대 방향 엔티티를 참조하는 외래키
    public Set<Authority> authorities = new HashSet<>();

    public void savePoints(int point) {
        this.point += point;
    }

    public void userPoints(int point) {
        if (this.point >= point) {
            this.point -= point;
        } else {
            throw new UsePointsUnavailableException();
        }
    }

}
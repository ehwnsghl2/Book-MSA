package com.brandjunhoe.auth.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Create by DJH on 2021/10/26.
 */
@Entity
@Table(name = "T_USER")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", length = 20, unique = true, nullable = false)
    private String username;

    @Column(length = 400, nullable = false)
    private String password;

    @Column(name = "user_type", nullable = false)
    private int userType;

    @Column(nullable = false)
    private Date date;


    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.userType = 0;
        this.date = new Date();
    }

}

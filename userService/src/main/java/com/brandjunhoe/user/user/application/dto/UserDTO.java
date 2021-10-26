package com.brandjunhoe.user.user.application.dto;

import com.brandjunhoe.user.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
public class UserDTO {

    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activated = false;

    private String langKey;

    private String imageUrl;

    private String activationKey;

    private String resetKey;

    private Instant resetDate = null;

    private int point;

    public UserDTO(User source) {
        copyProperties(source, this);
    }

    public void updateActivated() {
        this.activated = true;
        this.activationKey = null;
    }

    public void changePassword(String password) {
        this.password = password;
        this.resetKey = null;
        this.resetDate = null;
    }

    public void resetKey() {
        this.resetKey = "randomUtil.generateResetKey";
        this.resetDate = Instant.now();
    }

}
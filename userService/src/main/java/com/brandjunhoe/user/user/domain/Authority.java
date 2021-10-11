package com.brandjunhoe.user.user.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Authority {

    @Id
    @Column(length = 50)
    private String name;

}

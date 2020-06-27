package com.my.springbootshiro.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String permission;
    private String url;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

}

package com.my.springbootshiro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String password;

    @JsonIgnoreProperties("user")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Role> roles;
}

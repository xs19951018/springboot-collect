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
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String roleName;

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    private User user;

    @JsonIgnoreProperties("role")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.EAGER)
    private List<Permission> permissions;
}

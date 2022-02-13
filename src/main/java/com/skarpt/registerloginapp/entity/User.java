package com.skarpt.registerloginapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.intellij.lang.annotations.Pattern;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String fullName;


    private String email;

    private String password;

    private Long phoneNumber;

    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateOfBirth;

    @ManyToMany
    private Collection<Role> roles = new ArrayList<>();

}

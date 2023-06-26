package com.ufps.practicalogin.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name="users_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="users_id_seq")
    private Integer id;

    @Column(name="name", nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 500)
    private String pass;

    @Column(nullable = false, length = 100)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bill> bills;
}

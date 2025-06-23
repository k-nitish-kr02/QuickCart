package com.E_Commerce.eCom.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
    uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
    }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(min =8 , max = 100)
    private String password;

    @NotBlank
    @Email
    private String email;

    //Unidirectional Relationship
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    //Bidirectional Relationship
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller",cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    //Unidirectional Relationship
//    @Getter
//    @Setter
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    //Bidirectional Relationship
    @ToString.Exclude
    @OneToOne(mappedBy = "user",fetch = FetchType.EAGER,
    cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    private Cart cart;


}

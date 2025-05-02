package com.example.passenger.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data  // will generate getters, setters, toString, equals, and hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;
    private String fullName;
    
}

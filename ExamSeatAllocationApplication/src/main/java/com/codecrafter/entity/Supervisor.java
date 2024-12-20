package com.codecrafter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "supervisor")
public class Supervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supervisorId;
    private String superVisorName;
    private String contactNumber;
    private String email;
    private String departName;
    private boolean isBlockAllocated;

    @OneToOne
    @JoinColumn(name = "block_id", nullable = true)
    private Block block;
}

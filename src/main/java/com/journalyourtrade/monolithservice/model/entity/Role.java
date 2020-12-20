package com.journalyourtrade.monolithservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<JYTUser> jytUsers = new ArrayList<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }
}

package com.journalyourtrade.monolithservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "sys_config")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "active")
    private Boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    private ServiceInfo serviceInfo;
}

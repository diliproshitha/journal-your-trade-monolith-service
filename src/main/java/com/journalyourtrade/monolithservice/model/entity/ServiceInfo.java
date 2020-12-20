package com.journalyourtrade.monolithservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "service_info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInfo {

    public static final int SERVICE_COMMON = 1;
    public static final int SERVICE_USER_SERVICE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "service_name", nullable = false, updatable = false)
    private String serviceName;
}

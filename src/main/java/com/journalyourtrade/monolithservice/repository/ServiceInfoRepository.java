package com.journalyourtrade.monolithservice.repository;

import com.journalyourtrade.monolithservice.model.entity.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, String> {
}

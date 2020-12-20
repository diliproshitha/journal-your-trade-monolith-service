package com.journalyourtrade.monolithservice.repository;

import com.journalyourtrade.monolithservice.model.entity.SysConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysConfigRepository extends JpaRepository<SysConfig, String> {

    @Query(value = "select * from sys_config where active=true and service_id in (:ids)", nativeQuery = true)
    List<SysConfig> findAllActiveConfigs(@Param("ids") List<Integer> ids);
}

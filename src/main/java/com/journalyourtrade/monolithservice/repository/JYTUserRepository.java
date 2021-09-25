package com.journalyourtrade.monolithservice.repository;

import com.journalyourtrade.monolithservice.model.entity.JYTUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JYTUserRepository extends JpaRepository<JYTUser, Integer> {

    Optional<JYTUser> findByEmail(String email);

    void deleteByEmail(String email);
}

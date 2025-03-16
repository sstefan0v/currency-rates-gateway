package com.sstefanov.currency.rates.gateway.repositories;

import com.sstefanov.currency.rates.gateway.entities.RequestInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RequestInfoRepository extends JpaRepository<RequestInfo, Long> {
    @Query("SELECT r.requestId FROM RequestInfo r WHERE r.requestId = :requestId")
    Optional<String> findRequestId(String requestId);
}
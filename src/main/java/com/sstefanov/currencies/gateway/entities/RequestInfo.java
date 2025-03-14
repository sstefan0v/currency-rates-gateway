package com.sstefanov.currencies.gateway.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "request_statistics")
@Data
public class RequestInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "request_id", unique = true, nullable = false)
    private String requestId;

    @Column(nullable = false)
    private Long timestamp;

    @Column(name = "client_id", nullable = false)
    private String clientId;
}

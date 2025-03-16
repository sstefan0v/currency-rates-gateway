CREATE TABLE request_statistics
(
    id           SERIAL PRIMARY KEY,
    service_name VARCHAR(20)        NOT NULL,
    request_id   VARCHAR(40) UNIQUE NOT NULL,
    timestamp    BIGINT             NOT NULL,
    client_id    VARCHAR(25)        NOT NULL
);

CREATE TABLE currency_rates
(
    timestamp     BIGINT          NOT NULL,
    base_currency CHAR(3)         NOT NULL,
    currency      CHAR(3)         NOT NULL,
    rate          NUMERIC(20, 10) NOT NULL,
    PRIMARY KEY (timestamp, base_currency, currency)
) PARTITION BY RANGE (timestamp);

CREATE TABLE currency_rates_2025 PARTITION OF currency_rates
    FOR VALUES FROM 1735682400000 TO 1767218399000;

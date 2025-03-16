# currency-rates-gateway

This service provides info about currency rates by specified base currency. It has two rest APIs: 1 for json requests 
and 2 for xml requests.

To build:
```
mvn install
```
This will run docker and build
- postgres db
- redis
- rabbitMq

This service works together with https://github.com/sstefan0v/currency-rates-collector
# BidFlow

An online auction marketplace.

## What It Does

Users can create auctions, list items for sale, place bids, upload item media, and complete purchases. The system is built to handle real-world engineering concerns: concurrency, consistency, performance, observability, and failure.

## Core Domain Concepts

- **User** — a registered participant who can create auctions or place bids
- **Auction** — a time-boxed sale event created by a user for a specific item
- **Item** — the thing being sold, with a description and optional media
- **Bid** — an offer placed by a user on an active auction
- **Order** — created when an auction closes and a winner is determined
- **Payment** — the settlement of an order

## Business Rules

- An auction has a start time and an end time
- Bids can only be placed on active auctions
- A bid must exceed the current highest bid
- A user cannot bid on their own auction
- When an auction closes, the highest bid wins
- An order is created automatically when an auction closes

## Tech Stack

Kotlin, Spring Boot, PostgreSQL, Redis, Kafka, Cassandra, MinIO, Docker, Kubernetes, GitHub Actions, OpenTelemetry, Prometheus, Grafana

## Phases

1. **Foundation** — REST API, PostgreSQL, Docker
2. **Caching** — Redis
3. **Events** — Kafka, outbox pattern
4. **Bid History** — Cassandra
5. **Media** — object storage (MinIO)
6. **Auth** — JWT, RSA key signing
7. **CDN / Rate Limiting** — Fastly simulation
8. **Kubernetes** — local cluster deployment
9. **CI/CD** — GitHub Actions pipeline
10. **Observability** — traces, metrics, logs
11. **Performance** — load testing, tuning

---

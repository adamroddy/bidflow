package com.roddy.bidflow.bid.domain

import org.springframework.data.cassandra.core.cql.Ordering
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID


@Table(value = "bid_history")
class Bid(
    @PrimaryKeyColumn(name = "auction_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    val auctionId: UUID,

    @PrimaryKeyColumn(name = "timestamp", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING, ordinal = 1)
    val timestamp: Instant,

    @PrimaryKeyColumn(name = "bid_id", type = PrimaryKeyType.CLUSTERED, ordinal = 2)
    val id: UUID,

    @Column("user_id")
    val userId: UUID,

    @Column("amount")
    val amount: BigDecimal
)



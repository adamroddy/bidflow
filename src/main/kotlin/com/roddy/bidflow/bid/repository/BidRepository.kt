package com.roddy.bidflow.bid.repository

import com.roddy.bidflow.bid.domain.Bid
import org.springframework.data.cassandra.repository.CassandraRepository
import java.util.UUID

interface BidRepository : CassandraRepository<Bid, UUID> {

    fun findByAuctionId(auctionId: UUID): List<Bid>
}
package com.roddy.bidflow.auction.repository

import com.roddy.bidflow.auction.domain.Auction
import com.roddy.bidflow.auction.domain.AuctionState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AuctionRepository : JpaRepository<Auction, UUID> {

    fun findByState(state: AuctionState): List<Auction>

    fun findByUserId(userId: UUID): List<Auction>

    fun findByItemId(itemId: UUID): Auction?
}

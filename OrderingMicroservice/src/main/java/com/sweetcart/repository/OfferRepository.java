package com.sweetcart.repository;

import com.sweetcart.entity.Offer;
import com.sweetcart.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
}

package com.sweetcart.sweetcart.repository;

import com.sweetcart.sweetcart.entity.CakeShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeShopRepository extends JpaRepository<CakeShop,Long> {
}
package com.sweetcart.demo.repository;

import com.sweetcart.demo.entity.CakeShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeShopRepository extends JpaRepository<CakeShop,Long>{
}

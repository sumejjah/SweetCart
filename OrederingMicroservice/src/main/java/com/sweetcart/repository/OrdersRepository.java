package com.sweetcart.repository;

import com.sweetcart.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sumejja on 23.03.2018..
 */

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>{
}

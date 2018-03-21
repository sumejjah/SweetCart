package com.sweetcart.repository;

import com.sweetcart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Sumejja on 20.03.2018..
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
}

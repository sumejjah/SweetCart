package com.sweetcart.repository;

import com.sweetcart.entity.Orders;
import com.sweetcart.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Long> {
}

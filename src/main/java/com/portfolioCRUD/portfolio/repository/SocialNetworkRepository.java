package com.portfolioCRUD.portfolio.repository;

import com.portfolioCRUD.portfolio.model.SocialNetwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Long> {

        public List<SocialNetwork> findAllByOrderByPositionAsc();

}

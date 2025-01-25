package com.example.easytable.repository.restaurant;

import com.example.easytable.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>, RestaurantRepositoryCustom {
}
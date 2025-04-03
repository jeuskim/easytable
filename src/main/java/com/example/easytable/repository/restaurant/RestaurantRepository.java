package com.example.easytable.repository.restaurant;

import com.example.easytable.entity.Restaurant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositoryCustom {

    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:name%")
    Slice<Restaurant> findByNameWithPaging(@Param("name") String name, Pageable pageable);


}
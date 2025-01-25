package com.example.easytable.repository.review;

import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>,ReviewRepositoryCustom {

    @Query("select r from Review r join fetch r.user where r.restaurant = :restaurant")
    List<Review> getReviews(@Param("restaurant") Restaurant restaurant);
}

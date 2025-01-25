package com.example.easytable.repository.review;

import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepositoryCustom {

    Double getAverageRating(Integer restaurantId);

}

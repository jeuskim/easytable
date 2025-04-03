package com.example.easytable.service;

import com.example.easytable.dto.service.request.RestaurantModifyParam;
import com.example.easytable.dto.service.request.RestaurantRegisterParam;
import com.example.easytable.entity.Restaurant;
import com.example.easytable.entity.user.User;
import com.example.easytable.entity.user.UserType;
import com.example.easytable.exception.RestaurantNotFoundException;
import com.example.easytable.exception.UnauthorizedException;
import com.example.easytable.repository.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public void registerRestaurant(User user, RestaurantRegisterParam request) {

        if (user.getUserType() != UserType.MANAGER) {
            throw new UnauthorizedException();
        }

        restaurantRepository.save(
                Restaurant.builder()
                        .name(request.getName())
                        .location(request.getLocation())
                        .cuisineType(request.getCuisineType())
                        .manager(user)
                        .openingHours(request.getOpeningHours())
                        .closingHours(request.getClosingHours())
                        .description(request.getDescription())
                        .build()

        );

    }

    public void modifyRestaurant(User user, Long restaurantId, RestaurantModifyParam request) {

        log.info("restaurantId = {}", restaurantId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);

        log.info("restaurant.mangerId ={}", restaurant.getManager().getId());
        log.info("user.id = {}", user.getId());

        if (!restaurant.getManager().getId().equals(user.getId())) {
            throw new UnauthorizedException();
        }


        restaurant.modify(request);


    }

    public Slice<Restaurant> getRestaurants(String name, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 5);

        return restaurantRepository.findByNameWithPaging(name, pageRequest);
    }

    public Double getRating(Long restaurantId) {
        return restaurantRepository.getRating(restaurantId);
    }


}

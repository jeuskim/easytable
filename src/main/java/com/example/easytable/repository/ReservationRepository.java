package com.example.easytable.repository;

import com.example.easytable.entity.Reservation;
import com.example.easytable.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    @EntityGraph(attributePaths = {"user", "restaurant"})
    @Query("select r from Reservation r where r.reservationId = :reservationId")
    Optional<Reservation> findWithUserAndRestaurantById(@Param("reservationId") int reservationId);

    @Query("select r from Reservation r join fetch r.restaurant where r.user =: user ")
    List<Reservation> findAllByUser(User user);
}

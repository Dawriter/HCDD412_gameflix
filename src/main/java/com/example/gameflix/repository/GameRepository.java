package com.example.gameflix.repository;

import com.example.gameflix.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
    @Override
    Optional<Game> findById(Long sid);
}

package com.example.gameflix.service;

import com.example.gameflix.model.Game;
import org.springframework.data.domain.Page;
import java.util.List;

public interface GameService {
    List<Game> getAllGames();
    void saveGame(Game game);
    Game getGameById(Long id);
    void deleteGameById(Long id);
    Page<Game> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}

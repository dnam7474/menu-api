package com.simple.menu_api.repository;

import com.simple.menu_api.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    List<Film> findByTitleContainingIgnoreCase(String title);
    List<Film> findByOrderByPopularityDesc();
    List<Film> findByOrderByVoteAverageDesc();
}

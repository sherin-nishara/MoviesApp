package com.example.demo.repository;

import com.example.demo.entity.Genre;
import com.example.demo.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

    List<MovieEntity> findByGenre(Genre genre);
    List<MovieEntity> findByRatingGreaterThanEqual(Double rating);
    List<MovieEntity> findByGenreAndRatingGreaterThanEqual(Genre genre, Double rating);
}

package com.example.demo.services;

import com.example.demo.entity.Genre;
import com.example.demo.entity.MovieEntity;
import com.example.demo.repository.MovieRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final List<MovieEntity> recentlyViewed = new ArrayList<>();

    public MovieEntity addMovie(MovieEntity movies) {
        return movieRepository.save(movies);
    }

    public MovieEntity updateMovie(Long id, MovieEntity movies) {
        MovieEntity existingMovie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie Not Found ID: " + id));
        MovieEntity movie = MovieEntity.builder()
                .id(existingMovie.getId())
                .name(pick(movies.getName(), existingMovie.getName()))
                .genre(pick(movies.getGenre(), existingMovie.getGenre()))
                .rating(pick(movies.getRating(), existingMovie.getRating()))
                .build();
        return movieRepository.save(movie);
    }

    private <T> T pick(T newVal, T oldVal){
        return newVal != null ? newVal : oldVal;
    }

    public List<MovieEntity> getAllMovie(Genre genre, Double rating) {
        List<MovieEntity> movies;

        if (genre != null && rating != null) {
            movies = movieRepository.findByGenreAndRatingGreaterThanEqual(genre, rating);
        } else if (genre != null) {
            movies = movieRepository.findByGenre(genre);
        } else if (rating != null) {
            movies = movieRepository.findByRatingGreaterThanEqual(rating);
        } else {
            movies = movieRepository.findAll();
        }

        for (MovieEntity movie : movies) {
            recentlyViewed.add(movie);
            while (recentlyViewed.size() > 5) {
                recentlyViewed.remove(0); // remove oldest
            }
        }
        return movies;
    }

    public List<MovieEntity> getRecentMovies() {
        return recentlyViewed;
    }

    public List<MovieEntity> getByFirstLetter(Character letter){
        List<MovieEntity> movies = movieRepository.findAll();
        Map<Character, List<MovieEntity>> map = new HashMap<>();
        for(MovieEntity m : movies){
            char first = Character.toUpperCase(m.getName().charAt(0));

            if(map.containsKey(first)){
                map.get(first).add(m);
            }else {
                List<MovieEntity> list = new ArrayList<>();
                list.add(m);
                map.put(first, list);
            }
        }
        if(map.containsKey(letter)){
            return map.get(letter);
        }
        return new ArrayList<>();
    }

    public MovieEntity getById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie Not Found ID: "+ id));
    }

    public String deleteMovie(Long id) {
        movieRepository.deleteById(id);
        return "ID "+ id + " Deleted Successfully!";
    }
}

package com.example.demo.controller;

import com.example.demo.entity.Genre;
import com.example.demo.entity.MovieEntity;
import com.example.demo.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieEntity> addMovie(@RequestBody MovieEntity movies){
        return ResponseEntity.ok(movieService.addMovie(movies));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieEntity> updateMovie(@PathVariable Long id, @RequestBody MovieEntity movies){
        return ResponseEntity.ok(movieService.updateMovie(id, movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(movieService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieEntity>> getAllMovies(
            @RequestParam(required = false) Genre genre,
            @RequestParam(required = false) Double rating
    ){
        return ResponseEntity.ok(movieService.getAllMovie(genre, rating));
    }
    
    @GetMapping("/search/{letter}")
    public ResponseEntity<List<MovieEntity>> getByFirstLetter(@PathVariable char letter){
        return ResponseEntity.ok(movieService.getByFirstLetter(letter));
    }

    @GetMapping("/recent")
    public List<MovieEntity> getRecentMovies(){
        return movieService.getRecentMovies();
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id){
        return movieService.deleteMovie(id);
    }
}

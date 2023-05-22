package com.gdu.movie.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.movie.domain.QueryDTO;
import com.gdu.movie.service.MovieService;

@Controller
public class MovieController {

  @Autowired
  private MovieService movieService;
  
  @GetMapping(value="/searchAllMovies", produces="application/json")
  @ResponseBody
  public Map<String, Object> searchAllMovies(){
    return movieService.getAllMovies();
  }
  
  @GetMapping(value="/searchMovie", produces="application/json")
  @ResponseBody
  public Map<String, Object> searchMovie(QueryDTO queryDTO){
    return movieService.getMoviesByQuery(queryDTO);
  }
  
}

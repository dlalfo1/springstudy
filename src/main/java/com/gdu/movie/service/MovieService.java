package com.gdu.movie.service;

import java.util.Map;

import com.gdu.movie.domain.QueryDTO;

public interface MovieService {
  public Map<String, Object> getAllMovies();
  public Map<String, Object> getMoviesByQuery(QueryDTO queryDTO);
}

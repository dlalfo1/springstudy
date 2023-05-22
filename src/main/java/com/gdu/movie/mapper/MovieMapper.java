package com.gdu.movie.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.movie.domain.MovieDTO;
import com.gdu.movie.domain.QueryDTO;

@Mapper
public interface MovieMapper {
  public List<MovieDTO> getAllMovies();
  public List<MovieDTO> getMoviesByQuery(QueryDTO queryDTO);
}

package com.gdu.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gdu.movie.domain.MovieDTO;
import com.gdu.movie.domain.QueryDTO;
import com.gdu.movie.mapper.MovieMapper;
import com.gdu.movie.util.SecurityUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

  private MovieMapper movieMapper;
  private SecurityUtil securityUtil;
  
  @Override
  public Map<String, Object> getAllMovies() {
    
    List<MovieDTO> list = movieMapper.getAllMovies();
    int status = 0;
    String message = null;
    if(list.isEmpty()) {
      status = 500;
      message = "목록이 없습니다.";
    } else {
      status = 200;
      message = "전체 " + list.size() + "개의 목록을 가져왔습니다.";
    }
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("list", list);
    map.put("message", message);
    map.put("status", status);
    
    return map;
    
  }

  @Override
  public Map<String, Object> getMoviesByQuery(QueryDTO queryDTO) {
    
    queryDTO.setSearchText( securityUtil.preventXSS( queryDTO.getSearchText() ) );
    
    List<MovieDTO> list = movieMapper.getMoviesByQuery(queryDTO);
    int status = 0;
    String message = null;
    
    if(list.isEmpty()) {
      status = 500;
      message = queryDTO.getSearchText() + " 검색 결과가 없습니다.";
    } else {
      status = 200;
      message = list.size() + "개의 검색 결과가 있습니다.";
    }
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("list", list);
    map.put("message", message);
    map.put("status", status);
    
    return map;
    
  }

}
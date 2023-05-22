package com.gdu.movie.batch;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.movie.domain.MovieDTO;
import com.gdu.movie.domain.QueryDTO;
import com.gdu.movie.mapper.MovieMapper;

@EnableScheduling
@Component
public class FindComedyScheduler {

	@Autowired
	private MovieMapper movieMapper;
	
	@Scheduled(cron = "0 0/1 * * * *")
	public void execute() throws Exception {
		
		QueryDTO queryDTO = new QueryDTO();
		queryDTO.setColumn("GENRE");
		queryDTO.setSearchText("코미디");
		
		List<MovieDTO> movies = movieMapper.getMoviesByQuery(queryDTO);
		
		if(movies.isEmpty()) {
			try (PrintWriter out = new PrintWriter(new FileWriter("error.txt"))) {
				out.println("코미디 검색 결과가 없습니다.");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try (PrintWriter out = new PrintWriter(new FileWriter("코미디.txt"))) {
				for (MovieDTO movie : movies) {
					out.println("제목 : " + movie.getTitle());
					out.println("장르 : " + movie.getGenre());
					out.println("개요 : " + movie.getDescription());
					out.println("평점 : " + movie.getStar());
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}

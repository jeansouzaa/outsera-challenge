package com.outsera.csvreader.loader;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.outsera.csvreader.CsvReaderApplication;
import com.outsera.csvreader.entity.Movie;
import com.outsera.csvreader.repository.MovieRepository;
import com.outsera.csvreader.service.impl.MovieService;

@Component
public class MovieLoader {

	private MovieRepository movieRepository;

	private MovieService movieService;

	@Value("${csv.file.path:}")
	private String csvPath;

	public MovieLoader(MovieRepository movieRepository, MovieService movieService) {
		this.movieRepository =  movieRepository;
		this.movieService = movieService;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadDataOnStartup() {
		try {
			List<Movie> movies = movieService.readLineCSV(resolveCsvPath());
			if(movies != null && !movies.isEmpty()) {
				movieRepository.saveAll(movies);
			}
			
		}catch(Exception exception) {
			System.out.println(movieService.getMessage(exception));
			exception.printStackTrace();
		}
	}

	private String resolveCsvPath() {
	    return (csvPath == null || csvPath.isBlank()) ? "src/main/resources/movielist.csv" : csvPath;
	}
}

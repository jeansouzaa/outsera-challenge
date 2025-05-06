package com.outsera.csvreader.loader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.outsera.csvreader.entity.Movie;
import com.outsera.csvreader.repository.MovieRepository;
import com.outsera.csvreader.service.impl.MovieService;

@Component
public class MovieLoader implements CommandLineRunner{

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieService service;

	@Value("${csv.file.path:}")
	private String csvPath;

	@Override
	public void run(String... args) throws Exception {
		try {
			List<Movie> movies = service.readLineCSV(resolveCsvPath());
			if(movies != null && !movies.isEmpty()) {
				for(Movie movie : movies) {
					movieRepository.save(movie);
				}
			}
			
		}catch(Exception exception) {
			System.out.println(service.getMessage(exception));
			exception.printStackTrace();
		}
	}

	private String resolveCsvPath() {
	    return (csvPath == null || csvPath.isBlank()) ? "src/main/resources/movielist.csv" : csvPath;
	}
}

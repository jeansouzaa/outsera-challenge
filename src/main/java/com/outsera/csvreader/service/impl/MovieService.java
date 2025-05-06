package com.outsera.csvreader.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.outsera.csvreader.dto.AwardIntervalResponseDTO;
import com.outsera.csvreader.entity.Movie;
import com.outsera.csvreader.factory.MovieFactory;
import com.outsera.csvreader.repository.MovieRepository;
import com.outsera.csvreader.service.CSVReadLineService;

@Service
public class MovieService implements CSVReadLineService {

	private MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public List<Movie> readLineCSV(String CSVpath) {
		List<Movie> movies = new ArrayList<Movie>();
		try(BufferedReader reader = new BufferedReader(new FileReader(new File(CSVpath)))) {
			Boolean firstLine = true;
			String line;
			while((line = reader.readLine()) != null) {
				if(firstLine) {
					firstLine = false;
					continue;
				}
				Movie movie = MovieFactory.build(line);
				if(movie != null) {
					movies.add(movie);
				}
			}
		}catch(Exception exception) {
			System.out.println(getMessage(exception));
			exception.printStackTrace();
		}
		return movies;
	}

	public String getMessage(Exception exception) {
		return exception.getMessage() != null ? exception.getMessage() : "Ocorreu um erro ao tentar processar essa informação!";
	}

	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	public AwardIntervalResponseDTO getOrderedMovies() {
		List<Movie> moviesWinners = movieRepository.findByWinnerTrue();
		Map<String, List<Integer>> moviesByProducers = new HashMap<>();
		for(Movie movie : moviesWinners) {
			String producers = movie.getProducers();
			if(movie.getProducers().contains("and")) {
				producers = producers.replace("and", ",");
			}
			String[] producersSeparated = producers.split(",");
			for(int count = 0; count < producersSeparated.length; count++) {
				String producer = producersSeparated[count].trim();
				if(moviesByProducers.containsKey(producer)) {
					moviesByProducers.get(producer).add(movie.getYear());
				}else {
					moviesByProducers.put(producer, new ArrayList<Integer>(List.of(movie.getYear())));
				}
			}
		}
		
		return null;
	}

}

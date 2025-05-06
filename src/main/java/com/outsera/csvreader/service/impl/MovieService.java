package com.outsera.csvreader.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.outsera.csvreader.CsvReaderApplication;
import com.outsera.csvreader.dto.AwardIntervalResponseDTO;
import com.outsera.csvreader.dto.ProducerIntervalDTO;
import com.outsera.csvreader.entity.Movie;
import com.outsera.csvreader.factory.MovieFactory;
import com.outsera.csvreader.factory.ProducerIntervalFactory;
import com.outsera.csvreader.repository.MovieRepository;
import com.outsera.csvreader.service.CSVReadLineService;

@Service
public class MovieService implements CSVReadLineService {

    private final CsvReaderApplication csvReaderApplication;

	private MovieRepository movieRepository;

	public MovieService(MovieRepository movieRepository, CsvReaderApplication csvReaderApplication) {
		this.movieRepository = movieRepository;
		this.csvReaderApplication = csvReaderApplication;
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
		Map<String, List<Long>> moviesByProducers = new HashMap<>();
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
					moviesByProducers.put(producer, new ArrayList<Long>(List.of(movie.getYear())));
				}
			}
			moviesByProducers.entrySet().forEach(key ->{
				String producerKey = key.getKey();
				Long previousWin = 0L;
				Long followingWin = 0L;
				Long interval = 0L;

				List<Long> years = key.getValue();
				if(years != null) {
					years.sort(Comparator.reverseOrder());
					followingWin = years.get(0);
					if(years.size() >= 2)
					{
						previousWin = years.get(1);
					}
					if(previousWin > 0L) {
						interval = followingWin - previousWin;
					}
					ProducerIntervalDTO producerIntervalDTO = ProducerIntervalFactory.build(followingWin, previousWin, null, producerKey);
				}
			});
		}
		
		return null;
	}

}

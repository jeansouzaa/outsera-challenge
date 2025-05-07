package com.outsera.csvreader.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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
	    Map<String, List<Long>> producersWins = separeProducers(moviesWinners);

	    List<ProducerIntervalDTO> allIntervals = sortProducersList(producersWins);

	    long minInterval = allIntervals.stream().mapToLong(ProducerIntervalDTO::getInterval).min().orElse(0);
	    long maxInterval = allIntervals.stream().mapToLong(ProducerIntervalDTO::getInterval).max().orElse(0);

	    List<ProducerIntervalDTO> minList = allIntervals.stream()
	        .filter(producerInterval -> producerInterval.getInterval() == minInterval)
	        .toList();

	    List<ProducerIntervalDTO> maxList = allIntervals.stream()
	        .filter(producerInterval -> producerInterval.getInterval() == maxInterval)
	        .toList();

	    AwardIntervalResponseDTO response = new AwardIntervalResponseDTO();
	    response.setMin(minList);
	    response.setMax(maxList);
	    return response;
	}

	private Map<String, List<Long>> separeProducers(List<Movie> moviesWinners) {
		Map<String, List<Long>> producersWins = new HashMap<String, List<Long>>();
		for (Movie movie : moviesWinners) {
	        String[] producers = movie.getProducers().replace(" and ", ",").split(",");
	        for (String rawProducer : producers) {
	            String producer = rawProducer.trim();
	            producersWins.computeIfAbsent(producer, p -> new ArrayList<>()).add(movie.getYear());
	        }
	    }
		return producersWins;
	}

	private List<ProducerIntervalDTO> sortProducersList(Map<String, List<Long>> producersWins) {
		List<ProducerIntervalDTO> producers = new ArrayList<>();
		for (Map.Entry<String, List<Long>> entry : producersWins.entrySet()) {
	        List<Long> years = entry.getValue();
	        if (years.size() < 2) continue;

	        years.sort(Long::compareTo);

	        for (int i = 1; i < years.size(); i++) {
	            long interval = years.get(i) - years.get(i - 1);
	            ProducerIntervalDTO producerInterval = ProducerIntervalFactory.build(
	                years.get(i), years.get(i - 1), interval, entry.getKey()
	            );
	            producers.add(producerInterval);
	        }
	    }
		return producers;
	}
}

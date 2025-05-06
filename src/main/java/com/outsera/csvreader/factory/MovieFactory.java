package com.outsera.csvreader.factory;

import com.outsera.csvreader.entity.Movie;

public class MovieFactory {

	public static Movie build(String movie) {
		if(movie != null && !movie.isEmpty()) {
			String[] movieParts = movie.split(";");
			if(movieParts != null && movieParts.length > 0) {
				Integer year = Integer.parseInt(movieParts[0]);
				String title = movieParts[1];
				String studios = movieParts[2];
				String producers = movieParts[3];
				Boolean winner = movieParts.length > 4 && "yes".equalsIgnoreCase(movieParts[4]) ? true : false;
				return build(year, title, studios, producers, winner);
			}
			
		}
		return null;
	}

	public static Movie build(Integer year, String title, String studios, String producers, Boolean winner) {
		Movie movie = new Movie();
		movie.setYear(year);
		movie.setTitle(title);
		movie.setStudios(studios);
		movie.setProducers(producers);
		movie.setWinner(winner);
		return movie;
	}
}

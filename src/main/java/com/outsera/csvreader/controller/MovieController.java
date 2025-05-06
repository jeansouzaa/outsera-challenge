package com.outsera.csvreader.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsera.csvreader.entity.Movie;
import com.outsera.csvreader.service.impl.MovieService;

@RestController
@RequestMapping("producers/")
public class MovieController {

	private MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping
	@RequestMapping("movies")
	public List<Movie> getProducersWinners() {
		return movieService.getAllMovies();
	}

	@GetMapping
	@RequestMapping("orderedmovies")
	public String orderedMovies() {
		String movies = movieService.getOrderedMovies();
		return "";
	}
}

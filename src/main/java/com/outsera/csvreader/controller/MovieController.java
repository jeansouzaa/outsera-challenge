package com.outsera.csvreader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outsera.csvreader.dto.AwardIntervalResponseDTO;
import com.outsera.csvreader.service.impl.MovieService;

@RestController
@RequestMapping("/producers")
public class MovieController {

	private MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/intervals")
	public AwardIntervalResponseDTO orderedMovies() {
		AwardIntervalResponseDTO movies = movieService.getOrderedMovies();
		return movies;
	}
}

package com.outsera.csvreader.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.outsera.csvreader.entity.Movie;
import com.outsera.csvreader.factory.MovieFactory;
import com.outsera.csvreader.service.CSVReadLineService;

@Service
public class MovieService implements CSVReadLineService {

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

}

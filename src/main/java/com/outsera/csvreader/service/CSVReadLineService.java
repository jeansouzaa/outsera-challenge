package com.outsera.csvreader.service;

import java.util.List;

import com.outsera.csvreader.entity.Movie;

public interface CSVReadLineService {

	public List<Movie> readLineCSV(String CSVpath);

}

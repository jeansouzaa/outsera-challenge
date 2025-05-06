package com.outsera.csvreader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.outsera.csvreader.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}

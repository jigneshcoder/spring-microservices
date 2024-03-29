package com.jigl.movieinfoservice.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jigl.movieinfoservice.models.Movie;

@RestController
@RequestMapping("/movies")
public class MovieResource
{
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId)
	{
		return new Movie(movieId,"Test name","Test Desc");
	}
}

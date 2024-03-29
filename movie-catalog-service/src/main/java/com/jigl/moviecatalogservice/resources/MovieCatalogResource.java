package com.jigl.moviecatalogservice.resources;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.jigl.moviecatalogservice.models.CatalogItem;
import com.jigl.moviecatalogservice.models.Movie;
import com.jigl.moviecatalogservice.models.Rating;
import com.jigl.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource
{
	@Autowired
	private RestTemplate restTemplate;
	
//	@Autowired
//	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable(name = "userId") String userId)
	{
		/*List<Rating> ratings = Arrays.asList(
				new Rating("1234",4),
				new Rating("5678",3)
		);
		*/
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+userId,UserRating.class);
		return userRating.getUserRating().stream().map(rating->{
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
			

			return new CatalogItem(movie.getName(), movie.getDesc(), rating.getRating());
		}).collect(Collectors.toList());
		//return Collections.singletonList(new CatalogItem("Transformers", "Test", 4));
	}
}

/*
Movie movie = webClientBuilder.build()
				.get()
				.uri("http://localhost:8082/movies/"+rating.getMovieId())
				.retrieve()
				.bodyToMono(Movie.class)
				.block();
*/
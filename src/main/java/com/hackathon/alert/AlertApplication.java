package com.hackathon.alert;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestFactory;
import com.hackathon.alert.twitter.Converter;
import com.hackathon.alert.twitter.Result;
import com.hackathon.alert.twitter.auth.TwitterAuthenticator;
import com.hackathon.alert.twitter.model.Trend;
import com.hackathon.alert.twitter.model.Tweets;
import com.hackathon.alert.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class AlertApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AlertApplication.class, args);
	}

	@Autowired private TweetService tweetService;
	@Autowired private ObjectMapper mapper;
	@Autowired private Converter converter;

	@Override
	public void run(String... args) throws Exception {
		Map<Trend, Tweets> tweetsForCurrentTrends = tweetService.findTweetsForCurrentTrends();
//		Map<String, List<String>> tweetsForCurrentTrendsAsString = tweetService.findTweetsForCurrentTrendsAsString();
//		String s = mapper.writeValueAsString(tweetsForCurrentTrends);
		List<Result> results = converter.convert(tweetsForCurrentTrends);
		String s2 = mapper.writeValueAsString(results);
//		System.out.println("\n\n\n\n");
//		System.out.println(s);
//		System.out.println("\n\n\n\n");
		System.out.println("\n\n\n\n");
		System.out.println(s2);
		System.out.println("\n\n\n\n");
	}

	@Bean
	public HttpRequestFactory httpRequestFactory() throws Exception {
		String consumerKey = "RLSrphihyR4G2UxvA0XBkLAdl";
		String consumerSecret = "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4";
		PrintStream os = System.out;
		TwitterAuthenticator t = new TwitterAuthenticator(os, consumerKey, consumerSecret);
		return t.getAuthorizedHttpRequestFactory();
	}

	@Bean
	public ObjectMapper mapper(){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}



}

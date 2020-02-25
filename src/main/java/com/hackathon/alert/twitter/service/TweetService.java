package com.hackathon.alert.twitter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.common.base.Joiner;
import com.hackathon.alert.twitter.model.Trend;
import com.hackathon.alert.twitter.model.Tweets;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class TweetService {

    private final TrendService trendService;
    private final HttpRequestFactory requestFactory;
    private final ObjectMapper mapper;

    private static final Joiner joinerEqual = Joiner.on("=");
    private static final Joiner joinerAnd = Joiner.on("&");
    private static final String TWEETS_URL = "https://api.twitter.com/1.1/search/tweets.json?";

    public TweetService(TrendService trendService, HttpRequestFactory requestFactory, ObjectMapper mapper){
        this.trendService = trendService;
        this.requestFactory = requestFactory;
        this.mapper = mapper;
    }

    public Map<Trend, Tweets> findTweetsForCurrentTrends(){
        List<Trend> trends = trendService.findTrends();
        return findTweetsByTrend(trends);
    }

    public Map<String, List<String>> findTweetsForCurrentTrendsAsString(){
        List<Trend> trends = trendService.findTrends();
        Map<Trend, Tweets> tweetsByTrend = findTweetsByTrend(trends);
        Map<String, List<String>> collect = tweetsByTrend
                .entrySet()
                .stream()
                .collect(toMap(
                        e -> e.getKey().getQuery(),
                        e -> e.getValue().getStatuses().stream().map(s -> s.getText()).collect(toList())
                ));
        return collect;
    }

    public Map<Trend, Tweets> findTweetsByTrend(Collection<Trend> trends){
        return trends.stream().collect(toMap(e -> e, this::findTweetsByTrend));
    }

    public Tweets findTweetsByTrend(Trend trend){
        try {
            HttpRequest request = requestFactory.buildGetRequest((buildUrl(trend.getQuery())));
            HttpResponse response = request.execute();
            Tweets tweets = mapper.readValue(response.getContent(), Tweets.class);
            return tweets;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private GenericUrl buildUrl(String trend) {
        String urlString = urlString(trend);
//        logger.info("Sending request to " + urlString);
        return new GenericUrl(urlString);
    }

    private String urlString(String trend) {
        StringBuilder sb = new StringBuilder(TWEETS_URL);
        String queryParam = joinerEqual.join("q", trend);
        String maxParam = joinerEqual.join("count", "1");
        String urlParameters = joinerAnd.join(queryParam, maxParam);
        sb.append(urlParameters);
        return sb.toString();
    }

}

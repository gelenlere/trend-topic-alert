package com.hackathon.alert.twitter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.common.base.Joiner;
import com.hackathon.alert.twitter.model.Trend;
import com.hackathon.alert.twitter.model.Trends;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrendService {

    private final HttpRequestFactory requestFactory;
    private final ObjectMapper mapper;

    private static final Joiner joinerEqual = Joiner.on("=");
    private static final String TRENDS_URL = "https://api.twitter.com/1.1/trends/place.json?";
    private static final int TRENDS_GLOBAL_INFORMATION_PARAMETER_WORLD = 1;
    private static final int TRENDS_GLOBAL_INFORMATION_PARAMETER_USA = 23424977;
    private static final int TRENDS_GLOBAL_INFORMATION_PARAMETER_ITALY = 23424977;
    private static final int TRENDS_GLOBAL_INFORMATION_PARAMETER_NEW_YORK = 2459115;
    private static final int TRENDS_GLOBAL_INFORMATION_PARAMETER_UNITED_KINGDOM = 23424975;

    public TrendService(HttpRequestFactory requestFactory, ObjectMapper mapper){
        this.requestFactory = requestFactory;
        this.mapper = mapper;
    }

    public List<Trend> findTrends(){
        try {
            HttpRequest request = requestFactory.buildGetRequest((buildUrl()));
            HttpResponse response = request.execute();
            Trends[] trendsArray = mapper.readValue(response.getContent(), Trends[].class);
            List<Trends> trends = Arrays.asList(trendsArray);
            return trends.stream().flatMap(t->t.getTrends().stream()).collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private GenericUrl buildUrl() {
        String urlString = urlString();
//        logger.info("Sending request to " + urlString);
        return new GenericUrl(urlString);
    }

    private String urlString() {
        StringBuilder sb = new StringBuilder(TRENDS_URL);
        String urlParameters = joinerEqual.join("id", TRENDS_GLOBAL_INFORMATION_PARAMETER_UNITED_KINGDOM);
        sb.append(urlParameters);
        return sb.toString();
    }

}

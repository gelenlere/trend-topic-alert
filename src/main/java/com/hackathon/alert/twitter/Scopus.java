package com.hackathon.alert.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.alert.twitter.model.Tweets;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Arrays;

@Data
public class Scopus {

    @JsonProperty("trending_topic") private String trendingTopic;
    @JsonProperty("publication_title") private String publicationTitle;
    @JsonProperty("scopus_link")private String scopusLink;

    public static void main(String[] args) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = new File("/Users/gelenlere/Documents/alert/src/main/java/com/hackathon/alert/twitter/scopus.json");
        Scopus[] scopus = mapper.readValue(new FileInputStream(file), Scopus[].class);
        System.out.println(Arrays.asList(scopus));
    }

}

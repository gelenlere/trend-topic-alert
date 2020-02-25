package com.hackathon.alert.twitter.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.List;

@Data
public class Tweets {

    private List<Tweet> statuses;

    public static void main(String[] args) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = new File("/Users/sestayocaamanof/Projects/Hackathon/alert/src/main/java/com/hackathon/alert/twitter/twits.json");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        StringBuilder sb = new StringBuilder("");
        while(line != null){
            sb.append(line);
            line = br.readLine();
        }
        br.close();
        String json = sb.toString();
        Tweets tweets = mapper.readValue(new FileInputStream(file), Tweets.class);
        Tweets tweets2 = mapper.readValue(json, Tweets.class);
        System.out.println("");
    }


}

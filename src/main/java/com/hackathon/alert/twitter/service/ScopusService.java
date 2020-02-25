package com.hackathon.alert.twitter.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.alert.twitter.Scopus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class ScopusService {

    public List<Scopus> find(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            File file = new File("/Users/gelenlere/Documents/alert/src/main/java/com/hackathon/alert/twitter/scopus.json");
            Scopus[] scopus = mapper.readValue(new FileInputStream(file), Scopus[].class);
            return Arrays.asList(scopus);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}

package com.hackathon.alert.twitter;

import lombok.Data;
import java.util.List;

@Data
public class Result {

    private String trend;
    private List<String> tweets;

}


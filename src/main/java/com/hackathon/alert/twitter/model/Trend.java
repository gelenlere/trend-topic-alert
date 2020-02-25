package com.hackathon.alert.twitter.model;

import lombok.Data;

@Data
public class Trend {

    private String name;
    private String url;
    private String query;
    private int tweetVolume;

}

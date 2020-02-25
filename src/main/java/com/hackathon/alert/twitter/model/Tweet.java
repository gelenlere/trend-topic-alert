package com.hackathon.alert.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Tweet {

    @JsonProperty("id_str") private String id;
    @JsonProperty("created_at") private String createdAt;
    private String text;

}

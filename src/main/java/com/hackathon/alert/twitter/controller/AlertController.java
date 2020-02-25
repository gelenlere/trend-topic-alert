package com.hackathon.alert.twitter.controller;

import com.hackathon.alert.twitter.email.MailSender;
import com.hackathon.alert.twitter.model.TrendTopicPublication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AlertController {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/publications", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void sendPublicationsPerTrendTopics(@RequestBody TrendTopicPublication trendTopicPublication) {
        MailSender.sendMail(trendTopicPublication.getPublicationsForTT());

    }

}

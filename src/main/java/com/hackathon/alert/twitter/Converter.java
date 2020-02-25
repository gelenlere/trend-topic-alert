package com.hackathon.alert.twitter;

import com.hackathon.alert.twitter.model.Article;
import com.hackathon.alert.twitter.model.EmailData;
import com.hackathon.alert.twitter.model.Trend;
import com.hackathon.alert.twitter.model.Tweets;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Converter {

    public List<Result> convert(Map<Trend, Tweets> map){
        return map.entrySet().stream().map(e -> convert(e)).collect(Collectors.toList());
    }

    private Result convert(Map.Entry<Trend, Tweets> entry){
        Result result = new Result();
        result.setTrend(entry.getKey().getName());
        List<String> tweets = entry.getValue().getStatuses().stream().map(s -> s.getText()).collect(Collectors.toList());
        result.setTweets(tweets);
        return result;
    }

    public List<EmailData> convert(List<Scopus> scopuses){
        return scopuses.stream().map(s -> convert(s)).collect(Collectors.toList());
    }

    private EmailData convert(Scopus s){
        EmailData e = new EmailData();
        e.setTrend(s.getTrendingTopic());
        Article a = new Article();
        a.setTitle(s.getPublicationTitle());
        a.setLink(s.getScopusLink());
        e.setArticle(a);
        return e;
    }

}

package com.example.liubo.world_of_movie.Bean;

import java.util.List;

/**
 * Created by Liubo on 2018/3/14.
 */

public class MovieInfo {
    private String id;
    private String name;
    private String content;
    private String praise;
    private String pic_url;
    private String movie_url;
    private String director;
    private String performer;
    private List<MovieDiscussInfo> listDiscuss;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getMovie_url() {
        return movie_url;
    }

    public void setMovie_url(String movie_url) {
        this.movie_url = movie_url;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public List<MovieDiscussInfo> getListDiscuss() {
        return listDiscuss;
    }

    public void setListDiscuss(List<MovieDiscussInfo> listDiscuss) {
        this.listDiscuss = listDiscuss;
    }
}

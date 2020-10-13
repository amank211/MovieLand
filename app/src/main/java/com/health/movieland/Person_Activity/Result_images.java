
package com.health.movieland.Person_Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result_images {

    @SerializedName("iso_639_1")
    @Expose
    private Object iso6391;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("aspect_ratio")
    @Expose
    private Double aspectRatio;
    @SerializedName("media")
    @Expose
    private Media media;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("width")
    @Expose
    private Integer width;

    public Object getIso6391() {
        return iso6391;
    }

    public void setIso6391(Object iso6391) {
        this.iso6391 = iso6391;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getFilePath() {
        return "https://image.tmdb.org/t/p/w500" + filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}

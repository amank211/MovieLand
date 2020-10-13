
package com.health.movieland.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Random;

public class nowplaying {

    @SerializedName("results")
    @Expose
    private List<Result_nowplaying> results = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("dates")
    @Expose
    private Dates_nowplaying dates;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    public List<Result_nowplaying> getResults() {
        return results;
    }

    public void setResults(List<Result_nowplaying> resultNowplayings) {
        this.results = resultNowplayings;
    }


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Dates_nowplaying getDates() {
        return dates;
    }

    public void setDates(Dates_nowplaying dates) {
        this.dates = dates;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}

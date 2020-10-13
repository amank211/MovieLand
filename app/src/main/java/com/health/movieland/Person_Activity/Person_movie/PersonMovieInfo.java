
package com.health.movieland.Person_Activity.Person_movie;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonMovieInfo {

    @SerializedName("cast")
    @Expose
    private List<Cast> cast = new ArrayList<>();
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = new ArrayList<>();
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

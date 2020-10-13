
package com.health.movieland.Cast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cast {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private Cast_[] cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cast_[] getCast() {
        return cast;
    }

    public void setCast(Cast_[] cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

}

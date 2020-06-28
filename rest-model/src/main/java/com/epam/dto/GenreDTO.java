package com.epam.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GenreDTO {
    @Size(min = 2, max = 30,message = "Incorrect genre name(Should have size 2-30)")
    @Pattern(regexp = "[a-zA-Z 0-9]+")
    @NotBlank(message = "Incorrect genre name(Should contain not only spaces)")
    @NotEmpty(message = "Incorrect genre name(Should contain some information)")
    private String genreName;
    private long genreId;


    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }
}

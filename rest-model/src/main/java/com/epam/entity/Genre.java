package com.epam.entity;

public class Genre {

    private String genreName;
    private long genreId;

    public Genre() {
    }
    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public Genre(String genreName, long genreId) {
        this.genreName = genreName;
        this.genreId = genreId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (genreId != genre.genreId) return false;
        return genreName != null ? genreName.equals(genre.genreName) : genre.genreName == null;
    }

    @Override
    public int hashCode() {
        int result = genreName != null ? genreName.hashCode() : 0;
        result = 31 * result + (int) (genreId ^ (genreId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreName='" + genreName + '\'' +
                ", genreId=" + genreId +
                '}';
    }
}

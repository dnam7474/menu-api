package com.simple.menu_api.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "films")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String homepage;

    @Column(name = "releasedate")
    @JsonProperty("releasedate")
    private LocalDate releaseDate;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(name = "posterpath")
    @JsonProperty("posterpath")
    private String posterPath;

    private Integer runtime;

    private String tagline;

    @Column(precision = 4, scale = 1)
    private BigDecimal popularity;

    @Column(name = "imdbid")
    @JsonProperty("imdbid")
    private String imdbId;

    @Column(name = "voteaverage", precision = 3, scale = 1)
    @JsonProperty("voteaverage")
    private BigDecimal voteAverage;

    @Column(name = "votecount")
    @JsonProperty("votecount")
    private Integer voteCount;

    // Custom constructor for common use cases
    public Film(String title, String homepage, String overview, String posterPath,
                Integer runtime, String tagline, BigDecimal popularity, String imdbId,
                BigDecimal voteAverage, Integer voteCount) {
        this.title = title;
        this.homepage = homepage;
        this.overview = overview;
        this.posterPath = posterPath;
        this.runtime = runtime;
        this.tagline = tagline;
        this.popularity = popularity;
        this.imdbId = imdbId;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }
}


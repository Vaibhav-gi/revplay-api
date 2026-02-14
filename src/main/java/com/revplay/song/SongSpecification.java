package com.revplay.song;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class SongSpecification {

    public static Specification<Song> filterBy(
            String genre,
            String artist,
            Integer releaseYear
    ) {

        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            if (genre != null && !genre.isBlank()) {
                predicate = cb.and(predicate,
                        cb.equal(cb.lower(root.get("genre")),
                                genre.toLowerCase()));
            }

            if (artist != null && !artist.isBlank()) {
                Join<Object, Object> artistJoin =
                        root.join("artist");

                predicate = cb.and(predicate,
                        cb.like(
                                cb.lower(artistJoin.get("username")),
                                "%" + artist.toLowerCase() + "%"
                        ));
            }

            if (releaseYear != null) {
                predicate = cb.and(predicate,
                        cb.equal(
                                cb.function("year",
                                        Integer.class,
                                        root.get("releaseDate")),
                                releaseYear
                        ));
            }

            return predicate;
        };
    }
}
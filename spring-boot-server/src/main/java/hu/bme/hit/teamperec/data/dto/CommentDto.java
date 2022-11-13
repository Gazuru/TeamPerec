package hu.bme.hit.teamperec.data.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CommentDto(@JsonProperty("commenter") UUID commenterId,
                         @JsonProperty("comment_text") String commentText) {
}

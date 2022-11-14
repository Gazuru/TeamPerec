package hu.bme.hit.teamperec.data.dto;

import java.util.UUID;

public record CommentDto(UUID commenter,
                         String commentText) {
}

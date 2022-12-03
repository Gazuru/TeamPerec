package hu.bme.hit.teamperec.data.response;

import java.util.UUID;

public record CommentResponse(UUID id,
                              String commenter,
                              String commentText,
                              String creationDate) {
}

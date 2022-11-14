package hu.bme.hit.teamperec.data.response;

import java.util.UUID;

public record CommentResponse(UUID id,
                              UUID commenter,
                              String commentText) {
}

package hu.bme.hit.teamperec.data.response;

import java.util.List;
import java.util.UUID;

public record CAFFResponse(UUID id,
                           String name,
                           String description,
                           List<CommentResponse> comments,
                           String imagePreviewBase64,
                           UUID uploader,
                           String uploadeName) {
}

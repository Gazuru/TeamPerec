package hu.bme.hit.teamperec.data.response;

import java.util.List;
import java.util.UUID;

public record CAFFResponse(UUID id,
                           String name,
                           String description,
                           List<UUID> comments,
                           String image,
                           UUID uploader) {
}

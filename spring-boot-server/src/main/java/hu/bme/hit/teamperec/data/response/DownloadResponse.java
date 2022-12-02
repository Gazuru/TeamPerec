package hu.bme.hit.teamperec.data.response;

import java.util.UUID;

public record DownloadResponse(UUID caffId,
                               String caffName,
                               String downloadDate) {
}

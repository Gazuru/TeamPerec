package hu.bme.hit.teamperec.data.response;

import java.util.Set;
import java.util.UUID;

public record UserResponse(UUID id, String name, String email, Set<DownloadResponse> downloads,
                           Set<CAFFUserResponse> caffs) {
}

package hu.bme.hit.teamperec.data.dto;

import java.util.UUID;

public record CAFFDto(String name,
                      String description,
                      byte[] image,
                      UUID uploader) {

}

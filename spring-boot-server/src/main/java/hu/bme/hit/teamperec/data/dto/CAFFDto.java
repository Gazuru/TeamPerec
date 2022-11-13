package hu.bme.hit.teamperec.data.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CAFFDto(@JsonProperty("name") String name,
                      @JsonProperty("description") String description,
                      @JsonProperty("image") byte[] image,
                      @JsonProperty("uploader") UUID uploader) {

}

package hu.bme.hit.teamperec.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDto(@JsonProperty("username") String username,
                      @JsonProperty("email") String email,
                      @JsonProperty("password") String password) {
}

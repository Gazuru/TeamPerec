package hu.bme.hit.teamperec.data.dto;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record SignupRequest(@NotBlank @Size(min = 3, max = 20) String username,
                            @NotBlank @Size(max = 50) @Email String email,
                            Set<String> role,
                            @NotBlank @Size(min = 6, max = 40) String password) {
}

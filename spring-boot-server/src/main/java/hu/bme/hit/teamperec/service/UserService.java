package hu.bme.hit.teamperec.service;

import java.util.List;
import java.util.UUID;

import hu.bme.hit.teamperec.config.security.services.UserDetailsImpl;
import hu.bme.hit.teamperec.data.ComputerSecurityException;
import hu.bme.hit.teamperec.data.dto.UserDto;
import hu.bme.hit.teamperec.data.entity.User;
import hu.bme.hit.teamperec.data.repository.UserRepository;
import hu.bme.hit.teamperec.data.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ComputerSecurityException("User not found by id: " + id));
    }

    public UserResponse getUser(UUID id) {
        return toResponse(getUserById(id));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ComputerSecurityException("User not found by username: " + username));
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse updateUser(UUID userId, UserDto userDto) {
        var user = getUserById(userId);

        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());

        return toResponse(userRepository.save(user));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getUsername(), user.getEmail());
    }

    public void deleteUser(UUID userId) {
        var currentUserId =
                ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if (userRepository.existsById(userId) && !getUserById(userId).getId().equals(currentUserId)) {
            userRepository.deleteById(userId);
        }
    }
}

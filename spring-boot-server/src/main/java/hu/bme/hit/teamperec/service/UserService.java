package hu.bme.hit.teamperec.service;

import java.util.List;
import java.util.UUID;

import hu.bme.hit.teamperec.data.ComputerSecurityException;
import hu.bme.hit.teamperec.data.dto.UserDto;
import hu.bme.hit.teamperec.data.entity.User;
import hu.bme.hit.teamperec.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ComputerSecurityException("User not found by id: " + id));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UUID userId, UserDto userDto) {
        var user = getUser(userId);

        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());

        return userRepository.save(user);
    }
}

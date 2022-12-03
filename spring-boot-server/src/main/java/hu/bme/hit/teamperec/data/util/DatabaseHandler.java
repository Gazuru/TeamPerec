package hu.bme.hit.teamperec.data.util;

import java.util.Set;
import javax.transaction.Transactional;

import hu.bme.hit.teamperec.data.entity.Role;
import hu.bme.hit.teamperec.data.entity.User;
import hu.bme.hit.teamperec.data.enums.ERole;
import hu.bme.hit.teamperec.data.repository.RoleRepository;
import hu.bme.hit.teamperec.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class DatabaseHandler {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void initDatabase() {
        var roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            var roleUser = new Role();
            roleUser.setName(ERole.ROLE_USER);
            var roleAdmin = new Role();
            roleAdmin.setName(ERole.ROLE_ADMIN);
            roleRepository.save(roleUser);
            roleRepository.save(roleAdmin);
        }
        var users = userRepository.findByRolesName(ERole.ROLE_ADMIN);
        if (users.isEmpty()) {
            var admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.admin");
            var adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }
    }
}

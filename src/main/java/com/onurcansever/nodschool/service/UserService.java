package com.onurcansever.nodschool.service;

import com.onurcansever.nodschool.model.Roles;
import com.onurcansever.nodschool.model.User;
import com.onurcansever.nodschool.repository.RolesRepository;
import com.onurcansever.nodschool.repository.UserRepository;
import com.onurcansever.nodschool.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createNewUser(User user) {
        boolean isSaved = false;

        Roles role = this.rolesRepository.findByRoleName(Constants.STUDENT_ROLE);

        user.setRoles(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = this.userRepository.save(user);

        if (savedUser.getUserId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }
}

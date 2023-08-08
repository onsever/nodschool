package com.onurcansever.nodschool.service;

import com.onurcansever.nodschool.model.Roles;
import com.onurcansever.nodschool.model.User;
import com.onurcansever.nodschool.repository.RolesRepository;
import com.onurcansever.nodschool.repository.UserRepository;
import com.onurcansever.nodschool.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public UserService(UserRepository userRepository, RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    public boolean createNewUser(User user) {
        boolean isSaved = false;

        Roles role = this.rolesRepository.findByRoleName(Constants.STUDENT_ROLE);

        user.setRoles(role);

        User savedUser = this.userRepository.save(user);

        if (savedUser.getUserId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }
}

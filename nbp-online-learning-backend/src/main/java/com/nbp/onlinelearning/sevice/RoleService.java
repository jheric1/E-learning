package com.nbp.onlinelearning.sevice;

import com.nbp.onlinelearning.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Long getRoleIdByName(String roleName) {
        return roleRepository.findRoleIdByName(roleName);
    }
}

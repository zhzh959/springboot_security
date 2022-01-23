package com.gunt.springboot.springboot_security.dao;

import com.gunt.springboot.springboot_security.entity.Role;


public interface RoleDAO {

    void save(Role role);

    void delete(Role role);

    Role getById(Long id);

    Role getRoleByName(String rolename);

    Role createRoleIfNotFound(String name, long id);
}

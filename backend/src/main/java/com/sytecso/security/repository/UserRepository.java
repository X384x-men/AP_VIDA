package com.sytecso.security.repository;

import java.sql.SQLException;

import com.sytecso.dto.usuarioacceso.UserAccess;

public interface UserRepository {
    public UserAccess findUser(String username, String password);
    public void createDefaultUsersAndRoles() throws SQLException;
    public void createDefaultMenuOptions();
	public UserAccess findUserName(String username);
}

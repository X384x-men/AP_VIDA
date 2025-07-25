package com.sytecso.security.init;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.sytecso.security.repository.UserRepository;

@Component
public class LoaderComponents {
	{
		executedTimes++;
	}
	private static int executedTimes = -1;
	@Autowired
	private UserRepository userRepository;

	@Bean(autowireCandidate = false)
	@DependsOn({ "createDefaultMenu" })
	private void createDefaultUsers() throws SQLException {
		if (executedTimes == 0) {
			this.userRepository.createDefaultUsersAndRoles();
		}
	}

	@Bean(autowireCandidate = false)
	private void createDefaultMenu() {
		if (executedTimes == 0) {
			this.userRepository.createDefaultMenuOptions();
		}
	}


}

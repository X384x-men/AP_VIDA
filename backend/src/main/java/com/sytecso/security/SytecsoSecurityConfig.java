package com.sytecso.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.sytecso.component.utility.UtileriaCifrado;
import com.sytecso.security.provider.CustomAccessDecisionVoter;
import com.sytecso.security.provider.CustomAuthenticationFilter;
import com.sytecso.security.provider.CustomUserDetailsAuthenticationProvider;
import com.sytecso.security.response.CustomAccessDeniedHandler;
import com.sytecso.security.response.CustomAuthenticationFailureHandler;
import com.sytecso.security.response.CustomHttp403ForbiddenEntryPoint;
import com.sytecso.security.response.CustomLogoutSuccessHandler;
import com.sytecso.security.response.SimpleUrlAuthenticationSuccessHandler;
import com.sytecso.security.service.CustomUserDetailsService;

@Configuration
@ComponentScan
@EnableWebSecurity
public class SytecsoSecurityConfig extends WebSecurityConfigurerAdapter {
	// @Autowired
	// private CorsFilter corsFilter;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private CustomAccessDecisionVoter customAccessDecisionVoter;

	public SytecsoSecurityConfig() {
		super();
	}

	private CustomAuthenticationFilter authenticationFilter() throws Exception {
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManagerBean());
		filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		filter.setAuthenticationFailureHandler(authenticationFailureHandler());
		return filter;
	}

	// private CsrfTokenRepository csrfTokenRepository() {
	// HttpSessionCsrfTokenRepository repository = new
	// HttpSessionCsrfTokenRepository();
	// repository.setHeaderName(CustomCsrfFilter.CSRF_COOKIE_NAME);
	// return repository;
	// }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Bean
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(),
				new RoleVoter(), new AuthenticatedVoter(), customAccessDecisionVoter);
		return new UnanimousBased(decisionVoters);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				// .csrf().csrfTokenRepository(csrfTokenRepository())
				// .and().addFilterAfter(new CustomCsrfFilter(), CsrfFilter.class)
				.authorizeRequests().antMatchers("/angular2/**").permitAll().antMatchers("/assets/**").permitAll()

				.antMatchers("/ws/**").permitAll()
				.antMatchers("/createEmpleadoAP").permitAll()
				.antMatchers("/getDependencias").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/email/**").permitAll()
				.antMatchers("/adminProyecto/**").permitAll()
				.antMatchers("/timeline/**").permitAll()
				.antMatchers("/usuario-acceso/**").permitAll()
				.antMatchers("/PDF/**").permitAll()
				.antMatchers("/solicitud/**").permitAll()
				.antMatchers("/angular/register").permitAll()
				.antMatchers("/angular/changePswd").permitAll()
				.antMatchers("/batch/**").permitAll()
				.antMatchers("/batch/postBatch").permitAll()
				.antMatchers("/batch/processBatch").permitAll()
				.antMatchers("/catalogos/**").permitAll()
				.antMatchers("/ws/**").permitAll().antMatchers("/login").permitAll()
				.antMatchers("/register/**").permitAll()
				.antMatchers("/aclaraciones/**").permitAll()
				
				

				.antMatchers("/ws/**").permitAll().antMatchers("/login").permitAll().antMatchers("/evidencia/**")
				
				
				.permitAll()


				.accessDecisionManager(accessDecisionManager()).anyRequest().fullyAuthenticated().and()
				// .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
				.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class).formLogin()
				.loginPage("/login").loginProcessingUrl("/login/**").defaultSuccessUrl("/angular/")
				.failureUrl("/login?error=true").and().logout().logoutSuccessHandler(logoutSuccessHandler())
				.clearAuthentication(true).invalidateHttpSession(true).logoutSuccessUrl("/login?logout")
				.deleteCookies("JSESSIONID").and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and()
				.exceptionHandling().authenticationEntryPoint(customHttp403ForbiddenEntryPoint())
				// .and().rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
				// defines a repository where tokens are stored
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
	}

	@Bean
	public AuthenticationEntryPoint customHttp403ForbiddenEntryPoint() {
		return new CustomHttp403ForbiddenEntryPoint();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return UtileriaCifrado.getMD5(rawPassword.toString()).equals(encodedPassword);
			}

			@Override
			public String encode(CharSequence rawPassword) {
				return UtileriaCifrado.getMD5(rawPassword.toString());
			}
		};
	}

	public AuthenticationProvider authProvider() {
		return new CustomUserDetailsAuthenticationProvider(passwordEncoder(), userDetailsService);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/angular2/**");
		web.ignoring().antMatchers("/assets/**");
	}

	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new SimpleUrlAuthenticationSuccessHandler();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("file://", "http://localhost:4200", "http://localhost:8080",
				"https://www.apvida-consultas.mx", "http://54.215.98.76:8080","http://54.153.12.83:8080", "http://34.200.128.17:8080"));
		//"https://www.apvida-consultas.mx", "http://34.200.128.17:8080"));
		// configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
		// habilitar unicamente para pruebas en conjunto con android
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
		configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept",
				"Authorization", "Access-Control-Allow-Origin", "X-Frame-Options"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
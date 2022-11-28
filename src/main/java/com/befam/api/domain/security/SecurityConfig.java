package com.befam.api.domain.security;

import com.befam.api.domain.exception.AccessDeniedCustomHandler;
import com.befam.api.domain.exception.AuthenticationCustomEntryPoint;
import com.befam.api.domain.filter.ApiFilter;
import com.befam.api.domain.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.httpBasic()
					.disable()
				.csrf()
					.disable()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.authorizeHttpRequests()
					.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
					.antMatchers("/api/v1/auth*/**").permitAll()
					.anyRequest().authenticated()
					.and()
				.cors()
					.and()
				.headers()
					.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN))
					.and()
				.exceptionHandling().accessDeniedHandler(new AccessDeniedCustomHandler())
					.and()
				.exceptionHandling().authenticationEntryPoint(new AuthenticationCustomEntryPoint())
					.and()
				.addFilterBefore(new ApiFilter(), UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean    
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.setAllowedMethods(Arrays.asList(
				HttpMethod.OPTIONS.name(), 
				HttpMethod.HEAD.name(), 
				HttpMethod.GET.name(), 
				HttpMethod.POST.name(), 
				HttpMethod.PUT.name(), 
				HttpMethod.DELETE.name()
				));
		configuration.addExposedHeader(JwtUtil.ACCESS_TOKEN);
		configuration.addAllowedHeader("*");
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}

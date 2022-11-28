package com.befam.api.domain.exception;

import com.befam.api.domain.utils.ApiResponse;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AuthenticationCustomEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("error: " + authException.getMessage());
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
		PrintWriter writer = response.getWriter();
		
		ApiResponse.ApiResult<?> apiResult = ApiResponse.error(ServiceError.ACCESS_DENIED.getDescription(), HttpStatus.FORBIDDEN);
		String body = new Gson().toJson(apiResult);
		writer.print(body);
	}
}

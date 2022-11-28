package com.befam.api.domain.exception;

import com.befam.api.domain.utils.ApiResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AccessDeniedCustomHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.error("error: " + accessDeniedException.getMessage());
		
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
		PrintWriter writer = response.getWriter();
		
		ApiResponse.ApiResult<?> apiResult = ApiResponse.error(accessDeniedException, HttpStatus.FORBIDDEN);
		String body = new Gson().toJson(apiResult);
		writer.print(body);
	}
}

package com.example.Employee_projects.config;


import com.example.Employee_projects.ApiResponse.ApiResponse;
import com.example.Employee_projects.Document.Employee;
import com.example.Employee_projects.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;


public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private JwtTokenUtils jwtTokenUtil;

    private EmployeeRepository employeeRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    JwtAuthenticationFilter(JwtTokenUtils jwtTokenUtil, EmployeeRepository userCRepo) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.employeeRepository = userCRepo;
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filter)
            throws IOException {
        try {
            String authToken = req.getHeader("Authorization");
            String username = jwtTokenUtil.parseToken(authToken);
            Employee employee = employeeRepository.findByPhone(username);
            if (employee != null) {
                // Create an authentication token without any specific roles
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        employee, null, Collections.emptyList()); // or Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("Authenticated user {}, setting security context", username);

                // Set the authenticated user in the security context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Proceed with the request
                filter.doFilter(req, res);
            }
            else {
                generateUnauthorisedAccess(res);
            }

        } catch (Exception e) {
            logger.error("e: ", e);
            generateUnauthorisedAccess(res);
        }
    }

    public void generateUnauthorisedAccess(HttpServletResponse res) throws IOException {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        ApiResponse resp = new ApiResponse(HttpStatus.UNAUTHORIZED , "UNAUTHORISED");
        String jsonRespString = ow.writeValueAsString(resp);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = res.getWriter();
        writer.write(jsonRespString);
        System.out.println("===============================");

    }

}


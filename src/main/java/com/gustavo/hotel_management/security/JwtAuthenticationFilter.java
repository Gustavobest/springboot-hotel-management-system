package com.gustavo.hotel_management.security;
import org.slf4j.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;


    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request  , HttpServletResponse response, FilterChain filterChain)
                 throws ServletException , IOException {


        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if(!jwtService.isTokenValid(token)){
            logger.warn("Token invaliado o expirado");
            filterChain.doFilter(request , response);
            return;
        }

        System.out.println("Token Recibido " + token);

        String email  = jwtService.extractEmail(token);
        logger.info("Autenticando usuario: {}",  email);
        logger.info("Email del token{} " , email);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        logger.info("usuario encontrado {} ",userDetails.getUsername());


        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()

                );
        SecurityContextHolder.getContext()
                        .setAuthentication(authentication);


        filterChain.doFilter(request,response);



    }



}

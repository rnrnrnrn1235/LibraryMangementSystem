package com.ghada.library.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ghada.library.Security.JwtService;
import com.ghada.library.Security.SyncService;
import com.mongodb.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

   // private final UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SyncService sync;
    
   // public JwtFilter(JwtService jwtService){
        //this.userDetailsService = userDetailsService;
      //  this.jwtService = jwtService;
   // }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain) throws ServletException, IOException{

        //test 1 for seeing if runtime has reached the JWT filter
            final String authHeader = request.getHeader("Authorization");

            //header doesn't exist or doesn't start with "bearer"
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return; }
            //extract token & if token is empty return
            final String JwtToken = authHeader.substring(7);
            if (JwtToken.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Empty JWT Token");
            return;
        }
            String username;
            try {
            //username extraction
            username = jwtService.extractUsername(JwtToken);
        } catch (Exception e) {
            // Invalid token detection
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return;
        }
            //if username doesn't exist
            if(username == null || SecurityContextHolder.getContext()
                .getAuthentication() != null){
            filterChain.doFilter(request, response);
            return;
            }
            //role extraction
            List<String> roles = jwtService.extractRole(JwtToken);
            List<SimpleGrantedAuthority> authorities =
                    roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            //id extraction
            String id = jwtService.extractId(JwtToken);
                         
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );
            
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
            sync.syncUserIfNotExists(username, roles, id);
            filterChain.doFilter(request, response);
        //test 2 for getting auth
            System.out.println("AUTH AFTER FILTER = " + 
        SecurityContextHolder.getContext().getAuthentication());
    }

}

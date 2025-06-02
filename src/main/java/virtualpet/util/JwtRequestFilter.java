package virtualpet.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import virtualpet.services.CustomUserDetailsService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

  /*  @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // No interceptar login/register
        if (path.startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("🔎 JwtRequestFilter: interceptando " + path);

        // Mostrar headers
        System.out.println("📨 Headers:");
        request.getHeaderNames().asIterator().forEachRemaining(
                name -> System.out.println(name + ": " + request.getHeader(name))
        );

        // Obtener token desde Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            authHeader = request.getHeader("authorization"); // fallback por si llega en minúsculas
        }

        String jwt = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
                System.out.println("✅ Token recibido: " + jwt);
                System.out.println("👤 Usuario extraído del token: " + username);
            } catch (Exception e) {
                System.out.println("❌ Error al extraer username del token: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Encabezado Authorization no encontrado o mal formado.");
        }

        // Verificar contexto actual de autenticación
        System.out.println("📌 Ya hay autenticación?: " + SecurityContextHolder.getContext().getAuthentication());

        if (username != null &&
                (SecurityContextHolder.getContext().getAuthentication() == null ||
                        SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("✅ UserDetails cargado: " + userDetails.getUsername());

            boolean isValid = jwtUtil.validateToken(jwt, userDetails);
            System.out.println("🔍 ¿Token válido?: " + isValid);

            if (isValid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("🔐 Autenticación establecida correctamente para: " + userDetails.getUsername());
            } else {
                System.out.println("⛔ Token no válido para el usuario.");
            }
        }

        filterChain.doFilter(request, response);
    } */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        username = jwtUtil.extractUsername(jwt);
        System.out.println("🔐 Token recibido: " + jwt);
        System.out.println("📧 Usuario extraído del token: " + username);
        System.out.println("🔍 Ya hay autenticación?: " + SecurityContextHolder.getContext().getAuthentication());

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("✅ UserDetails cargado: " + userDetails.getUsername());

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("🔓 Autenticación establecida correctamente para: " + username);
            } else {
                System.out.println("❌ Token no válido para el usuario.");
            }
        }

        filterChain.doFilter(request, response);
    }


}
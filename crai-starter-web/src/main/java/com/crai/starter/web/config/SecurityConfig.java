package com.crai.starter.web.config;

import com.crai.starter.web.handler.KeycloakLogoutHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@Slf4j
public class SecurityConfig {

    @Autowired
    SecurityProperties securityProperties;


    @Autowired
    JwtAuthConverter jwtAuthConverter;

    @Autowired
    KeycloakLogoutHandler keycloakLogoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(Customizer.withDefaults());

        if(securityProperties.enabled) {
            log.error("OIDC SECURITY PASS");
            http.authorizeHttpRequests(req -> req.requestMatchers("/actuator","/unauthenticated", "/oauth2/**", "/login/**").permitAll()
                    .anyRequest().fullyAuthenticated());

            // WEB authentication
            http
                    .oauth2Client(Customizer.withDefaults())
                    .oauth2Login((oauth2Login) -> oauth2Login
                            .userInfoEndpoint((userInfo) -> userInfo
                                    .userAuthoritiesMapper(grantedAuthoritiesMapper())
                            )
                    );
            // JWT authentication
            http.oauth2ResourceServer(t ->
                    t.jwt(configure -> configure.jwtAuthenticationConverter(jwtAuthConverter)));

            http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
//            http.logout(logout -> logout.logoutSuccessUrl("http://localhost:18080/realms/crai-realm/protocol/openid-connect/logout?redirect_uri=http://localhost:8081/"));
            http.logout(logout -> {
                // if we want change url logout
//                logout.logoutUrl("/pepito");
                logout.addLogoutHandler(keycloakLogoutHandler);
                    }
            );

        } else {
            log.error("DISABLED SECURITY");
            // http.csrf(csrf -> csrf.disable())
            http
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        }
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

            authorities.forEach((authority) -> {
                GrantedAuthority mappedAuthority;

                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
                    mappedAuthority = new OidcUserAuthority(
                            "OIDC_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
                } else if (authority instanceof OAuth2UserAuthority) {
                    OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
                    mappedAuthority = new OAuth2UserAuthority(
                            "OAUTH2_USER", userAuthority.getAttributes());
                } else {
                    mappedAuthority = authority;
                }

                mappedAuthorities.add(mappedAuthority);
            });
            mappedAuthorities.stream().forEach(x -> log.debug("ROLE OF USER ------->{}", x.getAuthority()));
            return mappedAuthorities;
        };

    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        if (jwt.getClaim("realm_access") != null) {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            ObjectMapper mapper = new ObjectMapper();
            List<String> keycloakRoles = mapper.convertValue(realmAccess.get("roles"), new TypeReference<List<String>>() {});
            List<GrantedAuthority> roles = new ArrayList<>();

            for (String keycloakRole : keycloakRoles) {
                roles.add(new SimpleGrantedAuthority("ROLE_" + keycloakRole));
            }
            roles.stream().forEach(x -> log.debug("ROLES JWT ------>{}",x.getAuthority()));
            return roles;
        }
        return new ArrayList<>();
    }
}
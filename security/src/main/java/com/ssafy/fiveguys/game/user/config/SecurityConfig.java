package com.ssafy.fiveguys.game.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fiveguys.game.user.auth.JwtTokenProvider;
import com.ssafy.fiveguys.game.user.filter.JwtAuthenticationFilter;
import com.ssafy.fiveguys.game.user.handler.OAuth2LoginFailureHandler;
import com.ssafy.fiveguys.game.user.handler.OAuth2LoginSuccessHandler;
import com.ssafy.fiveguys.game.user.repository.UserRepositoy;
import com.ssafy.fiveguys.game.user.service.CustomOAuth2UserService;
import com.ssafy.fiveguys.game.user.service.GameUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // @Secure, @PreAuthorize, @PostAuthorize 사용가능
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final ObjectMapper objectMapper;
    private final GameUserDetailsService gameUserDetailsService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserRepositoy userRepositoy;
    private final JwtTokenProvider jwtTokenProvider;
    private static final String[] swaggerURL = {
        "/api/**", "/graphiql", "/graphql",
        "/swagger-ui/**", "/api-docs", "/swagger-ui.html",
        "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html"
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 기본 세팅
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()));

        // JWT 토큰 인증 설정
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // URL별 권한 설정
        http
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/error").permitAll()
                        .requestMatchers("/users/signup", "/login").permitAll()
                        .requestMatchers(swaggerURL).permitAll()
                        .anyRequest().authenticated()
                );
//
//        // Oauth 로그인 설정
//        http
//                .oauth2Login(oauth2Login->oauth2Login
//                        .successHandler(oAuth2LoginSuccessHandler)
//                        .failureHandler(oAuth2LoginFailureHandler)
//                        .userInfoEndpoint(userInfoEndpoint->
//                                userInfoEndpoint.userService(customOAuth2UserService)));
//
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager 설정 후 등록
     * PasswordEncoder를 사용하는 AuthenticationProvider 지정 (PasswordEncoder는 위에서 등록한 PasswordEncoder 사용)
     * FormLogin(기존 스프링 시큐리티 로그인)과 동일하게 DaoAuthenticationProvider 사용
     * UserDetailsService는 커스텀 LoginService로 등록
     * 또한, FormLogin과 동일하게 AuthenticationManager로는 구현체인 ProviderManager 사용(return ProviderManager)
     */
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(gameUserDetailsService);
//        return new ProviderManager(provider);
//    }
//
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}

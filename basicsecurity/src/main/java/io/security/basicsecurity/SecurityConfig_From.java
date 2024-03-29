//package io.security.basicsecurity;
//
//
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig_From extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    UserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated();
//
//        http.formLogin();
////            //.loginPage("/loginPage")
////            .defaultSuccessUrl("/")
////            .failureUrl("/login")
////            .usernameParameter("userId")
////            .passwordParameter("passwd")
////            .loginProcessingUrl("/login_proc")
////            .successHandler(new AuthenticationSuccessHandler() {
////                @Override
////                public void onAuthenticationSuccess(HttpServletRequest request,
////                    HttpServletResponse response, Authentication authentication)
////                    throws IOException, ServletException {
////                    System.out.println("authentication : "+authentication.getName());
////                    response.sendRedirect("/");
////                }
////            })
////            .failureHandler(new AuthenticationFailureHandler() {
////                @Override
////                public void onAuthenticationFailure(HttpServletRequest request,
////                    HttpServletResponse response, AuthenticationException exception)
////                    throws IOException, ServletException {
////                    System.out.println("exception : "+exception.getMessage());
////                    response.sendRedirect("/login");
////                }
////            })
////            .permitAll(); // loginPage 경로로 접근하는 모든 사용자들은 인증 받지않아도 접근가능하게 함
//        http.logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/loginPage")
//                .addLogoutHandler(new LogoutHandler() {
//                    @Override
//                    public void logout(HttpServletRequest request, HttpServletResponse response,
//                                       Authentication authentication) {
//                        HttpSession session = request.getSession();
//                        session.invalidate();
//
//                    }
//                })
//                .logoutSuccessHandler(new LogoutSuccessHandler() {
//                    @Override
//                    public void onLogoutSuccess(HttpServletRequest request,
//                                                HttpServletResponse response, Authentication authentication)
//                            throws IOException, ServletException {
//                        response.sendRedirect("/login");
//                    }
//                })
//                .deleteCookies("remember-me");
//
//        http.rememberMe()
//                .rememberMeParameter("remember")
//                .tokenValiditySeconds(3600)
//                .userDetailsService(userDetailsService);
//
//        http.sessionManagement()
//                .sessionFixation()
//                .changeSessionId()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false);
//
//    }
//}

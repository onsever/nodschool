package com.onurcansever.nodschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {

    /**
     * Overriding configuration bean for Spring Security.
     * @param http
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf((csrf) -> csrf
                        .ignoringRequestMatchers(mvcMatcherBuilder.pattern("/sendContactForm")).ignoringRequestMatchers(mvcMatcherBuilder.pattern("/createUser")))
                .authorizeHttpRequests((requests) -> requests.requestMatchers(mvcMatcherBuilder.pattern("/dashboard")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayMessages")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/closeMessage/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayProfile")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/updateProfile")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/createUser")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayCourses")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayClasses")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/addNewClass")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayStudents")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/deleteClass")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/addStudent")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/deleteStudent")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/viewStudents")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/addNewCourse")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/addStudentToCourse")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/deleteStudentFromCourse")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/student/**")).hasRole("STUDENT")
                        .requestMatchers(mvcMatcherBuilder.pattern("")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/home")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/holidays/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/contact")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/sendContactForm")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/courses")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/about")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/assets/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/logout")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/register")).permitAll())
                .formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true).permitAll())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

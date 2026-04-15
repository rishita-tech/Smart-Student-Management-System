//package com.cwm.studentmanagement.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
///*
// * Copyright (c) 2026 Mahesh Shet
// * Licensed under the MIT License.
// */
//
//@Configuration
//@EnableWebSecurity
//public class SpringConfig {
//	
//	private static final String[] PUBLIC_PATH = {
//			"/login",
//			"/css/**",
//			"/images/**",
//			"/js/**",
//			"/error"
//	};
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
//		http
//			.authorizeHttpRequests(auth -> auth
//				.requestMatchers(PUBLIC_PATH).permitAll()
//				.anyRequest().authenticated()
//			)
//			.formLogin(form -> form
//					.loginPage("/login")
//					.loginProcessingUrl("/login")
//					.defaultSuccessUrl("/dashboard", true)
//					.permitAll())
//			.logout(logout -> logout
//					.logoutUrl("/logout")
//					.logoutSuccessUrl("/login?logout")
//					.invalidateHttpSession(true)
//					.clearAuthentication(true)
//					.deleteCookies("JSESSIONID")
//					.permitAll());
//		
//		return http.build();
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//}
package com.cwm.studentmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    // 🔥 FORCE LOGIN USER HERE
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
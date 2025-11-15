package demo.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    public UserDetailsManager userDetailManger(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/showLoginPage", "/authenticateTheUser").permitAll()
                        .requestMatchers("/dashboard").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
                        .requestMatchers("/management").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers("/administration").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/showLoginPage")
                        .loginProcessingUrl("/authenticateTheUser")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(handler -> handler
                        .accessDeniedPage("/accessDenied")
                );

        return http.build();
    }

}

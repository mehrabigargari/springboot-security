package demo.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Config {

    @Bean
    public InMemoryUserDetailsManager userDetailManger(){
        UserDetails Masoud = User.builder().username("masoud").password("{noop}123").roles("admin").build();
        UserDetails Farshid = User.builder().username("farshid").password("{noop}123").roles("manager").build();
        UserDetails Sadeg = User.builder().username("sadeg").password("{noop}123").roles("employee").build();

        return new InMemoryUserDetailsManager(Masoud,Farshid,Sadeg);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/showLoginPage", "/authenticateTheUser").permitAll()
                        .requestMatchers("/dashboard").hasAnyRole("employee","manager","admin")
                        .requestMatchers("/management").hasAnyRole("manager","admin")
                        .requestMatchers("/administration").hasAnyRole("admin")
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

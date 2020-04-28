package pl.betweenthelines.szopp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.betweenthelines.szopp.security.UserDetailsServiceImpl;

import static org.springframework.http.HttpMethod.*;
import static pl.betweenthelines.szopp.security.domain.RoleName.*;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/**").hasRole(ADMIN.name())

                .antMatchers(POST, "/categories/**").hasAnyRole(ADMIN.name(), STAFF.name())
                .antMatchers(PUT, "/categories/**").hasAnyRole(ADMIN.name(), STAFF.name())
                .antMatchers(DELETE, "/categories/**").hasAnyRole(ADMIN.name(), STAFF.name())
                .antMatchers(GET, "/categories/**").permitAll()

                .antMatchers(GET, "/products/**").permitAll()
                .antMatchers(POST, "/products/**").hasAnyRole(ADMIN.name(), STAFF.name())
                .antMatchers(PUT, "/products/**").hasAnyRole(ADMIN.name(), STAFF.name())
                .antMatchers(DELETE, "/products/**").hasAnyRole(ADMIN.name(), STAFF.name())

                .antMatchers(GET, "/customers").hasAnyRole(ADMIN.name(), STAFF.name())
                .antMatchers("/customers/my-data").permitAll()
                .antMatchers(GET, "/customers/**/orders").hasRole(REGISTERED_USER.name())
                .antMatchers(GET, "/customers/**").hasAnyRole(ADMIN.name(), STAFF.name())

                .antMatchers("/cart/**").access("hasRole('REGISTERED_USER') || isAnonymous()")
                .antMatchers("/orders/**").hasAnyRole(ADMIN.name(), STAFF.name())

                .antMatchers("/auth/login").anonymous()
                .antMatchers("/auth/register").anonymous()
                .antMatchers("/auth/logout").authenticated()
//                .antMatchers("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

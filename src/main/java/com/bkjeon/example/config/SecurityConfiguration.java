package com.bkjeon.example.config;

import com.bkjeon.example.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    final String className = this.getClass().getCanonicalName();

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        log.info("MCI > " + className + " -> authenticationProvider()");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        log.info("MCO > " + className + " -> authenticationProvider()");
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        log.info("MCI > " + className + " -> configure() | Param: AuthenticationManagerBuilder auth");
        auth.authenticationProvider(this.authenticationProvider(userService));
        log.info("MCO > " + className + " -> configure() | Param: AuthenticationManagerBuilder auth");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("MCI > " + className + " -> configure() | Param: HttpSecurity http");
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/logging").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/home").hasAuthority("ADMIN") // only ADMIN users can access hm/home
            .anyRequest()
                .authenticated()
                .and().csrf().disable()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .usernameParameter("loginId")
                .passwordParameter("password")
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
        log.info("MCI > " + className + " -> configure() | Param: HttpSecurity http");
    }

    /**
     * Ignore assets
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        log.info("MCI > " + className + " -> configure() | Param: WebSecurity web");
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
        log.info("MCI > " + className + " -> configure() | Param: WebSecurity web");
    }

}
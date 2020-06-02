package com.api.demo.grid.config;


import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/grid/sign-up",
            "/grid/login",
            "/grid/logout",
            "/grid/all",
            "/grid/game",
            "/grid/genre",
            "/grid/name",
            "/grid/developer",
            "/grid/publisher",
            "/grid/sell-listing",
            "/grid/search",
            "/grid/public/user-info",
            "/grid/public/user-info",
            "/grid/auctions",
    };

    private static final String[] ADMIN_WHITELIST = {
            "/grid/add-game",
            "/grid/add-developer",
            "/grid/add-genre",
            "/grid/add-publisher",
    };

    private static final String[] USER_WHITELIST = {
            "/grid/private/user-info",
            "/grid/create-auction",
            "/grid/create-bidding",
            "/grid/user",
            "/grid/remove-user",
    };

    @Autowired
    private AuthenticationEntryPoint mAuthEntryPoint;

    @Autowired
    private CustomUserDetailsService mUserDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(mUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/**",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO -> verify roles here
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(USER_WHITELIST).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(ADMIN_WHITELIST).hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(mAuthEntryPoint)
                .and().logout().logoutUrl("/grid/logout");
    }
}

package com.api.demo.grid.config;


import com.api.demo.grid.repository.UserRepository;
import com.api.demo.grid.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
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

    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private static final String SELL_ENDPOINT = "/grid/sell-listing",

    private static final String[] GET_AUTH_WHITELIST = {
            "/grid/logout",
            "/grid/games/all",
            "/grid/games/genre",
            "/grid/games/name",
            "/grid/games/developer",
            "/grid/games/publisher",
            "/grid/games/game",
           SELL_ENDPOINT,
            "/grid/public/user",
            "/grid/reviews/user",
            "/grid/reviews/game",
            "/grid/reviews/all",
            "/grid/auction"
    };

    private static final String[] POST_AUTH_WHITELIST = {
            "/grid/sign-up",
            "/grid/login",
            "/grid/games/search",
            "/grid/gamekey",
           SELL_ENDPOINT,
            "/grid/buy-listing",
            "/grid/wishlist",
            "/grid/reviews/game",
            "/grid/reviews/user"
    };

    private static final String[] DELETE_USER_WHITELIST = {
            "/grid/user",
           SELL_ENDPOINT
    };

    private static final String[] POST_USER_WHITELIST = {
            "/grid/auction",
            "/grid/bidding",
    };

    private static final String[] POST_ADMIN_WHITELIST = {
            "/grid/games/game",
            "/grid/games/developer",
            "/grid/games/genre",
            "/grid/games/publisher",
    };

    private static final String[] PUT_USER_WHITELIST = {
            "/grid/private/user",
            "/grid/private/funds",
    };

    private static final String[] GET_USER_WHITELIST = {
            "/grid/private/user",
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

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, GET_AUTH_WHITELIST)
                .permitAll()
                .antMatchers(HttpMethod.GET, GET_USER_WHITELIST)
                .hasAnyAuthority(USER, ADMIN)
                .antMatchers(HttpMethod.POST, POST_AUTH_WHITELIST)
                .permitAll()
                .antMatchers(HttpMethod.POST, POST_USER_WHITELIST)
                .hasAnyAuthority(USER, ADMIN)
                .antMatchers(HttpMethod.POST, POST_ADMIN_WHITELIST)
                .hasAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT, PUT_USER_WHITELIST)
                .hasAnyAuthority(USER, ADMIN)
                .antMatchers(HttpMethod.DELETE, DELETE_USER_WHITELIST)
                .hasAnyAuthority(USER, ADMIN)
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(mAuthEntryPoint)
                .and().logout().logoutUrl("/grid/logout");


    }
}

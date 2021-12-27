package uz.pdp.uz.app_news_board.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.uz.app_news_board.security.JwtFilter;
import uz.pdp.uz.app_news_board.service.AuthService;

@EnableWebSecurity
@Configuration
public class ProjectSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthService authService;

    @Autowired
    JwtFilter jwtFilter;



    //authenticationManagerBean() Parol va Login larni solishtirib ketadi
    //AuthService da chaqirganman
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/register",
                        "/api/auth/login").permitAll()
                .anyRequest().authenticated();


        //Filterni chaqirib qo'yish
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        //Session ni ushamasligi
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }




}

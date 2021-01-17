package pl.pisarkiewicz.Global.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Resource(name = "myAppUserDetailsService")
  private UserDetailsService userDetailsService;

  @Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

    auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN", "USER");
    auth.inMemoryAuthentication().withUser("patient").password("{noop}patient").roles("PATIENT");
    auth.inMemoryAuthentication().withUser("doctor").password("{noop}doctor").roles("DOCTOR");

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CharacterEncodingFilter filter = new CharacterEncodingFilter();
    filter.setEncoding("UTF-8");
    filter.setForceEncoding(true);
    http.addFilterBefore(filter, CsrfFilter.class);

    http.authorizeRequests()
        .antMatchers("/register")
        .access("isAnonymous()")
        .antMatchers("/login")
        .access("isAnonymous()")
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .usernameParameter("login")
        .passwordParameter("password")
        .failureForwardUrl("/login.html?error")
        .and()
        .logout()
        .logoutSuccessUrl("/login.html?logout")
        .and()
        .exceptionHandling()
        .accessDeniedPage("/accessDenied");
  }
}

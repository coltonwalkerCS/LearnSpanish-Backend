package a.i.learn.spanish.com.learnspanishbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration - Marks the class as a source of bean definitions for the application context.
// by marking with configuration your telling spring that this class contains bean definitions

//  @Bean - Objects that are managed by the Spring inversion of control container.

// @EnableWebSecurity - Enables Spring security web security support and provides
// the Spring MVC integration. Allows the addition of security feature to the application.

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain - Configures the security filter chain that spring
    // security uses to handle web requests.
    // http
    // .authorizeHttpRequests((requests) -> requests ...)
    //    - Configures authorization for HTTP request

    //  .requestMatchers("/", "/home").permitAll()
    //    - Specifies that request to "/" and "/home" do not
    //      require authentication, making them assessable to anyone

    //  .anyRequest().authenticated()
    //    - Indicates that any request not matched by the previous
    //      matchers require the user to be authenticated

    //  .formLogin((form) -> form ...)
    //    - Configures form-based authentications

    //  .loginPage("/login").permitAll()
    //    - Specifies the path to the login page and allows unrestricted
    //      access to it, enabling users to perform authentication.

    //  .logout((logout) -> logout.permitAll())
    //    - Configures logout functionality and allows unrestricted
    //      access to the logout feature

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/", "/home").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());
        http
                .csrf(AbstractHttpConfigurer::disable)
                // Authorize requests
                .authorizeHttpRequests(auth -> auth
                        // Allow requests to all endpoints
                        .anyRequest().permitAll()
                )
                // Configure logout
                .logout(LogoutConfigurer::permitAll
                );
        return http.build();
    }

    // UserDetailsService - This method configures an in-memory user
    // store with a single user. This is a simple way to add a user
    // for authentication without needing a database or external system.

    // User.withDefaultPasswordEncoder() - is a user builder that specifies the
    // password encoder. Note: Using the default password encoder is not recommended
    // for production as it is insecure.

    // .username("user").password("password").roles("USER")
    //   - sets up a user wit the username "user", password "password", and assigns
    //     the role "USER" to this account.

    // The "InMemoryUserDetailsManager" manages user details in-memory and is configured
    // with the user created above. It's a simple way to manage users without needing a
    // persistent storage.

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
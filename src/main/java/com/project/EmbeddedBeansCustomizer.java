package com.project;

import com.project.extensions.SmartLocaleResolver;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.Locale;

/**
 * Created by rohitgupta on 12/14/15.
 */

@Configuration
public class EmbeddedBeansCustomizer {

    @Configuration
    public static class ExtendedBeansRegistry {
        @Bean
        public Filter shallowEtagHeaderFilter() {
            return new ShallowEtagHeaderFilter();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder;
        }

        @Bean
        public LocaleResolver localeResolver() {
            SmartLocaleResolver localeResolver = new SmartLocaleResolver();
            localeResolver.setDefaultLocale(Locale.US);
            localeResolver.setCookieSecure(true);
            localeResolver.setCookieHttpOnly(true);
            return localeResolver;
        }
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Configuration
    @DependsOn(value = {"passwordEncoder"})
    public static class ExtendedWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            MutableDateTime mutableDateTime = new MutableDateTime(DateTime.now());
            mutableDateTime.addDays(3);

            http
                    .authorizeRequests().antMatchers("/**?lang=**", "**/assets/**", "**/webjars/**").permitAll().and().formLogin()
                    .loginPage("/login").failureUrl("/login?error").permitAll().and()
                    .logout().deleteCookies("remember-me", "JSESSIONID").invalidateHttpSession(true).permitAll();


            http
                    .headers()
                    .httpStrictTransportSecurity().and().xssProtection().and()
                    .addHeaderWriter(new StaticHeadersWriter("Server", "Secured App Servers"))
                    .addHeaderWriter(new StaticHeadersWriter("Expires", mutableDateTime.toString()))
                    .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS"))
                    .addHeaderWriter(new StaticHeadersWriter("Keep-Alive", "5000"))
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));


            http
                    .csrf().disable().headers().frameOptions().disable().cacheControl().disable();
            //.csrf().ignoringAntMatchers("/console").and().headers().frameOptions();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .jdbcAuthentication()
                    .passwordEncoder(passwordEncoder)
                    .dataSource(dataSource)
                    .withDefaultSchema()
                    .withUser("user").password(passwordEncoder.encode("password")).roles("USER").and()
                    .withUser("admin").password(passwordEncoder.encode("password")).roles("USER", "ADMIN");
        }


    }

    @Configuration
    public static class ExtendedWebMVCConfigurerAdapter extends WebMvcConfigurerAdapter {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("login");
        }

//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            if (!registry.hasMappingForPattern("/webjars/**"))
//                registry.addResourceHandler("/webjars/**")
//                        .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        }


    }
}
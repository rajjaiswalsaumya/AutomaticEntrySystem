package com.project.extensions;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by rohitgupta on 1/9/16.
 */

/**
 * {@link LocaleResolver} implementation based on Spring's built-in
 * {@link CookieLocaleResolver} that falls back to Spring's
 * {@link SessionLocaleResolver} when no cookie is found.
 * This allows users with cookies disabled to still have a custom
 * Locale for the duration of their session.
 *
 * @author Joris Kuipers
 */
public class SmartLocaleResolver extends CookieLocaleResolver {

    private SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();

    @Override
    protected Locale determineDefaultLocale(HttpServletRequest request) {
        return sessionLocaleResolver.resolveLocale(request);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        super.setLocale(request, response, locale);
        sessionLocaleResolver.setLocale(request, response, locale);
    }

    @Override
    public void setDefaultLocale(Locale defaultLocale) {
        sessionLocaleResolver.setDefaultLocale(defaultLocale);
    }
}

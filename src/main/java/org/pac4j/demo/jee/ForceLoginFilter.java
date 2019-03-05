package org.pac4j.demo.jee;

import org.pac4j.core.client.Client;
import org.pac4j.core.config.ConfigSingleton;
import org.pac4j.core.context.JEEContext;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.http.adapter.JEEHttpActionAdapter;
import org.pac4j.jee.filter.AbstractConfigFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForceLoginFilter extends AbstractConfigFilter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    protected void internalFilter(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        final JEEContext context = new JEEContext(request, response);
        final Client client = ConfigSingleton.getConfig().getClients().findClient(request.getParameter(Pac4jConstants.DEFAULT_CLIENT_NAME_PARAMETER)).orElseThrow(() -> new TechnicalException("No client found"));
        HttpAction action;
        try {
            action = (HttpAction) client.redirect(context).get();
        } catch (final HttpAction e) {
            action = e;
        }
        JEEHttpActionAdapter.INSTANCE.adapt(action, context);
    }
}

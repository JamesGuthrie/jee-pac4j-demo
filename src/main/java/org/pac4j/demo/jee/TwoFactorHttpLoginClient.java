package org.pac4j.demo.jee;

import java.util.Optional;
import org.pac4j.core.client.DirectClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.http.ForbiddenAction;
import org.pac4j.core.exception.http.NoContentAction;
import org.pac4j.core.exception.http.StatusAction;
import org.pac4j.core.exception.http.UnauthorizedAction;
import org.pac4j.core.util.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwoFactorHttpLoginClient extends DirectClient<TwoFactorCredentials> {

  private String loginUrl;

  private static final Logger logger = LoggerFactory.getLogger(TwoFactorHttpLoginClient.class);

  public TwoFactorHttpLoginClient(String loginUrl,
      Authenticator<TwoFactorCredentials> usernamePasswordAuthenticator) {
    this.loginUrl = loginUrl;
    this.setAuthenticator(usernamePasswordAuthenticator);
  }

  @Override
  protected void clientInit() {
    logger.info("client init");
    this.defaultCredentialsExtractor(new JsonBodyExtractor<>(TwoFactorCredentials.class));
  }

  protected Optional<TwoFactorCredentials> retrieveCredentials(WebContext context) {
    logger.info("retrieve credentials");
    CommonHelper.assertNotNull("credentialsExtractor", this.getCredentialsExtractor());
    CommonHelper.assertNotNull("authenticator", this.getAuthenticator());

    try {
      Optional<TwoFactorCredentials> credentials = this.getCredentialsExtractor().extract(context);
      logger.debug("usernamePasswordCredentials: {}", credentials);
      if (!credentials.isPresent()) {
        throw UnauthorizedAction.INSTANCE;
      } else {
        TwoFactorCredentials c = credentials.get();
        if (c.getUsername().equals("2fabob")) {
          if (c.getToken() == null || c.getToken().isEmpty()) {
            throw new StatusAction(422);
          } else {
            this.getAuthenticator().validate(c, context);
            context.setResponseHeader("X-Foo-Bar", "Baz");
            throw NoContentAction.INSTANCE;
          }
        } else {
          this.getAuthenticator().validate(c, context);
          context.setResponseHeader("X-Foo-Bar", "Baz");
          throw NoContentAction.INSTANCE;
        }
      }
    } catch (CredentialsException e) {
      throw ForbiddenAction.INSTANCE;
    }
  }
}

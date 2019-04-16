package org.pac4j.demo.jee;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleAuthenticator implements Authenticator<TwoFactorCredentials> {

  private static final Logger logger = LoggerFactory.getLogger(SimpleAuthenticator.class);

  public SimpleAuthenticator() {
  }

  @Override
  public void validate(TwoFactorCredentials credentials, WebContext webContext) {
    logger.info("Validating credentials {}", credentials);
    if (credentials == null) {
      throw new CredentialsException("No credential");
    } else {
      String username = credentials.getUsername();
      String password = credentials.getPassword();
      String token = credentials.getToken();
      if (username.equals("bob")) {
        CommonProfile profile = new CommonProfile();
        profile.setId(username);
        profile.addAttribute("username", username);
        credentials.setUserProfile(profile);
        logger.info("set profile: {}", profile);
      } else if (username.equals("2fabob")) {
        if (token == null) {
          throw new CredentialsException("No token provided");
        } else {
          CommonProfile profile = new CommonProfile();
          profile.setId(username);
          profile.addAttribute("username", username);
          credentials.setUserProfile(profile);
          logger.info("set profile: {}", profile);
        }
      } else {
        throw new CredentialsException("Username : '" + username + "' not known");
      }
    }
  }
}

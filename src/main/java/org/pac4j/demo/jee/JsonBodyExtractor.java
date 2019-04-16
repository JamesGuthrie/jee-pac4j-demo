package org.pac4j.demo.jee;

import com.google.appengine.repackaged.com.google.gson.Gson;
import java.util.Optional;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.extractor.CredentialsExtractor;

public class JsonBodyExtractor<T extends Credentials> implements CredentialsExtractor<T> {

  private static Gson gson = new Gson();

  private Class<T> clazz;

  JsonBodyExtractor(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public Optional<T> extract(WebContext webContext) {
    return Optional.ofNullable(gson.fromJson(webContext.getRequestContent(), this.clazz));
  }

}

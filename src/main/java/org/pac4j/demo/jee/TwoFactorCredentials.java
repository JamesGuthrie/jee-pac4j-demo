package org.pac4j.demo.jee;

import java.util.Objects;
import org.pac4j.core.credentials.Credentials;

public class TwoFactorCredentials extends Credentials {

  private String username;
  private String password;
  private String token;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "TwoFactorCredentials{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", token='" + token + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TwoFactorCredentials that = (TwoFactorCredentials) o;
    return Objects.equals(getUsername(), that.getUsername()) &&
        Objects.equals(getPassword(), that.getPassword()) &&
        Objects.equals(getToken(), that.getToken());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUsername(), getPassword(), getToken());
  }
}

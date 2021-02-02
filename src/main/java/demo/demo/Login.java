package demo.demo;

public class Login {
  private String EmailId, password;

  public Login(String EmailId, String password) {
    this.EmailId = EmailId;
    this.password = password;
  }

  public String getEmailId() {
    return this.EmailId;
  }

  public void setEmailId(String EmailId) {
    this.EmailId = EmailId;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}

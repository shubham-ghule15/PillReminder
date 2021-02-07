package demo.demo;

public class ResetPassword {
  String oldpassword, newpassword;

  public ResetPassword(String oldpassword, String newpassword) {
    this.oldpassword = oldpassword;
    this.newpassword = newpassword;
  }

  public String getOldpassword() {
    return this.oldpassword;
  }

  public void setOldpassword(String oldpassword) {
    this.oldpassword = oldpassword;
  }

  public String getNewpassword() {
    return this.newpassword;
  }

  public void setNewpassword(String newpassword) {
    this.newpassword = newpassword;
  }

}

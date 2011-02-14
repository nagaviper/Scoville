package usi.poc.business.itf;

public class LoginInformation {
	
	private String mail;
	private String password;
	
	public LoginInformation() {

	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Login [mail=" + mail + ", password=" + password + "]";
	}

}

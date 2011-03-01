package usi.poc.business.itf;

public class LoginInformation implements IQuizzObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7709143894886984452L;
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

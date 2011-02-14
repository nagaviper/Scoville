package usi.poc.business.itf;

public class AdminUserRequest {
	
	private String user_mail;
	private String authentication_key;
	
	public AdminUserRequest() {

	}

	public String getUser_mail() {
		return user_mail;
	}

	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}

	public String getAuthentication_key() {
		return authentication_key;
	}

	public void setAuthentication_key(String authentication_key) {
		this.authentication_key = authentication_key;
	}

	@Override
	public String toString() {
		return "AdminRequest [user_mail=" + user_mail + ", authentication_key=" + authentication_key + "]";
	}
	
}

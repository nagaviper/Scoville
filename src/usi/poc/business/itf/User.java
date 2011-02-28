package usi.poc.business.itf;


public class User implements IQuizzObject {
	
	private static final long serialVersionUID = 3884899046258970447L;

	private String id;
	private String mail;
	private String firstname;
	private String lastname;
	private String password;
	
	public User() {

	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", mail=" + mail + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", password=" + password + "]";
	}

}

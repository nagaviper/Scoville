package usi.poc.business.itf;

public class AdminGame {

	private String authentication_key;
	private String parameters;
	
	public AdminGame() {

	}
	
	public String getAuthentication_key() {
		return authentication_key;
	}
	
	public void setAuthentication_key(String authentication_key) {
		this.authentication_key = authentication_key;
	}
	
	public String getParameters() {
		return parameters;
	}
	
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "Game [authentication_key=" + authentication_key + ", parameters=" + parameters + "]";
	}
	
}

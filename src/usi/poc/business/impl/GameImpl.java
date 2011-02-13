package usi.poc.business.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import usi.poc.business.itf.IGame;
import usi.poc.business.itf.User;

@Service
public class GameImpl implements IGame {

	private Connection conn;
	private ObjectMapper mapper = new ObjectMapper();

	public GameImpl() {
		try {
			Class.forName("org.postgresql.Driver");				
			Properties props = new Properties();
			props.setProperty("user","postgres");
			props.setProperty("password","root");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost/TestUSI", props);
			System.out.println("DataBase connection OK");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createUser(String jsonUser) {
		try {
			User user = mapper.readValue(jsonUser, User.class);

			Statement st = conn.createStatement();
			StringBuilder query = new StringBuilder("INSERT INTO users(email, firstname, lastname, passw) VALUES ('");
			query.append(user.getMail())
				 .append("', '")
				 .append(user.getFirstname())
				 .append("', '")
				 .append(user.getLastname())
				 .append("', '")
				 .append(user.getPassword())
 				 .append("');");

			st.execute(query.toString());

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println("User creation requested for user "+ jsonUser + " but the service is not yet implemented...");
	}

	@Override
	public void createGame(String jsonGame) {
		// TODO Auto-generated method stub
		System.out.println("Game creation requested for game "+ jsonGame + " but the service is not yet implemented...");
	}

	@Override
	public String getQuestion(int n) {
		// TODO Auto-generated method stub
		System.out.println("Question " + n + " requested but the service is not yet implemented...");
		return "Question " + n;
	}

}

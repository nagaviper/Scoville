package usi.poc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import usi.poc.business.itf.User;

public class ScoreTable {
	static private Map<Integer, Collection<User>> beforeScoreTable = new HashMap<Integer, Collection<User>>();
	static private Map<Integer, Collection<User>> afterScoreTable = new HashMap<Integer, Collection<User>>();
	static private Collection<User> highScoreTable = new ArrayList<User>();
	
	// TODO : trier les utilisateurs donnés
	static public void setBeforeScoreTable(int score, Collection<User> users) {
		beforeScoreTable.put(score, users);
	}
	
	// Renvoie les 50 joueurs *avant* score
	static public Collection<User> getBeforeScoreTable(int score) {
		return beforeScoreTable.get(score);
	}
	
	// TODO : trier les utilisateurs donnés 
	static public void setAfterScoreTable(int score, Collection<User> users) {
		afterScoreTable.put(score, users);
	}
	
	// Renvoie les 50 joueurs *après* score
	static public Collection<User> getAfterScoreTable(int score) {
		return afterScoreTable.get(score);
	}
	
	static public void setHighScoreTable(Collection<User> users) {
		highScoreTable = users;
	}
	
	// Renvoie jusqu'à 100 meilleurs joueurs
	static public Collection<User> getHighScoreTable() {
		return highScoreTable;
	}
}

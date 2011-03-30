package usi.poc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gemstone.bp.edu.emory.mathcs.backport.java.util.Collections;

import usi.poc.business.itf.User;

public class ScoreTable {
	static private Map<Integer, Collection<User>> beforeScoreTable = new HashMap<Integer, Collection<User>>();
	static private Map<Integer, Collection<User>> afterScoreTable = new HashMap<Integer, Collection<User>>();
	static private Collection<User> highScoreTable = new ArrayList<User>();
	static private UserComparator userComparator = new UserComparator();
	
	public static void setBeforeScoreTable(int score, Collection<User> users) {
		sort(users);
		beforeScoreTable.put(score, users);
	}
	
	// Renvoie les 50 joueurs *avant* score
	public static Collection<User> getBeforeScoreTable(int score) {
		return beforeScoreTable.get(score);
	}
	
	public static void setAfterScoreTable(int score, Collection<User> users) {
		sort(users);
		afterScoreTable.put(score, users);
	}
	
	// Renvoie les 50 joueurs *après* score
	public static Collection<User> getAfterScoreTable(int score) {
		return afterScoreTable.get(score);
	}

	public static void setHighScoreTable(Collection<User> users) {
		sort(users);
		highScoreTable = users;
	}
	
	// Renvoie jusqu'à 100 meilleurs joueurs
	public static Collection<User> getHighScoreTable() {
		return highScoreTable;
	}
	
	private static void sort(Collection<User> users) {
		Collections.sort((List<User>) users, userComparator);
	}
}

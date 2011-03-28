package usi.poc.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import usi.poc.ScoreTable;
import usi.poc.business.itf.User;

import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.query.FunctionDomainException;
import com.gemstone.gemfire.cache.query.NameResolutionException;
import com.gemstone.gemfire.cache.query.QueryInvocationTargetException;
import com.gemstone.gemfire.cache.query.TypeMismatchException;

public class GemUserDAO extends AbstractGemDAO implements IUserDAO {
	@Resource
	Region<String, User> usersCache;

	@Override
	public Map<String, User> getImpl() {
		return usersCache;
	}

	@Override
	public void put(String key, User obj) {
		getImpl().put(key, obj);
	}

	@Override
	public boolean existUser(String sessionId) {
		return usersCache.containsKey(sessionId);
	}

	@Override
	public int getScore(User user) {
		return user.getScore();
	}

	@Override
	public Collection<User> getHundredBestUsers() {
		// TODO : s'assurer que les listes ont bien été calculées
		return ScoreTable.getHighScoreTable();
	}

	@Override
	public Collection<User> getFiftyBeforeUsers(int score) {
		return ScoreTable.getBeforeScoreTable(score);
	}

	@Override
	public Collection<User> getFiftyAfterUsers(int score) {
		return ScoreTable.getAfterScoreTable(score);
	}

	@Override
	public void prepareScoreTable() {
		System.out.println("Calculating high scores tables...");
		try {
			// Gem ne supporte pas "order by" donc en attendant on bypasse sans changer de système de "stockage"...
			// On récupére à la fois jusqu'aux 100 meilleurs et jusqu'à 50 utilisateurs par score
			
			Map<Integer, Collection<User>> scoreTable = new HashMap<Integer, Collection<User>>();
			Collection<User> topUsers = new ArrayList<User>();
			int score = 274;
			long dep = System.currentTimeMillis();
			while (score >= 0) {
				int limit = 100 - topUsers.size();
				if (limit < 50)
					limit = 50;
				System.out.println("AVANT: " + System.currentTimeMillis());
				long d1 = System.currentTimeMillis();
				Collection<User> result = usersCache.query("score = " + score + " limit " + limit);
				long t = System.currentTimeMillis() - d1;
				System.out.println("TEMPS-" + score + " : " + t + "ms");
				if (result != null && result.size() > 0) {
					// On met à jour la table pour ce score
					scoreTable.put(score, result);

					// On complète éventuellement les 100 meilleurs
					for (User user : result) {
						if (topUsers.size() < 100)
							topUsers.add(user);
						else
							break;
					}
				}
				score--;
			}
			long end = System.currentTimeMillis() - dep;
			System.out.println("TEMPS TOTAL : " + end + "ms");
			ScoreTable.setHighScoreTable(topUsers);
			
			// On termine en calculant les listes avant/après pour chaque score
			score = 0;
			while (score <= 274) {
				// Avant
				int beforeScore = score - 1;
				Collection<User> beforeUsers = new ArrayList<User>();
				while (beforeUsers.size() < 50 && beforeScore > 0) {
					Collection<User> collection = scoreTable.get(beforeScore);
					if (collection != null) {
						for (User user : collection) {
							if (beforeUsers.size() < 50)
								beforeUsers.add(user);
							else
								break;
						}
					}
					beforeScore--;
				}
				ScoreTable.setBeforeScoreTable(score, beforeUsers);
				
				// Après
				int afterScore = score + 1;
				Collection<User> afterUsers = new ArrayList<User>();
				while (afterUsers.size() < 50 && afterScore <= 274) {
					Collection<User> collection = scoreTable.get(afterScore);
					if (collection != null) {
						for (User user : collection) {
							if (afterUsers.size() < 50)
								afterUsers.add(user);
							else
								break;
						}
					}
					afterScore++;
				}
				ScoreTable.setAfterScoreTable(score, afterUsers);
				
				score++;
			}
		System.out.println("Scores tables have been calculated.");
		} catch (FunctionDomainException e1) {
			e1.printStackTrace();
		} catch (TypeMismatchException e1) {
			e1.printStackTrace();
		} catch (NameResolutionException e1) {
			e1.printStackTrace();
		} catch (QueryInvocationTargetException e1) {
			e1.printStackTrace();
		}
	}
}

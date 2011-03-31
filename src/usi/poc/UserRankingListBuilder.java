package usi.poc;

import java.util.Collection;

import usi.poc.business.itf.User;
import usi.poc.business.itf.UserRankingList;

public class UserRankingListBuilder {
	public static UserRankingList build(Collection<User> users) {
		if (users != null) {
			int size = users.size();
			
			String [] mail = new String[size];
			int[] scores = new int[size];
			String [] firstname = new String[size];
			String [] lastname = new String[size];
			
			int i = 0;
			for (User user : users) {
				mail[i] = user.getMail();
				scores[i] = user.getScore();
				firstname[i] = user.getFirstname();
				lastname[i] = user.getLastname();
				i++;
			}
			
			UserRankingList userRankingList = new UserRankingList();
			userRankingList.setMail(mail);
			userRankingList.setScores(scores);
			userRankingList.setFirstname(firstname);
			userRankingList.setLastname(lastname);
			return userRankingList;
		}
		return null;
	}
}

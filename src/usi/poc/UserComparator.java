package usi.poc;

import java.util.Comparator;

import usi.poc.business.itf.User;


/**
 * @author nagaviper
 * Ordre décroissant sur score puis croissant sur nom, prénom, email
 *
 */
public class UserComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		if (o1.getScore() > o2.getScore())
			return -1;
		else if (o1.getScore() < o2.getScore())
			return 1;
		else if (o1.getLastname().compareTo(o2.getLastname()) < 0)
			return -1;
		else if (o1.getLastname().compareTo(o2.getLastname()) > 0)
			return 1;
		else if (o1.getFirstname().compareTo(o2.getFirstname()) < 0)
			return -1;
		else if (o1.getFirstname().compareTo(o2.getFirstname()) > 0)
			return 1;
		else if (o1.getMail().compareTo(o2.getMail()) < 0)
			return -1;
		else if (o1.getMail().compareTo(o2.getMail()) > 0)
			return 1;
		else
			return 0;
	}
}

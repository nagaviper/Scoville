package usi.poc;

import usi.poc.business.itf.User;

public class ScoreCalculator {
	public static void calculate(User user, int n, boolean good) {
		if (good) {
			int value = (n <= 5) ? 1 : 5 * (n / 5);
			user.addScore(value + user.getBonus());
			user.incrBonus();
		}
		else
			user.resetBonus();
	}
}

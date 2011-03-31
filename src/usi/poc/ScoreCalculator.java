package usi.poc;

import usi.poc.business.itf.User;

public class ScoreCalculator {
	public static void calculate(User user, int n, boolean good) {
		if (good) {
			int value = (n <= 5) ? 1 : 5 * (n / 5);
			if (user.getLastAnswer() == n - 1)
				value += user.getBonus();
			user.addScore(value);
			user.incrBonus();
		}
		else
			user.resetBonus();
	}
}

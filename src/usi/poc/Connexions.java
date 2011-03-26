package usi.poc;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.catalina.CometEvent;

import usi.poc.business.itf.User;

public class Connexions {
	private static ConcurrentHashMap<User, CometEvent> map = new ConcurrentHashMap<User, CometEvent>();

	public static ConcurrentHashMap<User, CometEvent> getMap() {
		return map;
	}
}

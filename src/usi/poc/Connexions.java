package usi.poc;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;

import usi.poc.business.itf.User;

public class Connexions {
	private static ConcurrentHashMap<User, HttpServletResponse> map = new ConcurrentHashMap<User, HttpServletResponse>();

	public static ConcurrentHashMap<User, HttpServletResponse> getMap() {
		return map;
	}
}

package usi.poc.data;

import usi.poc.business.itf.GameData;

public interface IGameDataDAO extends IDAO {

	public void createGame(GameData gameData);
	
	public GameData getGame();
	
	public void reset();
	
	public boolean isGameExists();
	
}

package usi.poc.data;

import java.util.Map;

import javax.annotation.Resource;

import usi.poc.business.itf.GameData;

import com.gemstone.gemfire.cache.Region;

public class GemGameDataDAO extends AbstractGemDAO implements IGameDataDAO {

	private static final String GAME_KEY = "game";
	
	@Resource
	private Region<String, GameData> gameCache;
	
	@Override
	public Map<String, GameData> getImpl() {
		return gameCache;
	}

	@Override
	public void createGame(GameData gameData) {
		if ( gameCache.size() == 1 ) {
			// TODO - throw exception
		}
		gameCache.put(GAME_KEY, gameData);
	}

	@Override
	public GameData getGame() {
		return gameCache.get(GAME_KEY);
	}

	@Override
	public void reset() {
		gameCache.clear();
	}

	@Override
	public boolean isGameExists() {
		return gameCache.containsKey(GAME_KEY);
	}

}

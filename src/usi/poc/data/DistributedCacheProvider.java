package usi.poc.data;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionShortcut;
import com.gemstone.gemfire.cache.query.IndexExistsException;
import com.gemstone.gemfire.cache.query.IndexInvalidException;
import com.gemstone.gemfire.cache.query.IndexNameConflictException;
import com.gemstone.gemfire.cache.query.IndexType;
import com.gemstone.gemfire.cache.query.RegionNotFoundException;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;


public class DistributedCacheProvider {

	@Resource
	private Cache cache;

	@SuppressWarnings("rawtypes")
	private Map<String, Region> regions = new HashMap<String, Region>();;

	public <K, V> Region<K, V> createUserCache(CacheListenerAdapter<K,V> listener) {
		String name = "users";
		if ( regions.containsKey(name) ) {
			// TODO : throw exception !
		}
		Region<K, V> region = cache.<K, V>createRegionFactory(RegionShortcut.REPLICATE)
		.addCacheListener(listener)
		.create(name);
		try {
			cache.getQueryService().createIndex("score", IndexType.FUNCTIONAL, "score", "/users");
		} catch (RegionNotFoundException e) {
			e.printStackTrace();
		} catch (IndexInvalidException e) {
			e.printStackTrace();
		} catch (IndexNameConflictException e) {
			e.printStackTrace();
		} catch (IndexExistsException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		}
		regions.put(name, region);
		return region;
	}

	public <K, V> Region<K, V> createGameCache() {
		String name = "game";
		if ( regions.containsKey(name) ) {
			// TODO : throw exception !
		}
		Region<K, V> region = cache.<K, V>createRegionFactory(RegionShortcut.REPLICATE)
		.create(name);
		regions.put(name, region);
		return region;
	}
	
	public <K, V> Region<K, V> createAnswersCache() {
		String name = "answers";
		if ( regions.containsKey(name) ) {
			// TODO : throw exception !
		}
		Region<K, V> region = cache.<K, V>createRegionFactory(RegionShortcut.REPLICATE_PERSISTENT_OVERFLOW)
			.create(name);
		regions.put(name, region);
		return region;
	}
	
	@SuppressWarnings("unchecked")
	public <K, V> Region<K, V> getCache(String name) {
		return regions.get(name);
	}

}

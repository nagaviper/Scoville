package usi.poc.data;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionShortcut;
import com.gemstone.gemfire.cache.util.CacheListenerAdapter;


public class DistributedCacheProvider {

	@Resource
	private Cache cache;

	@SuppressWarnings("rawtypes")
	private Map<String, Region> regions = new HashMap<String, Region>();;

	@SuppressWarnings("unchecked")
	public <K, V> Region<K, V> createReplicatedCache(String name, CacheListenerAdapter<K,V> listener) {
		if ( regions.containsKey(name) ) {
			return regions.get(name);
		}
		Region<K, V> region = cache.<K, V>createRegionFactory(RegionShortcut.REPLICATE)
		.addCacheListener(listener)
		.create(name);
		regions.put(name, region);
		return region;
	}

	@SuppressWarnings("unchecked")
	public <K, V> Region<K, V> createReplicatedCache(String name) {
		if ( regions.containsKey(name) ) {
			return regions.get(name);
		}
		Region<K, V> region = cache.<K, V>createRegionFactory(RegionShortcut.REPLICATE).create(name);
		regions.put(name, region);
		return region;
	}

	@SuppressWarnings("unchecked")
	public <K, V> Region<K, V> getCache(String name) {
		return regions.get(name);
	}

	public <K, V> V getCacheAsUniqueObject(String name) {
		@SuppressWarnings("unchecked")
		Region<K, V> r = regions.get(name);
		if ( r.size() != 1 ) {
			// TODO - throw exception
		}
		return r.get(0);
	}

}

package usi.poc.data;

import java.util.Timer;

import javax.annotation.Resource;

import com.gemstone.gemfire.cache.Region;

public class TimerDAO {
	@Resource
	Region<String, Timer> timerCache;

	public Region<String, Timer> getTimerCache() {
		return timerCache;
	}

	public void setTimerCache(Region<String, Timer> timerCache) {
		this.timerCache = timerCache;
	}
}

package com.snake.mcf.common.cache.redis;

public interface RedisConstant {
	
	/** redis 过期时间**/
	public final static Integer HOUR = 3600;
	
	public final static Integer HALF_HOUR = 1800;
	
	/** redis 存放key **/
	public final static String UNLOCK_ACCOUNT_INFO_KEY = "sysmgr:account:info:unlock:"; //解锁用户信息
	
}

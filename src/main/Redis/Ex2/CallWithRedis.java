package main.Redis.Ex2;

import redis.clients.jedis.Jedis;

public interface CallWithRedis {
    public void call(Jedis jedis);
}

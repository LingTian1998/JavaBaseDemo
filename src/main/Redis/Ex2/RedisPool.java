package main.Redis.Ex2;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisPool {
    private JedisPool jedisPool;

    public RedisPool(){
        this.jedisPool = new JedisPool();
    }

    public void execute(CallWithRedis caller){
        Jedis jedis = jedisPool.getResource();
        try{
            caller.call(jedis);
        } catch (JedisConnectionException e){ //重试
            caller.call(jedis);
        }finally {
            jedis.close();
        }
    }
}

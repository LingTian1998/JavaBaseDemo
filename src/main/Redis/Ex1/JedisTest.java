package main.Redis.Ex1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {

    public static void main(String[] args){
        JedisPool jedisPool = new JedisPool();
        try(Jedis jedis = jedisPool.getResource()){ //用完自动close
            doSomething(jedis);
        }
    }

    private static void doSomething(Jedis jedis) {
        System.out.println(jedis.get("test"));
    }
}

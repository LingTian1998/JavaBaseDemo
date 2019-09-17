package main.Redis.Ex2;

import redis.clients.jedis.Jedis;

public class JedisTest {
    public static void main(String[] args){
        RedisPool redisPool = new RedisPool();
        Holder<Long> countHolder = new Holder<>();
        redisPool.execute(new CallWithRedis() {
            @Override
            public void call(Jedis jedis) {
                long count = jedis.zcard("codehole");
                countHolder.value(count);
            }
        });
        System.out.println(countHolder.value());
    }
}

class Holder<T>{
    private T value;
    public Holder(){

    }

    public Holder(T value){
        this.value = value;
    }

    public void value(T value){
        this.value = value;
    }
    public T value(){
        return value;
    }
}
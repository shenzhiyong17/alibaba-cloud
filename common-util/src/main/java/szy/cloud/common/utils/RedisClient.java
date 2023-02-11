package szy.cloud.common.utils;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
// todo：本地沒裝redis server
public class RedisClient {}
//    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);
//    private JedisCluster jedisCluster;
//
//    public RedisClient(JedisPoolConfig poolConfig, int connectionTimeout, int soTimeout, int maxAttempts, String password, String clusterNodes) {
//        Set<HostAndPort> jedisClusterNodes = new HashSet();
//        String[] nodes = clusterNodes.split(";");
//        String[] var9 = nodes;
//        int var10 = nodes.length;
//
//        for(int var11 = 0; var11 < var10; ++var11) {
//            String node = var9[var11];
//            String[] hostport = node.split(":");
//            jedisClusterNodes.add(new HostAndPort(hostport[0], Integer.parseInt(hostport[1])));
//        }
//
//        this.jedisCluster = new JedisCluster(jedisClusterNodes, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
//    }
//
//    public String get(String key) {
//        return this.jedisCluster.get(key);
//    }
//
//    public void set(String key, String value) {
//        this.jedisCluster.set(key, value);
//    }
//
//    public boolean exists(String key) {
//        return this.jedisCluster.exists(key);
//    }
//
//    public long decr(String key) {
//        return this.jedisCluster.decr(key);
//    }
//
//    public long incr(String key) {
//        LOGGER.debug("incr key {}", key);
//        return this.jedisCluster.incr(key);
//    }
//
//    public long expire(String key, int second) {
//        LOGGER.debug("expire key {}, {} second", key, second);
//        return this.jedisCluster.expire(key, second);
//    }
//
//    public List<String> lrange(String key, long start, long stop) {
//        return this.jedisCluster.lrange(key, start, stop);
//    }
//
//    public void ltrim(String key, long start, long stop) {
//        this.jedisCluster.ltrim(key, start, stop);
//    }
//
//    public void lpush(String key, String value) {
//        this.jedisCluster.lpush(key, new String[]{value});
//    }
//
//    public void del(String key) {
//        this.jedisCluster.del(key);
//    }
//
//    public long llen(String key) {
//        return this.jedisCluster.llen(key);
//    }
//
//    public String lindex(String key, long index) {
//        return this.jedisCluster.lindex(key, index);
//    }
//
//    public String lpop(String key) {
//        return this.jedisCluster.lpop(key);
//    }
//
//    public long incrAndExpire(String key, int second) {
//        long count = this.incr(key);
//        this.expire(key, second);
//        return count;
//    }
//
//    public Long ttl(String key) {
//        return this.jedisCluster.ttl(key);
//    }
//
//    public void psetex(String key, long milliseconds, String value) {
//        this.jedisCluster.psetex(key, milliseconds, value);
//    }
//
//    public JedisCluster getJedisCluster() {
//        return this.jedisCluster;
//    }
//}

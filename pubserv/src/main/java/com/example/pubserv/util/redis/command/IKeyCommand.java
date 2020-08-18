package com.example.pubserv.util.redis.command;

/**
 * Created by lenovo on 2019/3/12.
 *
 * Redis 键(key) 命令
 */
public interface IKeyCommand {

    /**
     * Redis DEL 命令用于删除已存在的键。不存在的 key 会被忽略。
     * @param key KEY
     * @return 被删除 key 的数量。
     */
    Long del(final byte[] key);

    Boolean exists(byte[] key);

    /**
     * Redis Expire 命令用于设置 key 的过期时间。key 过期后将不再可用。
     * @param key KEY
     * @param seconds 有效时间（单位：秒）
     * @return 设置成功返回 1 。 当 key 不存在或者不能为 key 设置过期时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的过期时间)返回 0
     */
    Long expire(byte[] key, int seconds);

    /**
     * Redis Expireat 命令用于以 UNIX 时间戳(unix timestamp)格式设置 key 的过期时间。key 过期后将不再可用。
     * @param key KEY
     * @param unixTime UNIX 时间戳
     * @return 设置成功返回 1 。 当 key 不存在或者不能为 key 设置过期时间时(比如在低于 2.1.3 版本的 Redis 中你尝试更新 key 的过期时间)返回 0 。
     */
    Long expireAt(byte[] key, long unixTime);

    /**
     * Redis PERSIST 命令用于移除给定 key 的过期时间，使得 key 永不过期。
     * @param key KEY
     * @return 当过期时间移除成功时，返回 1 。 如果 key 不存在或 key 没有设置过期时间，返回 0 。
     */
    Long persist(byte[] key);

    /**
     * Redis TTL 命令以秒为单位返回 key 的剩余过期时间。
     * @param key KEY
     * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以毫秒为单位，返回 key 的剩余生存时间。
     */
    Long ttl(byte[] key);

    /**
     * Redis Type 命令用于返回 key 所储存的值的类型。
     * @param key KEY
     * @return 返回 key 的数据类型，数据类型有： none (key不存在) string (字符串) list (列表) set (集合) zset (有序集) hash (哈希表)
     *
     */
    String type(byte[] key);
}

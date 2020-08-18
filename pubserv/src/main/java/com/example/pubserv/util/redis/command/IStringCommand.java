package com.example.pubserv.util.redis.command;

/**
 * Created by lenovo on 2019/3/12.
 *
 * Redis 字符串(String)命令
 */
public interface IStringCommand {

    /**
     * Redis SET 命令用于设置给定 key 的值。如果 key 已经存储其他值， SET 就覆写旧值，且无视类型。
     * @param key KEY
     * @param value VALUE
     * @return 在 Redis 2.6.12 以前版本， SET 命令总是返回 OK 。 <br/> 从 Redis 2.6.12 版本开始， SET 在设置操作成功完成时，才返回 OK 。
     */
    String set(byte[] key, byte[] value);

    /**
     * Redis Get 命令用于获取指定 key 的值。如果 key 不存在，返回 nil 。如果key 储存的值不是字符串类型，返回一个错误
     * @param key KEY
     * @return 返回 key 的值，如果 key 不存在时，返回 nil。 如果 key 不是字符串类型，那么返回一个错误。
     */
    byte[] get(byte[] key);

    /**
     * Redis Strlen 命令用于获取指定 key 所储存的字符串值的长度。当 key 储存的不是字符串值时，返回一个错误。
     * @param key KEY
     * @return 字符串值的长度。 当 key 不存在时，返回 0。
     */
    Long strlen(byte[] key);

    /**
     * Redis Setex 命令为指定的 key 设置值及其过期时间。如果 key 已经存在， SETEX 命令将会替换旧的值。
     * @param key  KEY
     * @param seconds 过期时间（单位：秒）
     * @param value VALUE
     * @return 设置成功时返回 OK 。
     */
    String setex(byte[] key, int seconds, byte[] value);

    /**
     * Redis Setnx（SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
     * @param key KEY
     * @param value VALUE
     * @return 设置成功，返回 1 。 设置失败，返回 0 。
     */
    Long setnx(byte[] key, byte[] value);

}


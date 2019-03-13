package com.example.demo.util.redis.command;

import com.example.demo.util.redis.command.IHashCommand;
import com.example.demo.util.redis.command.IKeyCommand;
import com.example.demo.util.redis.command.IListCommand;
import com.example.demo.util.redis.command.ILockCommand;
import com.example.demo.util.redis.command.ISetCommand;
import com.example.demo.util.redis.command.ISortedSetCommand;
import com.example.demo.util.redis.command.IStringCommand;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * Created by realcolorred on 2019/3/12.
 */
public interface IBinaryJedis extends IKeyCommand, IStringCommand, ILockCommand {

}

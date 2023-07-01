package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.util.DBUtil;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class PTTLCommand extends AbstractCommand implements RedisCommand {
    private final String key;

    public PTTLCommand(RedisClient redisClient, String key) {
        super(redisClient);
        this.key = key;
    }

    @Override
    public void execute0() {
        execute0(redisClient, key);
    }

    @Override
    public String decode() {
        return "pttl " + key;
    }

    public void execute0(RedisClient redisClient, String key) {
        RedisDb db = redisClient.curDb();
        RedisObject value = DBUtil.lookupKeyRead(db, key);
        if (value == null) {
            redisClient.stream().response(-2);
            return;
        }
        Long expireTimestamp = db.expires().get(key);
        if (expireTimestamp == null) {
            redisClient.stream().response(-1);
        } else {
            redisClient.stream().responseString(String.valueOf((expireTimestamp - TimeUtil.currentTimeMillis())));
        }
    }

    public static PTTLCommand build(RedisClient redisClient, String value) {
        return new PTTLCommand(redisClient, value);
    }


}
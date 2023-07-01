package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.server.RedisServer;

public class SelectDbCommand extends AbstractCommand implements RedisCommand {

    private int dbIndex;

    protected SelectDbCommand(RedisClient redisClient, int dbIndex) {
        super(redisClient);
        this.dbIndex = dbIndex;
    }

    @Override
    public void execute0() {
        RedisServer redisServer = redisClient.server();
        redisClient.dataAccess().setCurDb(redisServer.db(dbIndex));
        redisClient.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public String decode() {
        return "select " + dbIndex;
    }

    public static SelectDbCommand build(RedisClient redisClient, int dbIndex) {
        return new SelectDbCommand(redisClient, dbIndex);
    }
}

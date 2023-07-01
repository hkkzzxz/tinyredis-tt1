package com.github.cjqcn.tinyredis.core.struct.impl;

import com.github.cjqcn.tinyredis.core.struct.Dict;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;

public class RedisDbImpl implements RedisDb {
    private final int id;
    private Dict dict = new BaseDict();
    private ExpiresDict expires = new ExpiresDict();

    public RedisDbImpl(final int id) {
        this.id = id;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Dict dict() {
        return dict;
    }

    @Override
    public ExpiresDict expires() {
        return expires;
    }
}

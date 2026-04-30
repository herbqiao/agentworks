package com.agentworks.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    private static final Long SYSTEM_USER_ID = 0L;
    private static final Integer NOT_DELETED = 0;

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        if (getFieldValByName("createTime", metaObject) == null) {
            setFieldValByName("createTime", now, metaObject);
        }
        if (getFieldValByName("updateTime", metaObject) == null) {
            setFieldValByName("updateTime", now, metaObject);
        }
        if (getFieldValByName("createBy", metaObject) == null) {
            setFieldValByName("createBy", SYSTEM_USER_ID, metaObject);
        }
        if (getFieldValByName("updateBy", metaObject) == null) {
            setFieldValByName("updateBy", SYSTEM_USER_ID, metaObject);
        }
        if (getFieldValByName("isDeleted", metaObject) == null) {
            setFieldValByName("isDeleted", NOT_DELETED, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        setFieldValByName("updateBy", SYSTEM_USER_ID, metaObject);
    }
}

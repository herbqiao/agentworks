package com.agentworks.config;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MybatisMetaObjectHandlerTest {

    private final MybatisMetaObjectHandler handler = new MybatisMetaObjectHandler();

    @Test
    void should_fill_audit_fields_when_inserting() {
        TestAuditEntity entity = new TestAuditEntity();

        handler.insertFill(SystemMetaObject.forObject(entity));

        assertThat(entity.getCreateTime()).isNotNull();
        assertThat(entity.getUpdateTime()).isNotNull();
        assertThat(entity.getCreateBy()).isEqualTo(0L);
        assertThat(entity.getUpdateBy()).isEqualTo(0L);
        assertThat(entity.getIsDeleted()).isEqualTo(0);
    }

    @Test
    void should_refresh_update_fields_when_updating() {
        TestAuditEntity entity = new TestAuditEntity();
        entity.setUpdateBy(12L);

        handler.updateFill(SystemMetaObject.forObject(entity));

        assertThat(entity.getUpdateTime()).isNotNull();
        assertThat(entity.getUpdateBy()).isEqualTo(0L);
    }

    private static final class TestAuditEntity {
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private Long createBy;
        private Long updateBy;
        private Integer isDeleted;

        public LocalDateTime getCreateTime() {
            return createTime;
        }

        public void setCreateTime(LocalDateTime createTime) {
            this.createTime = createTime;
        }

        public LocalDateTime getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
        }

        public Long getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Long createBy) {
            this.createBy = createBy;
        }

        public Long getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Long updateBy) {
            this.updateBy = updateBy;
        }

        public Integer getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Integer isDeleted) {
            this.isDeleted = isDeleted;
        }
    }
}

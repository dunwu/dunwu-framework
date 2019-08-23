package io.github.dunwu.dlock.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@TableName("lock_info")
public class LockInfo {

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    private String key;
    private Long time;
    private TimeUnit timeUnit;
    private Long expire;
}

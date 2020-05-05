package io.github.dunwu.filesystem.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.github.dunwu.data.core.constant.IStringStringEnum;

/**
 * 文件存储类型
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-27
 */
public enum FileStoreTypeEnum implements IStringStringEnum {

    DB("DB", FileSystemConstant.DB_FILE_CONTENT_SERVICE),
    FDFS("FDFS", FileSystemConstant.FDFS_FILE_CONTENT_SERVICE),
    HDFS("HDFS", FileSystemConstant.HDFS_FILE_CONTENT_SERVICE),
    OSS("OSS", FileSystemConstant.OSS_FILE_CONTENT_SERVICE),
    TEMP("TEMP", FileSystemConstant.TEMP_FILE_CONTENT_SERVICE);

    @EnumValue
    private final String key;

    private final String value;

    FileStoreTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}

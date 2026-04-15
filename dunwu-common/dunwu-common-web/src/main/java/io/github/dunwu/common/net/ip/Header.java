package io.github.dunwu.common.net.ip;

import lombok.Getter;

import java.util.Arrays;

/**
 * IP 数据库文件头信息
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-11
 */
@Getter
public class Header {

    /** 版本号 */
    private final int version;
    /** 索引策略 */
    private final int indexPolicy;
    /** 创建时间戳 */
    private final int createdAt;
    /** 起始索引指针 */
    private final int startIndexPtr;
    /** 结束索引指针 */
    private final int endIndexPtr;
    /** 原始缓冲区数据 */
    private final byte[] buffer;

    public Header(byte[] buff) {
        if (buff == null || buff.length < 16) {
            throw new IllegalArgumentException("Header buffer must be at least 16 bytes");
        }
        this.version = Searcher.getInt2(buff, 0);
        this.indexPolicy = Searcher.getInt2(buff, 2);
        this.createdAt = Searcher.getInt(buff, 4);
        this.startIndexPtr = Searcher.getInt(buff, 8);
        this.endIndexPtr = Searcher.getInt(buff, 12);
        this.buffer = Arrays.copyOf(buff, buff.length);
    }

    public byte[] getBuffer() {
        return Arrays.copyOf(buffer, buffer.length);
    }

    @Override
    public String toString() {
        return "Header{"
            + "version="
            + version
            + ", indexPolicy="
            + indexPolicy
            + ", createdAt="
            + createdAt
            + ", startIndexPtr="
            + startIndexPtr
            + ", endIndexPtr="
            + endIndexPtr
            + '}';
    }

}

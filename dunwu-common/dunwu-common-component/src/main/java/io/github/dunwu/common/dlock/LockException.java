package io.github.dunwu.common.dlock;

/**
 * 分布式锁异常
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-11
 */
public class LockException extends RuntimeException {

    /**
     * 根据错误消息构造锁异常
     *
     * @param message 错误消息
     */
    public LockException(String message) {
        super(message);
    }

    /**
     * 根据错误消息和原始异常构造锁异常
     *
     * @param message 错误消息
     * @param cause   原始异常
     */
    public LockException(String message, Throwable cause) {
        super(message, cause);
    }

}

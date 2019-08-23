package io.github.dunwu.dlock;

import java.util.concurrent.locks.Lock;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
public interface DLock extends Lock {

    long TIMEOUT = 3000;

    String OK = "OK";

    /** NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist. **/
    String NOT_EXIST = "NX";
    String EXIST = "XX";

    /** expx EX|PX, expire time units: EX = seconds; PX = milliseconds **/
    String SECONDS = "EX";
    String MILLISECONDS = "PX";
}

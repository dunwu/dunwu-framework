package io.github.dunwu.tool.web.log;

/**
 * @author peng.zhang
 * @date 2021-09-29
 */
public interface LogStorage {

    boolean store(AppLogInfo appLogInfo);

}

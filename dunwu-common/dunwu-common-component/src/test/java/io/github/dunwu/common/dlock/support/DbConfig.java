package io.github.dunwu.common.dlock.support;

import java.io.IOException;

public interface DbConfig {

    void startDb() throws IOException;

    void shutdownDb();

    String getJdbcUrl();

    String getUsername();

    String getPassword();

}

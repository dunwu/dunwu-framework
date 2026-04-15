package io.github.dunwu.common.dlock.support;

import io.github.dunwu.common.dlock.provider.LockProvider;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.ExecutionException;
import javax.sql.DataSource;

public class TransactionalFuzzTester {

    public static void fuzzTestShouldWorkWithTransaction(LockProvider lockProvider, DataSource dataSource)
        throws ExecutionException, InterruptedException {
        new FuzzTester(lockProvider) {
            @Override
            protected Void task(int iterations) {
                TransactionTemplate transactionTemplate =
                    new TransactionTemplate(new DataSourceTransactionManager(dataSource));
                return transactionTemplate.execute(status -> super.task(iterations));
            }

            @Override
            protected boolean shouldLog() {
                return true;
            }
        }.doFuzzTest();
    }

}

package io.github.dunwu.tool.io;

import io.github.dunwu.tool.io.watch.SimpleWatcher;
import io.github.dunwu.tool.io.watch.WatchMonitor;
import io.github.dunwu.tool.io.watch.Watcher;
import io.github.dunwu.tool.io.watch.watchers.DelayWatcher;
import io.github.dunwu.tool.lang.Console;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * 文件监听单元测试
 *
 * @author Looly
 */
public class WatchMonitorTest {

    public static void main(String[] args) {
        Watcher watcher = new SimpleWatcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);
            }

            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        };

        WatchMonitor monitor = WatchMonitor.createAll("d:/aaa/aaa.txt", new DelayWatcher(watcher, 500));

        monitor.setMaxDepth(0);
        monitor.start();
    }

}

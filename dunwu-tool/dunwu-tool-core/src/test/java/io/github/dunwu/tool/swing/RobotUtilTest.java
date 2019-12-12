package io.github.dunwu.tool.swing;

import io.github.dunwu.tool.io.FileUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RobotUtilTest {

    @Test
    @Disabled
    public void captureScreenTest() {
        RobotUtil.captureScreen(FileUtil.file("e:/screen.jpg"));
    }

}

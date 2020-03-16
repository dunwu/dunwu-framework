package io.github.dunwu.tool.swing;

import io.github.dunwu.tool.exceptions.UtilException;
import io.github.dunwu.tool.img.ImgUtil;
import io.github.dunwu.tool.swing.clipboard.ClipboardUtil;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * {@link Robot} 封装工具类，提供截屏等工具
 *
 * @author looly
 * @since 4.1.14
 */
public class RobotUtil {

    private static final Robot robot;

    private static int delay;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 截取全屏到文件
     *
     * @param outFile 写出到的文件
     * @return 写出到的文件
     */
    public static File captureScreen(File outFile) {
        ImgUtil.write(captureScreen(), outFile);
        return outFile;
    }

    /**
     * 截取全屏
     *
     * @return 截屏的图片
     */
    public static BufferedImage captureScreen() {
        return captureScreen(ScreenUtil.getRectangle());
    }

    /**
     * 截屏
     *
     * @param screenRect 截屏的矩形区域
     * @return 截屏的图片
     */
    public static BufferedImage captureScreen(Rectangle screenRect) {
        return robot.createScreenCapture(screenRect);
    }

    /**
     * 截屏
     *
     * @param screenRect 截屏的矩形区域
     * @param outFile    写出到的文件
     * @return 写出到的文件
     */
    public static File captureScreen(Rectangle screenRect, File outFile) {
        ImgUtil.write(captureScreen(screenRect), outFile);
        return outFile;
    }

    /**
     * 模拟单击<br> 鼠标单击包括鼠标左键的按下和释放
     *
     * @since 4.5.7
     */
    public static void click() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        delay();
    }

    /**
     * 等待指定毫秒数
     */
    private static void delay() {
        if (delay > 0) {
            robot.delay(delay);
        }
    }

    /**
     * 模拟键盘点击<br> 包括键盘的按下和释放
     *
     * @param keyCodes 按键码列表，见{@link java.awt.event.KeyEvent}
     * @since 4.5.7
     */
    public static void keyClick(int... keyCodes) {
        for (int keyCode : keyCodes) {
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
        delay();
    }

    /**
     * 打印输出指定字符串（借助剪贴板）
     *
     * @param str 字符串
     */
    public static void keyPressString(String str) {
        ClipboardUtil.setStr(str);
        keyPressWithCtrl(KeyEvent.VK_V);// 粘贴
        delay();
    }

    /**
     * ctrl+ 按键
     *
     * @param key 按键
     */
    public static void keyPressWithCtrl(int key) {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(key);
        robot.keyRelease(key);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        delay();
    }

    /**
     * alt+ 按键
     *
     * @param key 按键
     */
    public static void keyPressWithAlt(int key) {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(key);
        robot.keyRelease(key);
        robot.keyRelease(KeyEvent.VK_ALT);
        delay();
    }

    /**
     * shift+ 按键
     *
     * @param key 按键
     */
    public static void keyPressWithShift(int key) {
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(key);
        robot.keyRelease(key);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        delay();
    }

    /**
     * 模拟鼠标移动
     *
     * @param x 移动到的x坐标
     * @param y 移动到的y坐标
     * @since 4.5.7
     */
    public static void mouseMove(int x, int y) {
        robot.mouseMove(x, y);
    }

    /**
     * 模拟鼠标滚轮滚动
     *
     * @param wheelAmt 滚动数，负数表示向前滚动，正数向后滚动
     * @since 4.5.7
     */
    public static void mouseWheel(int wheelAmt) {
        robot.mouseWheel(wheelAmt);
        delay();
    }

    /**
     * 模拟右键单击<br> 鼠标单击包括鼠标右键的按下和释放
     *
     * @since 4.5.7
     */
    public static void rightClick() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        delay();
    }

    /**
     * 设置默认的延迟时间<br> 当按键执行完后的等待时间，也可以用ThreadUtil.sleep方法代替
     *
     * @param delayMillis 等待毫秒数
     * @since 4.5.7
     */
    public static void setDelay(int delayMillis) {
        delay = delayMillis;
    }

}

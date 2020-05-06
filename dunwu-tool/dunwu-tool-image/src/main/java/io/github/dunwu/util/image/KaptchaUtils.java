package io.github.dunwu.util.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * 验证码工具
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-13
 */
public class KaptchaUtils {

    private static final char[] CODE_SEQUENCE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    public static Kaptcha create(final KaptchaConfig config) {

        BufferedImage image = new BufferedImage(config.getWidth(), config.getHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = image.createGraphics();
        Random random = new Random();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, config.getWidth(), config.getHeight());

        // 字体高度
        int fontHeight = config.getHeight() - 4;
        Font font = new Font("Default", Font.PLAIN, fontHeight);
        graphics.setFont(font);

        for (int i = 0; i < config.getLineCount(); i++) {
            // x轴第一个点的位置
            int x1 = random.nextInt(config.getWidth());
            // y轴第一个点的位置
            int y1 = random.nextInt(config.getHeight());
            // x轴第二个点的位置
            int x2 = x1 + random.nextInt(config.getWidth() >> 2);
            // y轴第二个点的位置
            int y2 = y1 + random.nextInt(config.getHeight() >> 2);

            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);

            graphics.setColor(new Color(red, green, blue));
            graphics.drawLine(x1, y1, x2, y2);
        }

        StringBuilder randomCode = new StringBuilder(config.getCodeCount());
        // 字符所在 x 坐标
        int x = (config.getWidth() - 10) / config.getCodeCount();
        // 字符所在 y 坐标
        int y = config.getHeight() - 8;
        for (int i = 0; i < config.getCodeCount(); i++) {
            String strRand = String
                .valueOf(CODE_SEQUENCE[random.nextInt(CODE_SEQUENCE.length)]);
            int red = random.nextInt(255);
            int green = random.nextInt(255);
            int blue = random.nextInt(255);
            graphics.setColor(new Color(red, green, blue));
            graphics.drawString(strRand, i * x + 5, y);
            randomCode.append(strRand);
        }

        LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(config.getTimeout());
        return new Kaptcha(randomCode.toString(), localDateTime, config, image);
    }

    public static Kaptcha create() {
        return create(KaptchaConfig.defaultKaptchaConfig());
    }

    public static Kaptcha create(final int width, final int height, final double scale, final int codeCount,
        final int lineCount, ImageType type, long timeout) {
        KaptchaConfig config = new KaptchaConfig(width, height, scale, codeCount, lineCount, timeout, type);
        return create(config);
    }

    public static void toFile(final Kaptcha kaptcha, final File output) throws IOException {
        if (kaptcha == null || kaptcha.getConfig() == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        ImageProperties properties = kaptchaConfigToImageProperties(kaptcha.getConfig());
        ImageUtils.toFile(kaptcha.getImage(), output, properties);
    }

    public static void toOutputStream(final Kaptcha kaptcha, final OutputStream output) throws IOException {
        if (kaptcha == null || kaptcha.getConfig() == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        ImageProperties properties = kaptchaConfigToImageProperties(kaptcha.getConfig());
        ImageUtils.toOutputStream(kaptcha.getImage(), output, properties);
    }

    private static ImageProperties kaptchaConfigToImageProperties(KaptchaConfig config) {
        ImageProperties properties = new ImageProperties();
        properties.setWidth(config.getWidth())
            .setHeight(config.getHeight())
            .setScale(config.getScale())
            .setFormat(config.getType());
        return properties;
    }

    public static class Kaptcha implements Serializable {

        private static final long serialVersionUID = 117611061075580022L;

        private String code;
        private LocalDateTime expireTime;
        private KaptchaConfig config;
        private BufferedImage image;

        public Kaptcha() {}

        public Kaptcha(String code, LocalDateTime expireTime, KaptchaConfig config, BufferedImage image) {
            this.code = code;
            this.expireTime = expireTime;
            this.config = config;
            this.image = image;
        }

        @Override
        public String toString() {
            return "Kaptcha{" +
                "code='" + code + '\'' +
                ", expireTime=" + expireTime +
                ", config=" + config +
                ", image=" + image +
                '}';
        }

        public String getCode() {
            return code;
        }

        public Kaptcha setCode(String code) {
            this.code = code;
            return this;
        }

        public LocalDateTime getExpireTime() {
            return expireTime;
        }

        public Kaptcha setExpireTime(LocalDateTime expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public KaptchaConfig getConfig() {
            return config;
        }

        public Kaptcha setConfig(KaptchaConfig config) {
            this.config = config;
            return this;
        }

        public BufferedImage getImage() {
            return image;
        }

        public Kaptcha setImage(BufferedImage image) {
            this.image = image;
            return this;
        }

    }

    public static class KaptchaConfig implements Serializable {

        private static final long serialVersionUID = -1540074675734526420L;

        /**
         * 验证码默认字符个数
         */
        private static final int CODE_COUNT = 4;

        /**
         * 验证码图片的默认宽度
         */
        private static final int WIDTH = 120;

        /**
         * 验证码图片的默认高度
         */
        private static final int HEIGHT = 40;

        /**
         * 验证码图片的默认高度
         */
        private static final double SCALE = 1.0;

        /**
         * 验证码图片的默认干扰线数
         */
        private static final int LINE_COUNT = 20;

        /**
         * 验证码图片的默认超时时间（单位：秒）
         */
        private static final int DEFAULT_EXPIRE_TIME = 60 * 10;

        private int width;
        private int height;
        private double scale;
        private int codeCount;
        private int lineCount;
        private long timeout = 0L;
        private ImageType type;

        public KaptchaConfig() {
            this.scale = 1.0;
            this.timeout = 0L;
            this.type = ImageType.jpg;
        }

        public KaptchaConfig(int width, int height, double scale, int codeCount, int lineCount,
            long timeout, ImageType type) {
            this.width = width;
            this.height = height;
            this.scale = scale;
            this.codeCount = codeCount;
            this.lineCount = lineCount;
            this.type = type;
            this.timeout = timeout;
        }

        public static KaptchaConfig defaultKaptchaConfig() {
            KaptchaConfig config = new KaptchaConfig();
            config.setWidth(WIDTH)
                .setHeight(HEIGHT)
                .setScale(SCALE)
                .setCodeCount(CODE_COUNT)
                .setLineCount(LINE_COUNT)
                .setTimeout(DEFAULT_EXPIRE_TIME)
                .setType(ImageType.jpg);
            return config;
        }

        public int getWidth() {
            return width;
        }

        public KaptchaConfig setWidth(int width) {
            this.width = width;
            return this;
        }

        public int getHeight() {
            return height;
        }

        public KaptchaConfig setHeight(int height) {
            this.height = height;
            return this;
        }

        public double getScale() {
            return scale;
        }

        public KaptchaConfig setScale(double scale) {
            this.scale = scale;
            return this;
        }

        public int getCodeCount() {
            return codeCount;
        }

        public KaptchaConfig setCodeCount(int codeCount) {
            this.codeCount = codeCount;
            return this;
        }

        public int getLineCount() {
            return lineCount;
        }

        public KaptchaConfig setLineCount(int lineCount) {
            this.lineCount = lineCount;
            return this;
        }

        public ImageType getType() {
            return type;
        }

        public KaptchaConfig setType(ImageType type) {
            this.type = type;
            return this;
        }

        public long getTimeout() {
            return timeout;
        }

        public KaptchaConfig setTimeout(long timeout) {
            this.timeout = timeout;
            return this;
        }

    }

}

package io.github.dunwu.common.util.image;

import cn.hutool.core.io.IoUtil;
import io.github.dunwu.common.io.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 图片处理工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://github.com/coobird/thumbnailator/">thumbnailator</a>
 * @since 2017/2/13.
 */
public class ImageUtil {

    /**
     * 将字节数组转换为 {@link BufferedImage}
     *
     * @param bytes 图片字节数组
     * @return BufferedImage 对象
     * @throws IOException IO 异常
     */
    public static BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(bytes);
            return ImageIO.read(in);
        } finally {
            IoUtil.close(in);
        }
    }

    /**
     * 将输出流转换为字节数组
     *
     * @param os 输出流
     * @return 字节数组
     * @throws IOException IO 异常
     */
    public static byte[] toBytes(OutputStream os) throws IOException {
        byte[] bytes = new byte[0];
        os.write(bytes);
        os.flush();
        return bytes;
    }

    /**
     * 将 {@link BufferedImage} 转换为指定格式的字节数组
     *
     * @param image  图片对象
     * @param format 图片格式
     * @return 字节数组
     * @throws IOException IO 异常
     */
    public static byte[] toBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, format, os);
            byte[] bytes = os.toByteArray();
            os.flush();
            return bytes;
        } finally {
            IoUtil.close(os);
        }
    }

    private static void fillBuilderWithParams(Thumbnails.Builder<?> builder, ImageProperties properties) {

        if (properties == null) {
            return;
        }
        // 按照一定规则改变原图尺寸
        if (null != properties.getWidth() && null != properties.getHeight()) {
            builder.size(properties.getWidth(), properties.getHeight());
        } else if (null != properties.getXscale() && null != properties.getYscale()) {
            builder.scale(properties.getXscale(), properties.getYscale());
        } else if (null != properties.getScale()) {
            builder.scale(properties.getScale(), properties.getScale());
        } else {
            builder.scale(1.0);
        }

        if (null != properties.getSource()) {
            builder.sourceRegion(properties.getSource().getX(), properties.getSource().getY(),
                properties.getSource().getWidth(), properties.getSource().getHeight());
        }

        if (null != properties.getKeepAspectRatio()) {
            builder.keepAspectRatio(properties.getKeepAspectRatio());
        }

        // 设置图片旋转角度
        if (null != properties.getRotate()) {
            builder.rotate(properties.getRotate());
        }

        // 设置图片压缩质量
        if (null != properties.getQuality()) {
            builder.outputQuality(properties.getQuality());
        }

        // 设置图片格式
        if (null != properties.getFormat()) {
            builder.outputFormat(properties.getFormat().name());
        }

        // 设置水印
        if (null != properties.getImageWaterMark()) {
            Positions pos = getPositionsByCode(properties.getImageWaterMark().getPosition());
            builder.watermark(pos, properties.getImageWaterMark().getImage(),
                properties.getImageWaterMark().getOpacity());
        }
    }

    /**
     * 将位置类型码转换为 thumbnailator 可以识别的位置类型
     *
     * @param positions PositionsEnum
     * @return Positions
     */
    private static Positions getPositionsByCode(ImageWaterMark.WaterMarkPositions positions) {
        switch (positions) {
            case TOP_LEFT:
                return Positions.TOP_LEFT;
            case TOP_CENTER:
                return Positions.TOP_CENTER;
            case TOP_RIGHT:
                return Positions.TOP_RIGHT;
            case CENTER_LEFT:
                return Positions.CENTER_LEFT;
            case CENTER:
                return Positions.CENTER;
            case CENTER_RIGHT:
                return Positions.CENTER_RIGHT;
            case BOTTOM_LEFT:
                return Positions.BOTTOM_LEFT;
            case BOTTOM_CENTER:
                return Positions.BOTTOM_CENTER;
            case BOTTOM_RIGHT:
                return Positions.BOTTOM_RIGHT;
            default:
                return null;
        }
    }

    /**
     * 将图片文件按指定参数处理后输出到文件
     *
     * @param input      输入文件路径
     * @param output     输出文件路径
     * @param properties 图片处理参数
     * @return 输出文件
     * @throws IOException IO 异常
     */
    public static File toFile(String input, String output, ImageProperties properties) throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, properties);
        builder.toFile(output);
        return new File(output);
    }

    /**
     * 将图片文件按指定参数处理后输出到文件
     *
     * @param input      输入文件
     * @param output     输出文件
     * @param properties 图片处理参数
     * @return 输出文件
     * @throws IOException IO 异常
     */
    public static File toFile(File input, File output, ImageProperties properties) throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, properties);
        builder.toFile(output);
        return output;
    }

    /**
     * 将输入流中的图片按指定参数处理后输出到文件
     *
     * @param input      输入流
     * @param output     输出文件
     * @param properties 图片处理参数
     * @return 输出文件
     * @throws IOException IO 异常
     */
    public static File toFile(InputStream input, File output, ImageProperties properties) throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, properties);
        builder.toFile(output);
        return output;
    }

    /**
     * 将 {@link BufferedImage} 按指定参数处理后输出到文件
     *
     * @param input      输入图片
     * @param output     输出文件
     * @param properties 图片处理参数
     * @return 输出文件
     * @throws IOException IO 异常
     */
    public static File toFile(BufferedImage input, File output, ImageProperties properties) throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, properties);
        builder.toFile(output);
        return output;
    }

    /**
     * 批量将图片文件按指定参数处理后输出到文件列表
     *
     * @param input      输入文件列表
     * @param output     输出文件列表
     * @param properties 图片处理参数
     * @return 输出文件列表
     * @throws IOException IO 异常
     */
    public static List<File> toFiles(List<File> input, List<File> output, ImageProperties properties)
        throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.fromFiles(input);
        fillBuilderWithParams(builder, properties);
        builder.toFiles(output);
        return output;
    }

    /**
     * 将字节数组转换为输入流
     *
     * @param bytes 字节数组
     * @return 输入流
     */
    public static InputStream toInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 将图片文件按指定参数处理后输出到输出流
     *
     * @param input      输入文件路径
     * @param output     输出流
     * @param properties 图片处理参数
     * @throws IOException IO 异常
     */
    public static void toOutputStream(String input, OutputStream output, ImageProperties properties)
        throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, properties);
        builder.toOutputStream(output);
    }

    /**
     * 将图片文件按指定参数处理后输出到输出流
     *
     * @param input      输入文件
     * @param output     输出流
     * @param properties 图片处理参数
     * @throws IOException IO 异常
     */
    public static void toOutputStream(File input, OutputStream output, ImageProperties properties) throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, properties);
        builder.toOutputStream(output);
    }

    /**
     * 将输入流中的图片按指定参数处理后输出到输出流
     *
     * @param input      输入流
     * @param output     输出流
     * @param properties 图片处理参数
     * @throws IOException IO 异常
     */
    public static void toOutputStream(InputStream input, OutputStream output, ImageProperties properties)
        throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, properties);
        builder.toOutputStream(output);
    }

    /**
     * 将 {@link BufferedImage} 按指定参数处理后输出到输出流
     *
     * @param input      输入图片
     * @param output     输出流
     * @param properties 图片处理参数
     * @throws IOException IO 异常
     */
    public static void toOutputStream(BufferedImage input, OutputStream output, ImageProperties properties)
        throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, properties);
        builder.toOutputStream(output);
    }

    /**
     * 批量将输入流中的图片按指定参数处理后输出到输出流列表
     *
     * @param input      输入流列表
     * @param output     输出流列表
     * @param properties 图片处理参数
     * @throws IOException IO 异常
     */
    public static void toOutputStreams(List<InputStream> input, List<OutputStream> output, ImageProperties properties)
        throws IOException {
        Thumbnails.Builder<?> builder = Thumbnails.fromInputStreams(input);
        fillBuilderWithParams(builder, properties);
        builder.toOutputStreams(output);
    }

    /**
     * 获取图片文件的格式
     *
     * @param file 图片文件
     * @return 图片格式名称
     * @throws IOException IO 异常
     */
    public static String getFormat(File file) throws IOException {
        ImageInputStream iis = null;
        try {
            iis = ImageIO.createImageInputStream(file);
            return getFormat(iis);
        } finally {
            IoUtil.close(iis);
        }
    }

    /**
     * 从 {@link ImageInputStream} 中获取图片格式
     *
     * @param iis 图片输入流
     * @return 图片格式名称，若无法识别则返回 null
     * @throws IOException IO 异常
     */
    public static String getFormat(ImageInputStream iis) throws IOException {
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
            return null;
        }
        ImageReader reader = iter.next();
        return reader.getFormatName();
    }

    /**
     * 获取图片文件的信息（宽度、高度、大小、格式）
     *
     * @param file 图片文件
     * @return 图片信息
     * @throws IOException IO 异常
     */
    public static ImageInfo getImageInfo(File file) throws IOException {

        ImageInfo imageInfo = new ImageInfo();

        long length = file.length();
        imageInfo.setSize(length);

        String format = getFormat(file);
        imageInfo.setFormat(format);

        byte[] bytes = FileUtil.readBytes(file);
        BufferedImage bufferedImage = toBufferedImage(bytes);
        if (bufferedImage != null) {
            imageInfo.setWidth(bufferedImage.getWidth());
            imageInfo.setHeight(bufferedImage.getHeight());
        }

        return imageInfo;
    }

}

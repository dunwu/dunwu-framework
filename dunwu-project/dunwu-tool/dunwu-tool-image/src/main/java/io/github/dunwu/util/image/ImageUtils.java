package io.github.dunwu.util.image;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * 图片处理工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://github.com/coobird/thumbnailator/">thumbnailator</a>
 * @since 2017/2/13.
 */
public class ImageUtils {

    public static byte[] toBytes(BufferedImage image, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, format, os);
        byte[] bytes = os.toByteArray();
        os.flush();
        os.close();
        return bytes;
    }

    public static InputStream toInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public static BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream in = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(in);
        in.close();
        return image;
    }

    public static void outputStreamToBytes(OutputStream os, byte[] bytes)
        throws IOException {
        os.write(bytes);
        os.flush();
        os.close();
    }

    public static void toFile(String input, String output, ImageProperties params)
        throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toFile(output);
    }

    private static void fillBuilderWithParams(Thumbnails.Builder builder,
        ImageProperties params) {
        // builder.scale(1.0);

        if (params == null) {
            return;
        }
        // 按照一定规则改变原图尺寸
        if (null != params.getWidth() && null != params.getHeight()) {
            builder.size(params.getWidth(), params.getHeight());
        } else if (null != params.getXscale() && null != params.getYscale()) {
            builder.scale(params.getXscale(), params.getYscale());
        } else if (null != params.getScale()) {
            builder.scale(params.getScale(), params.getScale());
        }

        // 设置图片旋转角度
        if (null != params.getRotate()) {
            builder.rotate(params.getRotate());
        }

        // 设置图片压缩质量
        if (null != params.getQuality()) {
            builder.outputQuality(params.getQuality());
        }

        // 设置图片格式
        if (null != params.getFormat()) {
            builder.outputFormat(params.getFormat().name());
        }

        // 设置水印
        if (null != params.getImageWaterMark()) {
            Positions pos = getPostionsByCode(params.getImageWaterMark().getPosition());
            builder.watermark(pos, params.getImageWaterMark().getImage(),
                params.getImageWaterMark().getOpacity());
        }
    }

    /**
     * 将位置类型码转换为 thumbnailator 可以识别的位置类型
     *
     * @param positions PositionsEnum
     * @return Positions
     */
    private static Positions getPostionsByCode(ImageWaterMark.WaterMarkPositions positions) {
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

    public static void toFile(File input, File output, ImageProperties params)
        throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toFile(output);
    }

    public static void toFile(InputStream input, File output, ImageProperties params)
        throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toFile(output);
    }

    public static void toFile(BufferedImage input, File output, ImageProperties params)
        throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toFile(output);
    }

    public static void toFiles(List<File> input, List<File> output, ImageProperties params)
        throws IOException {
        Thumbnails.Builder builder = Thumbnails.fromFiles(input);
        fillBuilderWithParams(builder, params);
        builder.toFiles(output);
    }

    public static void toOutputStream(String input, OutputStream output,
        ImageProperties params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStream(output);
    }

    public static void toOutputStream(File input, OutputStream output, ImageProperties params)
        throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStream(output);
    }

    public static void toOutputStream(InputStream input, OutputStream output,
        ImageProperties params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStream(output);
    }

    public static void toOutputStream(BufferedImage input, OutputStream output,
        ImageProperties params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.of(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStream(output);
    }

    public static void toOutputStreams(List<InputStream> input, List<OutputStream> output,
        ImageProperties params) throws IOException {
        Thumbnails.Builder builder = Thumbnails.fromInputStreams(input);
        fillBuilderWithParams(builder, params);
        builder.toOutputStreams(output);
    }

}

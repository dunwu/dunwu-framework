package io.github.dunwu.tool.io.resource;

import io.github.dunwu.tool.io.IORuntimeException;
import io.github.dunwu.tool.util.CharsetUtil;
import io.github.dunwu.tool.util.StringUtil;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 基于byte[]的资源获取器<br> 注意：此对象中getUrl方法始终返回null
 *
 * @author looly
 * @since 4.0.9
 */
public class BytesResource implements Resource, Serializable {

    private static final long serialVersionUID = 1L;

    private final byte[] bytes;

    private final String name;

    /**
     * 构造
     *
     * @param bytes 字节数组
     */
    public BytesResource(byte[] bytes) {
        this(bytes, null);
    }

    /**
     * 构造
     *
     * @param bytes 字节数组
     * @param name  资源名称
     */
    public BytesResource(byte[] bytes, String name) {
        this.bytes = bytes;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public URL getUrl() {
        return null;
    }

    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(this.bytes);
    }

    @Override
    public BufferedReader getReader(Charset charset) {
        return new BufferedReader(new StringReader(readStr(charset)));
    }

    @Override
    public String readStr(Charset charset) throws IORuntimeException {
        return StringUtil.str(this.bytes, charset);
    }

    @Override
    public String readUtf8Str() throws IORuntimeException {
        return readStr(CharsetUtil.CHARSET_UTF_8);
    }

    @Override
    public byte[] readBytes() throws IORuntimeException {
        return this.bytes;
    }

}

package io.github.dunwu.tool.io.resource;

import io.github.dunwu.tool.io.IORuntimeException;
import io.github.dunwu.tool.io.IoUtil;
import io.github.dunwu.tool.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 基于{@link InputStream}的资源获取器<br> 注意：此对象中getUrl方法始终返回null
 *
 * @author looly
 * @since 4.0.9
 */
public class InputStreamResource implements Resource, Serializable {

    private static final long serialVersionUID = 1L;

    private InputStream in;

    private String name;

    /**
     * 构造
     *
     * @param in {@link InputStream}
     */
    public InputStreamResource(InputStream in) {
        this(in, null);
    }

    /**
     * 构造
     *
     * @param in   {@link InputStream}
     * @param name 资源名称
     */
    public InputStreamResource(InputStream in, String name) {
        this.in = in;
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
        return this.in;
    }

    @Override
    public BufferedReader getReader(Charset charset) {
        return IoUtil.getReader(this.in, charset);
    }

    @Override
    public String readStr(Charset charset) throws IORuntimeException {
        BufferedReader reader = null;
        try {
            reader = getReader(charset);
            return IoUtil.read(reader);
        } finally {
            IoUtil.close(reader);
        }
    }

    @Override
    public String readUtf8Str() throws IORuntimeException {
        return readStr(CharsetUtil.CHARSET_UTF_8);
    }

    @Override
    public byte[] readBytes() throws IORuntimeException {
        InputStream in = null;
        try {
            in = getStream();
            return IoUtil.readBytes(in);
        } finally {
            IoUtil.close(in);
        }
    }

}

package io.github.dunwu.util.base;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
public class ByteUtil {
    public static byte[] convertToPrimitiveArray(Byte[] objects) {
        byte[] bytes = new byte[objects.length];

        for (int i = 0; i < objects.length; ++i) {
            bytes[i] = objects[i];
        }

        return bytes;
    }

    public static Byte[] convertToObjectArray(byte[] bytes) {
        Byte[] objects = new Byte[bytes.length];

        for (int i = 0; i < bytes.length; ++i) {
            objects[i] = bytes[i];
        }

        return objects;
    }

    public static byte[] blobToByte(Blob blob) throws Exception {
        byte[] bytes = null;
        try {
            InputStream in = blob.getBinaryStream();
            BufferedInputStream inBuffered = new BufferedInputStream(in);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = inBuffered.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            inBuffered.close();
            in.close();
            bytes = out.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}

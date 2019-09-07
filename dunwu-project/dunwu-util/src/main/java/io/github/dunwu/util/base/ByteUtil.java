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

	/**
	 * Byte[] -> byte[]
	 * @param origin Byte[]
	 * @return byte[]
	 */
	public static byte[] convertToPrimitiveArray(final Byte[] origin) {
		if (origin == null) {
			return null;
		}

		byte[] target = new byte[origin.length];
		for (int i = 0; i < origin.length; ++i) {
			target[i] = origin[i];
		}

		return target;
	}

	/**
	 * byte[] -> Byte[]
	 * @param origin byte[]
	 * @return Byte[]
	 */
	public static Byte[] convertToObjectArray(final byte[] origin) {
		if (origin == null) {
			return null;
		}

		Byte[] target = new Byte[origin.length];
		for (int i = 0; i < origin.length; ++i) {
			target[i] = origin[i];
		}

		return target;
	}

	public static byte[] blobToByte(final Blob blob) throws Exception {
		InputStream in = blob.getBinaryStream();
		BufferedInputStream inBuffered = new BufferedInputStream(in);
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] bytes = new byte[1024];
		int size = 0;
		while ((size = inBuffered.read(bytes)) != -1) {
			out.write(bytes, 0, size);
		}
		inBuffered.close();
		in.close();
		return out.toByteArray();
	}

}

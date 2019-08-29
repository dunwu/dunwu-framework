package io.github.dunwu.util.io;

import io.github.dunwu.util.io.type.StringBuilderWriter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class IOUtilTest {

    @Test
    public void read() throws IOException {
        assertThat(ReadWriteUtil.toString(ResourceUtil.asStream("test.txt"))).isEqualTo("ABCDEFG\nABC");
        assertThat(ReadWriteUtil.toLines(ResourceUtil.asStream("test.txt"))).hasSize(2).containsExactly("ABCDEFG", "ABC");
    }

    @Test
    public void write() throws IOException {
        StringBuilderWriter sw = new StringBuilderWriter();
        ReadWriteUtil.write("hahahaha", sw);
        assertThat(sw.toString()).isEqualTo("hahahaha");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ReadWriteUtil.write("hahahaha", out);
        assertThat(new String(out.toByteArray(), StandardCharsets.UTF_8)).isEqualTo("hahahaha");

        ReadWriteUtil.closeQuietly(out);
        ReadWriteUtil.closeQuietly((Closeable) null);
    }

}

package io.github.dunwu.tool.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CharUtilTest {

    @Test
    public void trimTest() {
        //此字符串中的第一个字符为不可见字符: '\u202a'
        String str = "‪C:/Users/maple/Desktop/tone.txt";
        Assertions.assertEquals('\u202a', str.charAt(0));
        Assertions.assertTrue(CharUtil.isBlankChar(str.charAt(0)));
    }

    @Test
    public void isEmojiTest() {
        String a = "莉🌹";
        Assertions.assertFalse(CharUtil.isEmoji(a.charAt(0)));
        Assertions.assertTrue(CharUtil.isEmoji(a.charAt(1)));
    }

}

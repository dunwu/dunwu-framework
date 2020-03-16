package io.github.dunwu.tool.lang;

import io.github.dunwu.tool.exceptions.ValidateException;
import io.github.dunwu.tool.util.ValidatorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 验证器单元测试
 *
 * @author Looly
 */
public class ValidatorTest {

    @Test
    public void isNumberTest() {
        Assertions.assertTrue(ValidatorUtil.isNumber("45345365465"));
        Assertions.assertTrue(ValidatorUtil.isNumber("0004545435"));
        Assertions.assertTrue(ValidatorUtil.isNumber("5.222"));
        Assertions.assertTrue(ValidatorUtil.isNumber("0.33323"));
    }

    @Test
    public void isLetterTest() {
        Assertions.assertTrue(ValidatorUtil.isAllEnglishChar("asfdsdsfds"));
        Assertions.assertTrue(ValidatorUtil.isAllEnglishChar("asfdsdfdsfVCDFDFGdsfds"));
        Assertions.assertFalse(ValidatorUtil.isAllEnglishChar("asfdsdf你好dsfVCDFDFGdsfds"));
    }

    @Test
    public void isUperCaseTest() {
        Assertions.assertTrue(ValidatorUtil.isAllUpperEnglishChar("VCDFDFG"));
        Assertions.assertTrue(ValidatorUtil.isAllUpperEnglishChar("ASSFD"));

        Assertions.assertFalse(ValidatorUtil.isAllUpperEnglishChar("asfdsdsfds"));
        Assertions.assertFalse(ValidatorUtil.isAllUpperEnglishChar("ASSFD你好"));
    }

    @Test
    public void isAllLowerEnglishCharTest() {
        Assertions.assertTrue(ValidatorUtil.isAllLowerEnglishChar("asfdsdsfds"));

        Assertions.assertFalse(ValidatorUtil.isAllLowerEnglishChar("aaaa你好"));
        Assertions.assertFalse(ValidatorUtil.isAllLowerEnglishChar("VCDFDFG"));
        Assertions.assertFalse(ValidatorUtil.isAllLowerEnglishChar("ASSFD"));
        Assertions.assertFalse(ValidatorUtil.isAllLowerEnglishChar("ASSFD你好"));
    }

    @Test
    public void isDateTest() {
        boolean b = ValidatorUtil.isDate("20150101");
        Assertions.assertTrue(b);
        boolean b2 = ValidatorUtil.isDate("2015-01-01");
        Assertions.assertTrue(b2);
        boolean b3 = ValidatorUtil.isDate("2015.01.01");
        Assertions.assertTrue(b3);
        boolean b4 = ValidatorUtil.isDate("2015年01月01日");
        Assertions.assertTrue(b4);
        boolean b5 = ValidatorUtil.isDate("2015.01.01");
        Assertions.assertTrue(b5);
        boolean b6 = ValidatorUtil.isDate("2018-08-15");
        Assertions.assertTrue(b6);

        //验证年非法
        Assertions.assertFalse(ValidatorUtil.isDate("2095.05.01"));
        //验证月非法
        Assertions.assertFalse(ValidatorUtil.isDate("2015.13.01"));
        //验证日非法
        Assertions.assertFalse(ValidatorUtil.isDate("2015.02.29"));
    }

    @Test
    public void isCitizenIdTest() {
        boolean b = ValidatorUtil.isCitizenId15("150218199012123389");
        Assertions.assertTrue(b);
    }

    @Test
    public void validateTest() throws ValidateException {
        // ValidatorUtil.validateChinese("我是一段zhongwen", "内容中包含非中文");
        Assertions.assertThrows(ValidateException.class, () -> ValidatorUtil.isAllChineseChar("我是一段zhongwen"));
    }

    @Test
    public void isEmailTest() {
        boolean email = ValidatorUtil.isEmail("abc_cde@163.com");
        Assertions.assertTrue(email);
        boolean email1 = ValidatorUtil.isEmail("abc_%cde@163.com");
        Assertions.assertTrue(email1);
        boolean email2 = ValidatorUtil.isEmail("abc_%cde@aaa.c");
        Assertions.assertTrue(email2);
        boolean email3 = ValidatorUtil.isEmail("xiaolei.lu@aaa.b");
        Assertions.assertTrue(email3);
        boolean email4 = ValidatorUtil.isEmail("xiaolei.Lu@aaa.b");
        Assertions.assertTrue(email4);
    }

    @Test
    public void isMobileTest() {
        boolean m1 = ValidatorUtil.isMobile("13900221432");
        Assertions.assertTrue(m1);
        boolean m2 = ValidatorUtil.isMobile("015100221432");
        Assertions.assertTrue(m2);
        boolean m3 = ValidatorUtil.isMobile("+8618600221432");
        Assertions.assertTrue(m3);
    }

    @Test
    public void isMatchTest() {
        String url = "http://aaa-bbb.somthing.com/a.php?a=b&c=2";
        Assertions.assertTrue(ValidatorUtil.isHttpUrl(url));

        url = "https://aaa-bbb.somthing.com/a.php?a=b&c=2";
        Assertions.assertTrue(ValidatorUtil.isHttpUrl(url));

        url = "https://aaa-bbb.somthing.com:8080/a.php?a=b&c=2";
        Assertions.assertTrue(ValidatorUtil.isHttpUrl(url));
    }

    @Test
    public void isGeneralTest() {
        String str = "";
        boolean general = ValidatorUtil.isAllGeneralChar(str);
        Assertions.assertTrue(general);

        str = "123_abc_ccc";
        general = ValidatorUtil.isAllGeneralChar(str);
        Assertions.assertTrue(general);

        // 不允许中文
        str = "123_abc_ccc中文";
        general = ValidatorUtil.isAllGeneralChar(str);
        Assertions.assertFalse(general);
    }

}

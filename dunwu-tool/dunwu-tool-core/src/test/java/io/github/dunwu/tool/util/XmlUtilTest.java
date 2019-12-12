package io.github.dunwu.tool.util;

import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.map.MapBuilder;
import io.github.dunwu.tool.map.MapUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.xpath.XPathConstants;

/**
 * {@link XmlUtil} 工具类
 *
 * @author Looly
 */
public class XmlUtilTest {

    @Test
    public void parseTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
            + "<returnsms>"//
            + "<returnstatus>Success</returnstatus>"//
            + "<message>ok</message>"//
            + "<remainpoint>1490</remainpoint>"//
            + "<taskID>885</taskID>"//
            + "<successCounts>1</successCounts>"//
            + "</returnsms>";
        Document docResult = XmlUtil.parseXml(result);
        String elementText = XmlUtil.elementText(docResult.getDocumentElement(), "returnstatus");
        Assertions.assertEquals("Success", elementText);
    }

    @Test
    @Disabled
    public void writeTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
            + "<returnsms>"//
            + "<returnstatus>Success（成功）</returnstatus>"//
            + "<message>ok</message>"//
            + "<remainpoint>1490</remainpoint>"//
            + "<taskID>885</taskID>"//
            + "<successCounts>1</successCounts>"//
            + "</returnsms>";
        Document docResult = XmlUtil.parseXml(result);
        XmlUtil.toFile(docResult, "e:/aaa.xml", "utf-8");
    }

    @Test
    public void xpathTest() {
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
            + "<returnsms>"//
            + "<returnstatus>Success（成功）</returnstatus>"//
            + "<message>ok</message>"//
            + "<remainpoint>1490</remainpoint>"//
            + "<taskID>885</taskID>"//
            + "<successCounts>1</successCounts>"//
            + "</returnsms>";
        Document docResult = XmlUtil.parseXml(result);
        Object value = XmlUtil.getByXPath("//returnsms/message", docResult, XPathConstants.STRING);
        Assertions.assertEquals("ok", value);
    }

    @Test
    public void xmlToMapTest() {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"//
            + "<returnsms>"//
            + "<returnstatus>Success</returnstatus>"//
            + "<message>ok</message>"//
            + "<remainpoint>1490</remainpoint>"//
            + "<taskID>885</taskID>"//
            + "<successCounts>1</successCounts>"//
            + "</returnsms>";
        Map<String, Object> map = XmlUtil.xmlToMap(xml);

        Assertions.assertEquals(5, map.size());
        Assertions.assertEquals("Success", map.get("returnstatus"));
        Assertions.assertEquals("ok", map.get("message"));
        Assertions.assertEquals("1490", map.get("remainpoint"));
        Assertions.assertEquals("885", map.get("taskID"));
        Assertions.assertEquals("1", map.get("successCounts"));
    }

    @Test
    public void xmlToMapTest2() {
        String xml = "<root><name>张三</name><name>李四</name></root>";
        Map<String, Object> map = XmlUtil.xmlToMap(xml);

        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals(CollectionUtil.newArrayList("张三", "李四"), map.get("name"));
    }

    @Test
    public void mapToXmlTest() {
        Map<String, Object> map = MapBuilder.create(new LinkedHashMap<String, Object>())//
            .put("name", "张三")//
            .put("age", 12)//
            .put("game",
                MapUtil.builder(new LinkedHashMap<String, Object>()).put("昵称", "Looly").put("level", 14).build())//
            .build();
        Document doc = XmlUtil.mapToXml(map, "user");
        // Console.log(XmlUtil.toStr(doc, false));
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"//
                + "<user>"//
                + "<name>张三</name>"//
                + "<age>12</age>"//
                + "<game>"//
                + "<昵称>Looly</昵称>"//
                + "<level>14</level>"//
                + "</game>"//
                + "</user>", //
            XmlUtil.toStr(doc, false));
    }

    @Test
    public void readTest() {
        Document doc = XmlUtil.readXML("test.xml");
        Assertions.assertNotNull(doc);
    }

}

package io.github.dunwu.utils.md;

import io.github.dunwu.utils.regex.RegexHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Markdown 格式化
 * @author Zhang Peng
 * @date 2019-02-25
 */
public class MarkdownFormatHelper {
    public static void convertToGFM(String srcFilePath, String detFilePath) {

        List<String> contents = TxtFileUtil.readLineByLine(srcFilePath);

        List<String> newContents = new ArrayList<String>();

        boolean isCode = false;

        for (String text : contents) {
            text = replaceSpecialChars(text, isCode);

            if (text.contains("```")) {
                isCode = !isCode;
            } else if (!isCode) {
                // text = changeMathJaxToCodeCogs(text);
                text = convertImgTag(text);
                // text = addSpaceInHtmlTagB(text);
            }

            newContents.add(text);
        }

        newContents = TOC.changeTOCToGeneratedCatalogue(newContents);

        for (String str : newContents) {
            System.out.println(str);
        }

        TxtFileUtil.writeLineByLine(newContents, detFilePath);
    }

    public static String replaceSpecialChars(String text, boolean isCode) {

        if (!isCode) {
            text = text.replaceAll("([^\\\\])~", "$1\\\\~");
        }

        // System.out.println(" ".equals(" "));  // false, one is 160，one is 32
        text = text.replaceAll(" ", " ");

        return text;
    }

    public static String convertImgTag(final String text) {
        String newstr = RegexHelper.replaceAllMatchContent(text,
                                                           RegexHelper.Checker.REGEX_MARKDOWN_IMAGE_TAG, "![]");

        boolean hasPic = newstr.contains("![]");
        if (!hasPic) {
            return newstr;
        }

        int startIdx = newstr.indexOf("![]");
        int endIdx = newstr.indexOf(")", startIdx);
        String picPath = newstr.substring(startIdx + 4, endIdx);
        newstr = "<img src=\"" + picPath + "\"/>";
        newstr = "<br><div align=\"center\">" + newstr + "</div><br>";

        return newstr;
    }

    private static String addSpaceInHtmlTagB(final String text) {
        int index = 0;
        String temp = front(text, index);
        temp = back(temp, index);
        return temp;
    }

    private static String front(final String text, int index) {
        int startIdx = text.indexOf(" **");
        if (startIdx != -1) {
            return text;
        }

        startIdx = text.indexOf("**", startIdx + 3);
        if (startIdx == -1) {
            return text;
        }

        StringBuilder sb = new StringBuilder();
        if (startIdx != 0) {
            sb.append(text, 0, startIdx);
            sb.append(" ");
        }

        index = startIdx;
        return sb.toString();
    }

    private static String back(final String text, int index) {
        int startIdx = text.indexOf("** ", index);
        if (startIdx != -1) {
            return text;
        }

        startIdx = text.indexOf("**", index);
        if (startIdx == -1) {
            return text;
        }

        int endIdx = text.indexOf("**", startIdx + 3);
        if (endIdx == -1) {
            return text;
        }

        StringBuilder sb = new StringBuilder();
        if (endIdx != text.length() - 1) {
            sb.append(" ");
            sb.append(text.substring(endIdx + 2));
        }

        return sb.toString();
    }

    public static String changeMathJaxToCodeCogs(String text) {

        StringBuilder sb = new StringBuilder();

        while (true) {

            int startIdx, endIdx;
            if (isLineMath(text)) {
                startIdx = text.indexOf("$$");
                endIdx = text.indexOf("$$", startIdx + 1);
            } else if (isInLineMath(text)) {
                startIdx = text.indexOf("$");
                endIdx = text.indexOf("$", startIdx + 1);
            } else {
                sb.append(text);
                break;
            }

            String leftPartContent = text.substring(0, startIdx);

            while (startIdx < text.length() && text.charAt(startIdx) == '$')
                startIdx++;
            String mathJaxContent = text.substring(startIdx, endIdx);
            mathJaxContent = mathJaxContent.replaceAll(" ", "");
            mathJaxContent = "<img src=\"https://latex.codecogs.com/gif.latex?" + mathJaxContent + "\"/>";

            if (isLineMath(text)) {
                mathJaxContent = "<div align=\"center\">" + mathJaxContent + "</div> <br>";
            }

            while (endIdx < text.length() && text.charAt(endIdx) == '$')
                endIdx++;
            sb.append(leftPartContent).append(mathJaxContent);
            text = text.substring(endIdx);
        }

        return sb.toString();
    }

    private static boolean isLineMath(String text) {
        return hasPairs(text, "$$");
    }

    private static boolean isInLineMath(String text) {
        return hasPairs(text, "$");
    }

    private static boolean hasPairs(String text, String str) {

        int idx = text.indexOf(str);
        if (idx == -1 || (idx != 0 && text.charAt(idx - 1) == '\\')) {
            return false;
        }
        idx = text.indexOf(str, idx + 3);
        return idx != -1;
    }
}

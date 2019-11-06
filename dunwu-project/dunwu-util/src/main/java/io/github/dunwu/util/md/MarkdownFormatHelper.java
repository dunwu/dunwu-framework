package io.github.dunwu.util.md;

import io.github.dunwu.util.io.FileExtUtils;
import io.github.dunwu.util.text.RegexUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Markdown 格式化
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-02-25
 */
public class MarkdownFormatHelper {

	public static final String FRONT_MATTER_TAG = "---";

	public static final char CHAR_$ = '$';

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

			while (startIdx < text.length() && text.charAt(startIdx) == CHAR_$) {
				startIdx++;
			}
			String mathJaxContent = text.substring(startIdx, endIdx);
			mathJaxContent = mathJaxContent.replaceAll(" ", "");
			mathJaxContent = "<img src=\"https://latex.codecogs.com/gif.latex?"
				+ mathJaxContent + "\"/>";

			if (isLineMath(text)) {
				mathJaxContent = "<div align=\"center\">" + mathJaxContent
					+ "</div> <br>";
			}

			while (endIdx < text.length() && text.charAt(endIdx) == CHAR_$) {
				endIdx++;
			}
			sb.append(leftPartContent).append(mathJaxContent);
			text = text.substring(endIdx);
		}

		return sb.toString();
	}

	private static boolean isLineMath(String text) {
		return isPairs(text, "$$");
	}

	private static boolean isInLineMath(String text) {
		return isPairs(text, "$");
	}

	private static boolean isPairs(String text, String str) {

		int idx = text.indexOf(str);

		boolean flag = text.charAt(idx - 1) == '\\';
		if (idx == -1) {
			return false;
		}
		if (idx != 0 && flag) {
			return false;
		}
		idx = text.indexOf(str, idx + 3);
		return idx != -1;
	}

	public static void convertToGfm(String srcFilePath, String detFilePath) {

		List<String> contents = TxtFileUtil.readLineByLine(srcFilePath);

		List<String> newContents = new ArrayList<String>();

		boolean isCode = false;

		for (String text : contents) {
			text = replaceSpecialChars(text, isCode);

			if (RegexUtil.checkMatches(text, "date: \\d{4}/\\d{2}/\\d{2}")) {
				text = text.replaceAll("/", "-");
			}

			if (text.contains("```")) {
				isCode = !isCode;
			} else if (!isCode) {
				// text = changeMathJaxToCodeCogs(text);
				text = convertImgTag(text);
				// text = addSpaceInHtmlTag(text);
			}

			newContents.add(text);
		}

		newContents = Toc.changeTocToGeneratedCatalogue(newContents);
		// newContents = addFrontMatter(srcFilePath, newContents);

		TxtFileUtil.writeLineByLine(newContents, detFilePath);
	}

	public static String replaceSpecialChars(String text, boolean isCode) {

		if (!isCode) {
			text = text.replaceAll("([^\\\\])~", "$1\\\\~");
		}

		// System.out.println(" ".equals(" ")); // false, one is 160，one is 32
		text = text.replaceAll(" ", " ");

		return text;
	}

	public static String convertImgTag(final String text) {
		String newstr = RegexUtil.replaceAllMatchContent(text,
			RegexUtil.REGEX_MARKDOWN_IMAGE_TAG, "![]");

		boolean hasPic = newstr.contains("![]");
		if (!hasPic) {
			return newstr;
		}

		int startIdx = newstr.indexOf("![]");
		int endIdx = newstr.indexOf(")", startIdx);
		String picPath = newstr.substring(startIdx + 4, endIdx);
		newstr = "<img src=\"" + picPath + "\"/>";
		newstr = "<div align=\"center\">" + newstr + "</div>";

		return newstr;
	}

	private static List<String> addFrontMatter(String srcFilePath,
		List<String> contents) {
		if (CollectionUtils.isEmpty(contents)) {
			return contents;
		}

		String firstLine = contents.get(0);
		if (StringUtils.equals(firstLine, FRONT_MATTER_TAG)) {
			return contents;
		}

		String title = "";
		for (String line : contents) {
			if (line.startsWith("# ")) {
				title = StringUtils.substringAfter(line, "# ");
				break;
			}
		}

		String date = FileExtUtils.getFileCreateTimeString(srcFilePath, "yyyy-MM-dd");
		List<String> newContents = new ArrayList<>();
		newContents.add("---");
		newContents.add("title: " + title);
		newContents.add("date: " + date);
		newContents.add("---\n");
		newContents.addAll(contents);
		return newContents;
	}

	private static String addSpaceInHtmlTag(final String text) {
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

}

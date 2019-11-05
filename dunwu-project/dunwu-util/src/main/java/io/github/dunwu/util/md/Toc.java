package io.github.dunwu.util.md;

import java.util.ArrayList;
import java.util.List;

// Generate GFM support TOC tag
public class Toc {

	public static final char CHAR_SPACE = ' ';

	private final static String TOC_TAG_BEFORE = "[TOC]";

	private final static String TOC_TAG_AFTER = "<!-- GFM-TOC -->";

	// catalogue level
	private static int level = 4;

	public static void setCatalogueLevel(int level) {
		Toc.level = level;
	}

	public static List<String> changeTocToGeneratedCatalogue(List<String> contents) {

		List<String> ret = new ArrayList<String>();

		// If there already existed catalogue of document, then update this catalogue.
		boolean isInOldCatalogue = false;

		// Make sure generate once.
		boolean hasGenerated = false;

		for (String text : contents) {
			if (isInOldCatalogue) {
				if (text.contains(TOC_TAG_AFTER)) {
					ret.add(generateCatalogue(contents));
					hasGenerated = true;
					isInOldCatalogue = false;
				}
				continue;
			}
			if (hasGenerated) {
				ret.add(text);
			}
			else if (text.contains(TOC_TAG_AFTER)) {
				isInOldCatalogue = true;
			}
			else if (text.contains(TOC_TAG_BEFORE)) {
				ret.add(generateCatalogue(contents));
				hasGenerated = true;
			}
			else {
				ret.add(text);
			}
		}

		return ret;
	}

	private static String generateCatalogue(List<String> contents) {

		List<String> titles = getTitles(contents);

		StringBuilder sb = new StringBuilder();

		sb.append(TOC_TAG_AFTER).append(System.getProperty("line.separator"));

		for (String title : titles) {
			sb.append(formatTitle(title)).append(System.getProperty("line.separator"));
		}

		sb.append(TOC_TAG_AFTER).append(System.getProperty("line.separator"));

		return sb.toString();
	}

	private static List<String> getTitles(List<String> contents) {

		List<String> titles = new ArrayList<String>();

		boolean isCode = false;

		for (String line : contents) {
			if (line.contains("```")) {
				isCode = !isCode;
			}
			else if (line.startsWith("#") && !isCode
					&& getLevelOfTitle(line) <= Toc.level) {
				titles.add(line);
			}
		}

		return titles;
	}

	private static String formatTitle(String title) {

		StringBuilder ret = new StringBuilder();

		int level = getLevelOfTitle(title);
		for (int i = 1; i < level; i++) {
			ret.append("    ");
		}

		ret.append("*");
		ret.append(" ");

		int contentIdx = title.lastIndexOf("#");
		contentIdx++;
		while (title.charAt(contentIdx) == CHAR_SPACE) {
			contentIdx++;
		}

		String content = title.substring(contentIdx);

		content = content.trim();

		// replace ' ' to '-'
		String anchor = content.replaceAll(" ", "\\-");

		// remove spacial char
		anchor = anchor.replaceAll("[.：（）()*/、:+，]", "");

		// uppercase to lowercase
		anchor = anchor.toLowerCase();

		ret.append("[").append(content).append("]");
		ret.append("(#").append(anchor).append(")");

		return ret.toString();
	}

	private static int getLevelOfTitle(String title) {

		int cnt = 0;

		int idx = title.indexOf("#");
		while (idx != -1) {
			cnt++;
			idx = title.indexOf("#", idx + 1);
		}

		return cnt;
	}

}

package io.github.dunwu.util.md;

import java.io.File;
import java.util.Scanner;

public class MarkdownFormatCmdApp {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		String markdownFilePath;

		while (true) {

			System.out.print("File path : ");

			markdownFilePath = in.nextLine();
			if (new File(markdownFilePath).exists()) {
				break;
			}
			System.out.println("File not exist!");
		}

		Toc.setCatalogueLevel(3);

		String newMarkdownFilePath = markdownFilePath.substring(0, markdownFilePath.length() - 3) + ".gfm.md";

		MarkdownFormatHelper.convertToGfm(markdownFilePath, newMarkdownFilePath);

		System.out.println(newMarkdownFilePath + " has been generated!");
	}

}

package io.github.dunwu.util.io;

import io.github.dunwu.util.RandomExtUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileExtUtilsTest {

	@Test
	void readWrite() throws IOException {
		File file = FileExtUtils.createTempFile("abc", ".tmp").toFile();
		try {
			String content = "haha\nhehe";
			FileExtUtils.write(file, content, StandardCharsets.UTF_8);

			String result = FileExtUtils.readFileToString(file, StandardCharsets.UTF_8);
			assertThat(result).isEqualTo(content);
			List<String> lines = FileExtUtils.readLines(file, StandardCharsets.UTF_8);
			assertThat(lines).containsExactly("haha", "hehe");

			FileExtUtils.append("kaka", file);
			assertThat(
				new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8))
				.isEqualTo("haha\nhehekaka");
		} finally {
			FileExtUtils.deleteFile(file);
		}
	}

	@Test
	void opFiles() throws IOException {
		File file = new File(FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR,
			"testFile" + RandomUtils.nextInt()));
		FileExtUtils.touch(file);
		assertThat(FileExtUtils.isFileExists(file)).isTrue();
		FileExtUtils.touch(file);

		String content = "haha\nhehe";
		FileExtUtils.write(file, content, StandardCharsets.UTF_8);
		assertThat(FileExtUtils.readFileToString(file, StandardCharsets.UTF_8))
			.isEqualTo(content);

		File newFile = new File(FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR,
			"testFile" + RandomUtils.nextInt()));
		File newFile2 = new File(FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR,
			"testFile" + RandomUtils.nextInt()));

		FileExtUtils.copyFile(file, newFile);
		assertThat(FileExtUtils.isFileExists(newFile)).isTrue();
		assertThat(FileExtUtils.readFileToString(newFile, StandardCharsets.UTF_8))
			.isEqualTo(content);

		FileExtUtils.moveFile(newFile, newFile2);
		assertThat(FileExtUtils.readFileToString(newFile2, StandardCharsets.UTF_8))
			.isEqualTo("haha\nhehe");
	}

	@Test
	void opDir() throws IOException {
		String fileName = "testFile" + RandomUtils.nextInt();
		File dir = new File(FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir"));

		File file = new File(
			FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir", fileName));
		String content = "haha\nhehe";
		FileExtUtils.makesureDirExists(dir);
		FileExtUtils.write(file, content, StandardCharsets.UTF_8);

		File dir2 = new File(FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir2"));
		FileExtUtils.copyDirectory(dir, dir2);
		File file2 = new File(
			FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir2", fileName));
		assertThat(FileExtUtils.readFileToString(file2, StandardCharsets.UTF_8))
			.isEqualTo("haha\nhehe");

		File dir3 = new File(FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir3"));
		FileExtUtils.moveDirectory(dir, dir3);
		File file3 = new File(
			FileExtUtils.concat(SystemUtils.JAVA_IO_TMPDIR, "testDir3", fileName));
		assertThat(FileExtUtils.readFileToString(file3, StandardCharsets.UTF_8))
			.isEqualTo("haha\nhehe");
		assertThat(FileExtUtils.isDirectoryExists(dir)).isFalse();
	}

	@Test
	void fileExist() throws IOException {
		assertThat(FileExtUtils.isDirectoryExists(SystemUtils.JAVA_IO_TMPDIR)).isTrue();
		assertThat(FileExtUtils
			.isDirectoryExists(SystemUtils.JAVA_IO_TMPDIR + RandomUtils.nextInt()))
			.isFalse();

		File tmpFile = null;
		try {
			tmpFile = FileExtUtils.createTempFile().toFile();
			assertThat(FileExtUtils.isFileExists(tmpFile)).isTrue();

			assertThat(FileExtUtils
				.isFileExists(tmpFile.getAbsolutePath() + RandomUtils.nextInt()))
				.isFalse();
		} finally {
			FileExtUtils.deleteFile(tmpFile);
		}
	}

	@Test
	void getName() {

		assertThat(FileExtUtils.getFileName(FileExtUtils.normalizePath("/a/d/b/abc.txt")))
			.isEqualTo("abc.txt");
		assertThat(FileExtUtils.getFileName("abc.txt")).isEqualTo("abc.txt");

		assertThat(FileExtUtils
			.getFileExtension(FileExtUtils.normalizePath("a/d/b/abc.txt")))
			.isEqualTo("txt");
		assertThat(
			FileExtUtils.getFileExtension(FileExtUtils.normalizePath("/a/d/b/abc")))
			.isEqualTo("");
		assertThat(
			FileExtUtils.getFileExtension(FileExtUtils.normalizePath("/a/d/b/abc.")))
			.isEqualTo("");
	}

	@Test
	void newInputStream() throws Exception {
		Path path = FileExtUtils.createTempFile();
		assertThat(Files.newInputStream(path)).isNotNull();
	}

	@Test
	void newOutputStream() throws Exception {
		Path path = FileExtUtils.createTempFile();
		assertThat(Files.newOutputStream(path)).isNotNull();
	}

	@Test
	void newBufferedReader() throws Exception {
		Path path = FileExtUtils.createTempFile();
		assertThat(Files.newBufferedReader(path)).isNotNull();
	}

	@Test
	void newBufferedWriter() throws Exception {
		Path path = FileExtUtils.createTempFile();
		assertThat(Files.newBufferedWriter(path)).isNotNull();
	}

	@Test
	void copy() throws Exception {
		Path tempDir = FileExtUtils.createTempDir();
		assertThat(tempDir).exists();

		String srcFileName = "src";
		File srcFile = tempDir.resolve(srcFileName).toFile();
		FileExtUtils.touch(srcFile);
		assertThat(srcFile).exists();

		FileExtUtils.write(srcFile, "test", StandardCharsets.UTF_8);
		String destFileName = "dest";
		File destFile = new File(tempDir.toFile(), "parent1/parent2/" + destFileName);
		FileExtUtils.makesureParentDirExists(destFile);

		FileExtUtils.copyFile(srcFile, destFile);
		List<String> lines = FileUtils.readLines(destFile, StandardCharsets.UTF_8);
		assertThat(lines.get(0)).isEqualTo("test");
	}

	@Test
	void testMakesureDirExists() throws Exception {
		Path dir = FileExtUtils.createTempDir();
		String child1 = "child1";

		Path child1Dir = dir.resolve(child1);
		FileExtUtils.makesureDirExists(child1Dir.toString());
		assertThat(child1Dir).exists();

		String child2 = "child2";
		Path child2Dir = dir.resolve(child2);
		FileExtUtils.makesureDirExists(child2Dir);
		assertThat(child2Dir).exists();

		String child3 = "child3";
		Path child3Dir = dir.resolve(child3);
		FileExtUtils.makesureDirExists(child3Dir.toFile());
		assertThat(child3Dir).exists();
	}

	@Test
	void testIsFileExists() throws Exception {
		assertThat(FileExtUtils.isFileExists((String) null)).isFalse();
		assertThat(FileExtUtils.isFileExists((File) null)).isFalse();

		Path dir = FileExtUtils.createTempDir();
		FileExtUtils.touch(dir + "/" + "test");

		assertThat(FileExtUtils.isFileExists(dir + "/" + "test")).isTrue();

		assertThat(FileExtUtils.isFileExists(dir.resolve("test").toFile())).isTrue();
	}

	@Test
	void testGetFileExtension() throws Exception {
		Path path = FileExtUtils.createTempFile("aaa", ".txt");

		assertThat(FileExtUtils.getFileExtension(path.toFile())).isEqualTo("txt");
		assertThat(FileExtUtils.getFileExtension(path.toString())).isEqualTo("txt");
	}

	@Test
	void testIsDirExists() throws Exception {
		assertThat(FileExtUtils.isDirectoryExists((String) null)).isFalse();
		assertThat(FileExtUtils.isDirectoryExists((File) null)).isFalse();
		assertThat(FileExtUtils.isDirectoryExists((Path) null)).isFalse();

		Path dir = FileExtUtils.createTempDir();

		assertThat(FileExtUtils.isDirectoryExists(dir)).isTrue();
		assertThat(FileExtUtils.isDirectoryExists(dir.toString())).isTrue();
		assertThat(FileExtUtils.isDirectoryExists(dir.toFile())).isTrue();
	}

	@Test
	void changeFileNameToStandard() {
		boolean flag = FileExtUtils.changeFileNameToStandard(
			"D:\\Codes\\ZP\\Others\\images\\images\\design\\refactor\\Change Bidirectional Association to "
				+ "Unidirectional - After.png");
		System.out.println(flag);
	}

	@Test
	void changeFileNameToStandardInFolder() {
		FileExtUtils.changeFileNameToStandardInFolder(
			new File("D:\\Codes\\ZP\\Others\\images\\images\\design\\refactor"));
	}

	@Test
	void pathName() {
		String filePath = FileExtUtils.concat(File.separatorChar + "abc", "ef");
		assertThat(filePath).isEqualTo(FileExtUtils.normalizePath("/abc/ef"));

		String filePath2 = FileExtUtils
			.concat(File.separatorChar + "stuv" + File.separatorChar, "xy");
		assertThat(filePath2).isEqualTo(FileExtUtils.normalizePath("/stuv/xy"));

		assertThat(com.google.common.io.Files.simplifyPath("../dd/../abc"))
			.isEqualTo("../abc");
		assertThat(com.google.common.io.Files.simplifyPath("../../dd/../abc"))
			.isEqualTo("../../abc");
		assertThat(com.google.common.io.Files.simplifyPath("./abc")).isEqualTo("abc");

		assertThat(FileExtUtils.getParentPath(FileExtUtils.normalizePath("/abc/dd/efg/")))
			.isEqualTo(FileExtUtils.normalizePath("/abc/dd/"));

		assertThat(
			FileExtUtils.getParentPath(FileExtUtils.normalizePath("/abc/dd/efg.txt")))
			.isEqualTo(FileExtUtils.normalizePath("/abc/dd/"));
	}

	@Test
	void getJarPath() {
		System.out.println("the jar file contains Files.class"
			+ FileExtUtils.getJarPath(com.google.common.io.Files.class));
		assertThat(FileExtUtils.getJarPath(com.google.common.io.Files.class))
			.endsWith("guava-20.0.jar");
	}

	@Test
	void listFile() throws IOException {
		File tmpDir = FileExtUtils.createTempDir().toFile();

		List<File> all = FileExtUtils.listAll(tmpDir);
		assertThat(all).hasSize(1);

		List<File> files = FileExtUtils.listFile(tmpDir);
		assertThat(files).hasSize(0);

		FileExtUtils.touch(FileExtUtils.concat(tmpDir.getAbsolutePath(),
			"tmp-" + RandomExtUtils.nextInt()) + ".tmp");
		FileExtUtils.touch(FileExtUtils.concat(tmpDir.getAbsolutePath(),
			"tmp-" + RandomExtUtils.nextInt()) + ".abc");

		String childDir = FileExtUtils.concat(tmpDir.getAbsolutePath(),
			"tmp-" + RandomExtUtils.nextInt());
		FileExtUtils.makesureDirExists(childDir);

		FileExtUtils
			.touch(FileExtUtils.concat(childDir, "tmp-" + RandomExtUtils.nextInt())
				+ ".tmp");

		all = FileExtUtils.listAll(tmpDir);
		assertThat(all).hasSize(5);

		files = FileExtUtils.listFile(tmpDir);
		assertThat(files).hasSize(3);

		// extension
		files = FileExtUtils.listFileWithExtension(tmpDir, "tmp");
		assertThat(files).hasSize(2);

		files = FileExtUtils.listFileWithExtension(tmpDir, "tp");
		assertThat(files).hasSize(0);

		// wildcard
		files = FileExtUtils.listFileWithWildcardFileName(tmpDir, "*.tmp");
		assertThat(files).hasSize(2);
		files = FileExtUtils.listFileWithWildcardFileName(tmpDir, "*.tp");
		assertThat(files).hasSize(0);

		// regex
		files = FileExtUtils.listFileWithRegexFileName(tmpDir, ".*\\.tmp");
		assertThat(files).hasSize(2);
		files = FileExtUtils.listFileWithRegexFileName(tmpDir, ".*\\.tp");
		assertThat(files).hasSize(0);

		FileExtUtils.deleteDirectory(tmpDir);
		assertThat(FileExtUtils.isDirectoryExists(tmpDir)).isFalse();
	}

}

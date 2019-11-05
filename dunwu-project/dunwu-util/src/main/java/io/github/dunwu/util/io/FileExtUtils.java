package io.github.dunwu.util.io;

import com.google.common.base.Predicate;
import io.github.dunwu.util.SystemExtUtils;
import io.github.dunwu.util.text.TextUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * 关于文件的工具集. 主要是调用JDK自带的Files工具类，少量代码调用Guava Files。 固定encoding为UTF8. 1.文件读写 2.文件及目录操作
 */
@SuppressWarnings(value = { "all" })
public class FileExtUtils extends FileUtils {

	/**
	 * 追加String到File.
	 */
	public static void append(final CharSequence data, final File file)
		throws IOException {
		Validate.notNull(file);
		Validate.notNull(data);

		try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(),
			StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
			writer.append(data);
		}
	}

	public static Path getPath(String filePath) {
		return Paths.get(filePath);
	}

	/**
	 * 获得上层目录的路径
	 */
	public static String getParentPath(String path) {
		String parentPath = path;

		if (File.separator.equals(parentPath)) {
			return parentPath;
		}

		parentPath = StringUtils.removeEnd(parentPath, File.separator);
		int idx = parentPath.lastIndexOf(File.separator);
		if (idx >= 0) {
			parentPath = parentPath.substring(0, idx + 1);
		} else {
			parentPath = File.separator;
		}

		return parentPath;
	}

	/**
	 * 创建文件或更新时间戳.
	 *
	 * @see {@link com.google.common.io.Files#touch}
	 */
	public static void touch(String filePath) throws IOException {
		touch(new File(filePath));
	}

	/**
	 * 创建文件或更新时间戳.
	 *
	 * @see {@link com.google.common.io.Files#touch}
	 */
	public static void touch(File file) throws IOException {
		com.google.common.io.Files.touch(file);
	}

	/**
	 * 删除文件. 如果文件不存在或者是目录，则不做修改
	 */
	public static void deleteFile(File file) throws IOException {
		Validate.isTrue(isFileExists(file), "%s is not exist or not a file", file);
		deleteFile(file.toPath());
	}

	/**
	 * 删除文件. 如果文件不存在或者是目录，则不做修改
	 */
	public static void deleteFile(Path path) throws IOException {
		Validate.isTrue(isFileExists(path), "%s is not exist or not a file", path);

		Files.delete(path);
	}

	// exists
	// -------------------------------------------------------------------------------------------------

	/**
	 * 判断文件是否存在
	 */
	public static boolean isFileExists(Path path) {
		if (path == null) {
			return false;
		}
		return Files.exists(path) && Files.isRegularFile(path);
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean isFileExists(File file) {
		return isFileExists(file.toPath());
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean isFileExists(String fileName) {
		return isFileExists(getPath(fileName));
	}

	/**
	 * 判断目录是否存在
	 */
	public static boolean isDirectoryExists(Path dirPath) {
		return dirPath != null && Files.exists(dirPath) && Files.isDirectory(dirPath);
	}

	/**
	 * 判断目录是否存在
	 */
	public static boolean isDirectoryExists(String dirPath) {
		return isDirectoryExists(getPath(dirPath));
	}

	/**
	 * 判断目录是否存在
	 */
	public static boolean isDirectoryExists(File dir) {
		return isDirectoryExists(dir.toPath());
	}

	/**
	 * 确保目录存在, 如不存在则创建
	 */
	public static void makesureDirExists(String dirPath) throws IOException {
		makesureDirExists(getPath(dirPath));
	}

	/**
	 * 确保目录存在, 如不存在则创建.
	 *
	 * @see {@link Files#createDirectories}
	 */
	public static void makesureDirExists(Path dirPath) throws IOException {
		Validate.notNull(dirPath);
		Files.createDirectories(dirPath);
	}

	/**
	 * 确保父目录及其父目录直到根目录都已经创建.
	 */
	public static void makesureParentDirExists(File file) throws IOException {
		Validate.notNull(file);
		makesureDirExists(file.getParentFile());
	}

	/**
	 * 确保目录存在, 如不存在则创建
	 */
	public static void makesureDirExists(File file) throws IOException {
		Validate.notNull(file);
		makesureDirExists(file.toPath());
	}

	/**
	 * 在临时目录创建临时目录，命名为${毫秒级时间戳}-${同一毫秒内的随机数}.
	 *
	 * @see {@link Files#createTempDirectory}
	 */
	public static Path createTempDir() throws IOException {
		return Files.createTempDirectory(System.currentTimeMillis() + "-");
	}

	/**
	 * 在临时目录创建临时文件，命名为tmp-${random.nextLong()}.tmp
	 *
	 * @see {@link Files#createTempFile}
	 */
	public static Path createTempFile() throws IOException {
		return Files.createTempFile("tmp-", ".tmp");
	}

	/**
	 * 在临时目录创建临时文件，命名为${prefix}${random.nextLong()}${suffix}
	 *
	 * @see {@link Files#createTempFile}
	 */
	public static Path createTempFile(String prefix, String suffix) throws IOException {
		return Files.createTempFile(prefix, suffix);
	}

	// 获取文件信息
	// -------------------------------------------------------------------------------------------------

	/**
	 * 获取文件名(不包含路径)
	 */
	public static String getFileName(String fullName) {
		Validate.notEmpty(fullName);
		int last = fullName.lastIndexOf(File.separatorChar);
		return fullName.substring(last + 1);
	}

	/**
	 * 获取文件名的扩展名部分(不包含.)
	 *
	 * @see {@link com.google.common.io.Files#getFileExtension}
	 */
	public static String getFileExtension(File file) {
		return com.google.common.io.Files.getFileExtension(file.getName());
	}

	/**
	 * 获取文件名的扩展名部分(不包含.)
	 *
	 * @see {@link com.google.common.io.Files#getFileExtension}
	 */
	public static String getFileExtension(String fullName) {
		return com.google.common.io.Files.getFileExtension(fullName);
	}

	public static String getFileCreateTimeString(String fullName, String pattern) {
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		Long time = getFileCreateTime(fullName);
		if (time == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return sdf.format(calendar.getTime());
	}

	public static Long getFileCreateTime(String fullName) {
		Path path = Paths.get(fullName);
		BasicFileAttributeView basicview = Files.getFileAttributeView(path,
			BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		try {
			BasicFileAttributes attr = basicview.readAttributes();
			FileTime createTime = attr.creationTime();
			System.out.println("createTime = [" + createTime.toString() + "]");
			return createTime.toMillis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// changeFileNameToStandard
	// 将文件名中的空白去除，并将文件名转为小写
	// -------------------------------------------------------------------------------------------------

	public static boolean changeFileNameToStandard(String filePath) {
		return changeFileNameToStandard(new File(filePath));
	}

	public static boolean changeFileNameToStandard(File file) {
		if (!file.exists()) {
			return false;
		}

		String path = file.getParent();
		String name = file.getName();
		name = name.replaceAll(" - ", "-");
		name = name.replaceAll(" ", "-");
		String newPath = path + File.separator + name.toLowerCase();
		return file.renameTo(new File(newPath));
	}

	public static void changeFileNameToStandardInFolder(File root) {
		if (!root.exists()) {
			return;
		}

		if (!root.isDirectory()) {
			changeFileNameToStandard(root);
		}

		File[] files = root.listFiles();
		if (ArrayUtils.isNotEmpty(files)) {
			for (File f : files) {
				if (f.isDirectory()) {
					changeFileNameToStandardInFolder(f);
				} else {
					changeFileNameToStandard(f);
				}
			}
		}
	}

	// listFile
	// -------------------------------------------------------------------------------------------------

	/**
	 * 前序递归列出所有文件, 包含文件与目录，及根目录本身. 前序即先列出父目录，在列出子目录. 如要后序遍历, 直接使用Files.fileTreeTraverser()
	 */
	public static List<File> listAll(File rootDir) {
		return com.google.common.io.Files.fileTreeTraverser().preOrderTraversal(rootDir).toList();
	}

	/**
	 * 前序递归列出所有文件, 只包含文件.
	 */
	public static List<File> listFile(File rootDir) {
		return com.google.common.io.Files.fileTreeTraverser().preOrderTraversal(rootDir).filter(
			com.google.common.io.Files.isFile())
			.toList();
	}

	/**
	 * 前序递归列出所有文件, 列出后缀名匹配的文件. （后缀名不包含.）
	 */
	public static List<File> listFileWithExtension(final File rootDir,
		final String extension) {
		return com.google.common.io.Files.fileTreeTraverser().preOrderTraversal(rootDir)
			.filter(new FileExtensionFilter(extension)).toList();
	}

	/**
	 * 前序递归列出所有文件, 列出文件名匹配通配符的文件 如 ("/a/b/hello.txt", "he*") 将被返回
	 */
	public static List<File> listFileWithWildcardFileName(final File rootDir,
		final String fileNamePattern) {
		return com.google.common.io.Files.fileTreeTraverser().preOrderTraversal(rootDir)
			.filter(new WildcardFileNameFilter(fileNamePattern)).toList();
	}

	/**
	 * 前序递归列出所有文件, 列出文件名匹配正则表达式的文件 如 ("/a/b/hello.txt", "he.*\.txt") 将被返回
	 */
	public static List<File> listFileWithRegexFileName(final File rootDir,
		final String regexFileNamePattern) {
		return com.google.common.io.Files.fileTreeTraverser().preOrderTraversal(rootDir)
			.filter(new RegexFileNameFilter(regexFileNamePattern)).toList();
	}

	/**
	 * 以文件名正则表达式为filter，配合fileTreeTraverser使用
	 */
	private static final class RegexFileNameFilter implements Predicate<File> {

		private final Pattern pattern;

		private RegexFileNameFilter(String pattern) {
			this.pattern = Pattern.compile(pattern);
		}

		@Override
		public boolean apply(File file) {
			return file.isFile() && pattern.matcher(file.getName()).matches();
		}

	}

	/**
	 * 以文件名通配符为filter，配合fileTreeTraverser使用.
	 * <p>
	 * 支持*与?的通配符，如hello*.txt 匹配 helloworld.txt
	 */
	private static final class WildcardFileNameFilter implements Predicate<File> {

		private final String pattern;

		private WildcardFileNameFilter(String pattern) {
			this.pattern = pattern;
		}

		@Override
		public boolean apply(File file) {
			return file.isFile() && TextUtil.match(file.getName(), pattern);
		}

	}

	/**
	 * 以文件名后缀做filter，配合fileTreeTraverser使用
	 */
	private static final class FileExtensionFilter implements Predicate<File> {

		private final String extension;

		private FileExtensionFilter(String extension) {
			this.extension = extension;
		}

		@Override
		public boolean apply(File file) {
			return file.isFile()
				&& extension.equals(FileExtUtils.getFileExtension(file));
		}

	}

	/**
	 * 在Windows环境里，兼容Windows上的路径分割符，将 '/' 转回 '\'
	 */
	public static String normalizePath(String path) {
		if (File.separator.equals(SystemExtUtils.WINDOWS_FILE_PATH_SEPARATOR)
			&& StringUtils.indexOf(path,
			SystemExtUtils.LINUX_FILE_PATH_SEPARATOR) != -1) {
			return StringUtils.replaceChars(path,
				SystemExtUtils.LINUX_FILE_PATH_SEPARATOR,
				SystemExtUtils.WINDOWS_FILE_PATH_SEPARATOR);
		}
		return path;
	}

	/**
	 * 以拼接路径名
	 */
	public static String concat(String baseName, String... appendName) {
		if (appendName.length == 0) {
			return baseName;
		}

		StringBuilder concatName = new StringBuilder();
		if (StringUtils.endsWith(baseName, File.separator)) {
			concatName.append(baseName).append(appendName[0]);
		} else {
			concatName.append(baseName).append(File.separator).append(appendName[0]);
		}

		if (appendName.length > 1) {
			for (int i = 1; i < appendName.length; i++) {
				concatName.append(File.separator).append(appendName[i]);
			}
		}

		return concatName.toString();
	}


	/**
	 * 获得参数clazz所在的Jar文件的绝对路径
	 */
	public static String getJarPath(Class<?> clazz) {
		return clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
	}

}

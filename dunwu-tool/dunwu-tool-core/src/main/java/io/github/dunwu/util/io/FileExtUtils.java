package io.github.dunwu.util.io;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import io.github.dunwu.util.SystemExtUtils;
import io.github.dunwu.util.base.StringExtUtils;
import io.github.dunwu.util.number.UnitConvertUtils;
import io.github.dunwu.util.text.TextUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 关于文件的工具集. 主要是调用JDK自带的Files工具类，少量代码调用Guava Files。 固定encoding为UTF8. 1.文件读写 2.文件及目录操作
 */
@SuppressWarnings(value = { "all" })
public class FileExtUtils {

	/**
	 * The file copy buffer size (30 MB)
	 */
	private static final long FILE_COPY_BUFFER_SIZE = UnitConvertUtils.SIZE_MB * 30;

	/**
	 * 追加String到File.
	 */
	public static void append(final CharSequence data, final File file) throws IOException {
		Validate.notNull(file);
		Validate.notNull(data);

		try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(),
			StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
			writer.append(data);
		}
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

	/**
	 * Cleans a directory without deleting it.
	 *
	 * @param directory directory to clean
	 * @throws IOException              in case cleaning is unsuccessful
	 * @throws IllegalArgumentException if {@code directory} does not exist or is not a directory
	 */
	public static void cleanDirectory(final File directory) throws IOException {
		final File[] files = verifiedListFiles(directory);

		IOException exception = null;
		for (final File file : files) {
			try {
				forceDelete(file);
			} catch (final IOException ioe) {
				exception = ioe;
			}
		}

		if (null != exception) {
			throw exception;
		}
	}

	// copyFile
	// -------------------------------------------------------------------------------------------------

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
	 * Copies a whole directory to a new location preserving the file dates.
	 * <p>
	 * This method copies the specified directory and all its child directories and files to the specified destination.
	 * The destination is the new location and name of the directory.
	 * <p>
	 * The destination directory is created if it does not exist. If the destination directory did exist, then this
	 * method merges the source with the destination, with the source taking precedence.
	 * <p>
	 * <strong>Note:</strong> This method tries to preserve the files' last
	 * modified date/times using {@link File#setLastModified(long)}, however it is not guaranteed that those operations
	 * will succeed. If the modification operation fails, no indication is provided.
	 *
	 * @param srcDir  an existing directory to copy, must not be {@code null}
	 * @param destDir the new directory, must not be {@code null}
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @since 1.1
	 */
	public static void copyDirectory(final File srcDir, final File destDir) throws IOException {
		copyDirectory(srcDir, destDir, true);
	}

	/**
	 * Copies a whole directory to a new location.
	 * <p>
	 * This method copies the contents of the specified source directory to within the specified destination directory.
	 * <p>
	 * The destination directory is created if it does not exist. If the destination directory did exist, then this
	 * method merges the source with the destination, with the source taking precedence.
	 * <p>
	 * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
	 * {@code true} tries to preserve the files' last modified date/times using {@link File#setLastModified(long)},
	 * however it is not guaranteed that those operations will succeed. If the modification operation fails, no
	 * indication is provided.
	 *
	 * @param srcDir           an existing directory to copy, must not be {@code null}
	 * @param destDir          the new directory, must not be {@code null}
	 * @param preserveFileDate true if the file date of the copy should be the same as the original
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @since 1.1
	 */
	public static void copyDirectory(final File srcDir, final File destDir,
		final boolean preserveFileDate) throws IOException {
		copyDirectory(srcDir, destDir, null, preserveFileDate);
	}

	/**
	 * Copies a filtered directory to a new location preserving the file dates.
	 * <p>
	 * This method copies the contents of the specified source directory to within the specified destination directory.
	 * <p>
	 * The destination directory is created if it does not exist. If the destination directory did exist, then this
	 * method merges the source with the destination, with the source taking precedence.
	 * <p>
	 * <strong>Note:</strong> This method tries to preserve the files' last
	 * modified date/times using {@link File#setLastModified(long)}, however it is not guaranteed that those operations
	 * will succeed. If the modification operation fails, no indication is provided.
	 * </p>
	 * <h3>Example: Copy directories only</h3>
	 * <pre>
	 *  // only copy the directory structure
	 *  FileUtils.copyDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY);
	 *  </pre>
	 *
	 * <h3>Example: Copy directories and txt files</h3>
	 * <pre>
	 *  // Create a filter for ".txt" files
	 *  IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".txt");
	 *  IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);
	 *
	 *  // Create a filter for either directories or ".txt" files
	 *  FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, txtFiles);
	 *
	 *  // Copy using the filter
	 *  FileUtils.copyDirectory(srcDir, destDir, filter);
	 *  </pre>
	 *
	 * @param srcDir  an existing directory to copy, must not be {@code null}
	 * @param destDir the new directory, must not be {@code null}
	 * @param filter  the filter to apply, null means copy all directories and files should be the same as the original
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @since 1.4
	 */
	public static void copyDirectory(final File srcDir, final File destDir,
		final FileFilter filter) throws IOException {
		copyDirectory(srcDir, destDir, filter, true);
	}

	/**
	 * Copies a filtered directory to a new location.
	 * <p>
	 * This method copies the contents of the specified source directory to within the specified destination directory.
	 * <p>
	 * The destination directory is created if it does not exist. If the destination directory did exist, then this
	 * method merges the source with the destination, with the source taking precedence.
	 * <p>
	 * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
	 * {@code true} tries to preserve the files' last modified date/times using {@link File#setLastModified(long)},
	 * however it is not guaranteed that those operations will succeed. If the modification operation fails, no
	 * indication is provided.
	 * </p>
	 * <h3>Example: Copy directories only</h3>
	 * <pre>
	 *  // only copy the directory structure
	 *  FileUtils.copyDirectory(srcDir, destDir, DirectoryFileFilter.DIRECTORY, false);
	 *  </pre>
	 *
	 * <h3>Example: Copy directories and txt files</h3>
	 * <pre>
	 *  // Create a filter for ".txt" files
	 *  IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".txt");
	 *  IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);
	 *
	 *  // Create a filter for either directories or ".txt" files
	 *  FileFilter filter = FileFilterUtils.orFileFilter(DirectoryFileFilter.DIRECTORY, txtFiles);
	 *
	 *  // Copy using the filter
	 *  FileUtils.copyDirectory(srcDir, destDir, filter, false);
	 *  </pre>
	 *
	 * @param srcDir           an existing directory to copy, must not be {@code null}
	 * @param destDir          the new directory, must not be {@code null}
	 * @param filter           the filter to apply, null means copy all directories and files
	 * @param preserveFileDate true if the file date of the copy should be the same as the original
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @since 1.4
	 */
	public static void copyDirectory(final File srcDir, final File destDir,
		final FileFilter filter, final boolean preserveFileDate) throws IOException {
		checkFileRequirements(srcDir, destDir);
		if (!srcDir.isDirectory()) {
			throw new IOException("Source '" + srcDir + "' exists but is not a directory");
		}
		if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
			throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
		}

		// Cater for destination being directory within the source directory (see IO-141)
		List<String> exclusionList = null;
		if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
			final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
			if (srcFiles != null && srcFiles.length > 0) {
				exclusionList = new ArrayList<>(srcFiles.length);
				for (final File srcFile : srcFiles) {
					final File copiedFile = new File(destDir, srcFile.getName());
					exclusionList.add(copiedFile.getCanonicalPath());
				}
			}
		}
		doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
	}

	/**
	 * Copies a directory to within another directory preserving the file dates.
	 * <p>
	 * This method copies the source directory and all its contents to a directory of the same name in the specified
	 * destination directory.
	 * <p>
	 * The destination directory is created if it does not exist. If the destination directory did exist, then this
	 * method merges the source with the destination, with the source taking precedence.
	 * <p>
	 * <strong>Note:</strong> This method tries to preserve the files' last
	 * modified date/times using {@link File#setLastModified(long)}, however it is not guaranteed that those operations
	 * will succeed. If the modification operation fails, no indication is provided.
	 *
	 * @param srcDir  an existing directory to copy, must not be {@code null}
	 * @param destDir the directory to place the copy in, must not be {@code null}
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @since 1.2
	 */
	public static void copyDirectoryToDirectory(final File srcDir, final File destDir) throws IOException {
		if (srcDir == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (srcDir.exists() && srcDir.isDirectory() == false) {
			throw new IllegalArgumentException("Source '" + destDir + "' is not a directory");
		}
		if (destDir == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (destDir.exists() && destDir.isDirectory() == false) {
			throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
		}
		copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
	}

	/**
	 * Copies a file to a new location preserving the file date.
	 * <p>
	 * This method copies the contents of the specified source file to the specified destination file. The directory
	 * holding the destination file is created if it does not exist. If the destination file exists, then this method
	 * will overwrite it.
	 * <p>
	 * <strong>Note:</strong> This method tries to preserve the file's last
	 * modified date/times using {@link File#setLastModified(long)}, however it is not guaranteed that the operation
	 * will succeed. If the modification operation fails, no indication is provided.
	 *
	 * @param srcFile  an existing file to copy, must not be {@code null}
	 * @param destFile the new file, must not be {@code null}
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @throws IOException          if the output file length is not the same as the input file length after the copy
	 *                              completes
	 * @see #copyFileToDirectory(File, File)
	 * @see #copyFile(File, File, boolean)
	 */
	public static void copyFile(final File srcFile, final File destFile) throws IOException {
		copyFile(srcFile, destFile, true);
	}

	/**
	 * Copies a file or directory to within another directory preserving the file dates.
	 * <p>
	 * This method copies the source file or directory, along all its contents, to a directory of the same name in the
	 * specified destination directory.
	 * <p>
	 * The destination directory is created if it does not exist. If the destination directory did exist, then this
	 * method merges the source with the destination, with the source taking precedence.
	 * <p>
	 * <strong>Note:</strong> This method tries to preserve the files' last
	 * modified date/times using {@link File#setLastModified(long)}, however it is not guaranteed that those operations
	 * will succeed. If the modification operation fails, no indication is provided.
	 *
	 * @param src     an existing file or directory to copy, must not be {@code null}
	 * @param destDir the directory to place the copy in, must not be {@code null}
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @see #copyDirectoryToDirectory(File, File)
	 * @see #copyFileToDirectory(File, File)
	 * @since 2.6
	 */
	public static void copyToDirectory(final File src, final File destDir) throws IOException {
		if (src == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (src.isFile()) {
			copyFileToDirectory(src, destDir);
		} else if (src.isDirectory()) {
			copyDirectoryToDirectory(src, destDir);
		} else {
			throw new IOException("The source " + src + " does not exist");
		}
	}

	// copyDirectory
	// -------------------------------------------------------------------------------------------------

	/**
	 * Copies a files to a directory preserving each file's date.
	 * <p>
	 * This method copies the contents of the specified source files to a file of the same name in the specified
	 * destination directory. The destination directory is created if it does not exist. If the destination file exists,
	 * then this method will overwrite it.
	 * <p>
	 * <strong>Note:</strong> This method tries to preserve the file's last
	 * modified date/times using {@link File#setLastModified(long)}, however it is not guaranteed that the operation
	 * will succeed. If the modification operation fails, no indication is provided.
	 *
	 * @param srcs    a existing files to copy, must not be {@code null}
	 * @param destDir the directory to place the copy in, must not be {@code null}
	 * @throws NullPointerException if source or destination is null
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @see #copyFileToDirectory(File, File)
	 * @since 2.6
	 */
	public static void copyToDirectory(final Iterable<File> srcs, final File destDir) throws IOException {
		if (srcs == null) {
			throw new NullPointerException("Sources must not be null");
		}
		for (final File src : srcs) {
			copyFileToDirectory(src, destDir);
		}
	}

	/**
	 * Copies a file to a directory preserving the file date.
	 * <p>
	 * This method copies the contents of the specified source file to a file of the same name in the specified
	 * destination directory. The destination directory is created if it does not exist. If the destination file exists,
	 * then this method will overwrite it.
	 * <p>
	 * <strong>Note:</strong> This method tries to preserve the file's last
	 * modified date/times using {@link File#setLastModified(long)}, however it is not guaranteed that the operation
	 * will succeed. If the modification operation fails, no indication is provided.
	 *
	 * @param srcFile an existing file to copy, must not be {@code null}
	 * @param destDir the directory to place the copy in, must not be {@code null}
	 * @throws NullPointerException if source or destination is null
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @see #copyFile(File, File, boolean)
	 */
	public static void copyFileToDirectory(final File srcFile, final File destDir) throws IOException {
		copyFileToDirectory(srcFile, destDir, true);
	}

	/**
	 * Copies a file to a directory optionally preserving the file date.
	 * <p>
	 * This method copies the contents of the specified source file to a file of the same name in the specified
	 * destination directory. The destination directory is created if it does not exist. If the destination file exists,
	 * then this method will overwrite it.
	 * <p>
	 * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
	 * {@code true} tries to preserve the file's last modified date/times using {@link File#setLastModified(long)},
	 * however it is not guaranteed that the operation will succeed. If the modification operation fails, no indication
	 * is provided.
	 *
	 * @param srcFile          an existing file to copy, must not be {@code null}
	 * @param destDir          the directory to place the copy in, must not be {@code null}
	 * @param preserveFileDate true if the file date of the copy should be the same as the original
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @throws IOException          if the output file length is not the same as the input file length after the copy
	 *                              completes
	 * @see #copyFile(File, File, boolean)
	 * @since 1.3
	 */
	public static void copyFileToDirectory(final File srcFile, final File destDir, final boolean preserveFileDate)
		throws IOException {
		if (destDir == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (destDir.exists() && destDir.isDirectory() == false) {
			throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
		}
		final File destFile = new File(destDir, srcFile.getName());
		copyFile(srcFile, destFile, preserveFileDate);
	}

	/**
	 * Copies a file to a new location.
	 * <p>
	 * This method copies the contents of the specified source file to the specified destination file. The directory
	 * holding the destination file is created if it does not exist. If the destination file exists, then this method
	 * will overwrite it.
	 * <p>
	 * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
	 * {@code true} tries to preserve the file's last modified date/times using {@link File#setLastModified(long)},
	 * however it is not guaranteed that the operation will succeed. If the modification operation fails, no indication
	 * is provided.
	 *
	 * @param srcFile          an existing file to copy, must not be {@code null}
	 * @param destFile         the new file, must not be {@code null}
	 * @param preserveFileDate true if the file date of the copy should be the same as the original
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs during copying
	 * @throws IOException          if the output file length is not the same as the input file length after the copy
	 *                              completes
	 * @see #copyFileToDirectory(File, File, boolean)
	 * @see #doCopyFile(File, File, boolean)
	 */
	public static void copyFile(final File srcFile, final File destFile,
		final boolean preserveFileDate) throws IOException {
		checkFileRequirements(srcFile, destFile);
		if (srcFile.isDirectory()) {
			throw new IOException("Source '" + srcFile + "' exists but is a directory");
		}
		if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
			throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
		}
		final File parentFile = destFile.getParentFile();
		if (parentFile != null) {
			if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
				throw new IOException("Destination '" + parentFile + "' directory cannot be created");
			}
		}
		if (destFile.exists() && destFile.canWrite() == false) {
			throw new IOException("Destination '" + destFile + "' exists but is read-only");
		}
		doCopyFile(srcFile, destFile, preserveFileDate);
	}

	/**
	 * checks requirements for file copy
	 *
	 * @param src  the source file
	 * @param dest the destination
	 * @throws FileNotFoundException if the destination does not exist
	 */
	private static void checkFileRequirements(final File src, final File dest) throws FileNotFoundException {
		if (src == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (dest == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (!src.exists()) {
			throw new FileNotFoundException("Source '" + src + "' does not exist");
		}
	}

	/**
	 * Internal copy file method. This caches the original file length, and throws an IOException if the output file
	 * length is different from the current input file length. So it may fail if the file changes size. It may also fail
	 * with "IllegalArgumentException: Negative size" if the input file is truncated part way through copying the data
	 * and the new file size is less than the current position.
	 *
	 * @param srcFile          the validated source file, must not be {@code null}
	 * @param destFile         the validated destination file, must not be {@code null}
	 * @param preserveFileDate whether to preserve the file date
	 * @throws IOException              if an error occurs
	 * @throws IOException              if the output file length is not the same as the input file length after the
	 *                                  copy completes
	 * @throws IllegalArgumentException "Negative size" if the file is truncated so that the size is less than the
	 *                                  position
	 */
	private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate)
		throws IOException {
		if (destFile.exists() && destFile.isDirectory()) {
			throw new IOException("Destination '" + destFile + "' exists but is a directory");
		}

		try (FileInputStream fis = new FileInputStream(srcFile);
			 FileChannel input = fis.getChannel();
			 FileOutputStream fos = new FileOutputStream(destFile);
			 FileChannel output = fos.getChannel()) {
			final long size = input.size(); // TODO See IO-386
			long pos = 0;
			long count = 0;
			while (pos < size) {
				final long remain = size - pos;
				count = remain > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : remain;
				final long bytesCopied = output.transferFrom(input, pos, count);
				if (bytesCopied == 0) { // IO-385 - can happen if file is truncated after caching the size
					break; // ensure we don't loop forever
				}
				pos += bytesCopied;
			}
		}

		final long srcLen = srcFile.length(); // TODO See IO-386
		final long dstLen = destFile.length(); // TODO See IO-386
		if (srcLen != dstLen) {
			throw new IOException("Failed to copy full contents from '" +
				srcFile + "' to '" + destFile + "' Expected length: " + srcLen + " Actual: " + dstLen);
		}
		if (preserveFileDate) {
			destFile.setLastModified(srcFile.lastModified());
		}
	}

	/**
	 * 在临时目录创建临时目录，命名为${毫秒级时间戳}-${同一毫秒内的随机数}.
	 */
	public static Path createTempDir() throws IOException {
		return Files.createTempDirectory(System.currentTimeMillis() + "-");
	}

	/**
	 * 在临时目录创建临时文件，命名为tmp-${random.nextLong()}.tmp
	 */
	public static Path createTempFile() throws IOException {
		return Files.createTempFile("tmp-", ".tmp");
	}

	//-----------------------------------------------------------------------

	/**
	 * 在临时目录创建临时文件，命名为${prefix}${random.nextLong()}${suffix}
	 */
	public static Path createTempFile(String prefix, String suffix) throws IOException {
		return Files.createTempFile(prefix, suffix);
	}

	/**
	 * Deletes a directory recursively.
	 *
	 * @param directory directory to delete
	 * @throws IOException in case deletion is unsuccessful
	 */
	public static void deleteDirectory(final File directory) throws IOException {
		if (!directory.exists()) {
			return;
		}

		cleanDirectory(directory);

		if (!directory.delete()) {
			final String message =
				"Unable to delete directory " + directory + ".";
			throw new IOException(message);
		}
	}

	/**
	 * 删除文件. 如果文件不存在或者是目录，则不做修改
	 */
	public static void deleteFile(Path path) throws IOException {
		Validate.isTrue(isFileExists(path), "%s is not exist or not a file", path);
		Files.delete(path);
	}

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
	 * Deletes a file, never throwing an exception. If file is a directory, delete it and all sub-directories.
	 * <p>
	 * The difference between File.delete() and this method are:
	 * <ul>
	 * <li>A directory to be deleted does not have to be empty.</li>
	 * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
	 * </ul>
	 *
	 * @param file file or directory to delete, can be {@code null}
	 * @return {@code true} if the file or directory was deleted, otherwise {@code false}
	 * @since 1.4
	 */
	public static boolean deleteQuietly(final File file) {
		if (file == null) {
			return false;
		}
		try {
			if (file.isDirectory()) {
				cleanDirectory(file);
			}
		} catch (final Exception ignored) {
		}

		try {
			return file.delete();
		} catch (final Exception ignored) {
			return false;
		}
	}

	/**
	 * Deletes a file. If file is a directory, delete it and all sub-directories.
	 * <p>
	 * The difference between File.delete() and this method are:
	 * <ul>
	 * <li>A directory to be deleted does not have to be empty.</li>
	 * <li>You get exceptions when a file or directory cannot be deleted.
	 * (java.io.File methods returns a boolean)</li>
	 * </ul>
	 *
	 * @param file file or directory to delete, must not be {@code null}
	 * @throws NullPointerException  if the directory is {@code null}
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException           in case deletion is unsuccessful
	 */
	public static void forceDelete(final File file) throws IOException {
		if (file.isDirectory()) {
			deleteDirectory(file);
		} else {
			final boolean filePresent = file.exists();
			if (!file.delete()) {
				if (!filePresent) {
					throw new FileNotFoundException("File does not exist: " + file);
				}
				final String message =
					"Unable to delete file: " + file;
				throw new IOException(message);
			}
		}
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

	/**
	 * 获取文件名的扩展名部分(不包含.)
	 */
	public static String getFileExtension(File file) {
		return com.google.common.io.Files.getFileExtension(file.getName());
	}

	/**
	 * 获取文件名的扩展名部分(不包含.)
	 */
	public static String getFileExtension(String fullName) {
		return com.google.common.io.Files.getFileExtension(fullName);
	}

	/**
	 * 获取文件名(不包含路径)
	 */
	public static String getFileName(String fullName) {
		Validate.notEmpty(fullName);
		int last = fullName.lastIndexOf(File.separatorChar);
		return fullName.substring(last + 1);
	}

	/**
	 * 获得参数clazz所在的Jar文件的绝对路径
	 */
	public static String getJarPath(Class<?> clazz) {
		return clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
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
	 * 判断目录是否存在
	 */
	public static boolean isDirectoryExists(String dirPath) {
		return isDirectoryExists(getPath(dirPath));
	}

	/**
	 * 判断目录是否存在
	 */
	public static boolean isDirectoryExists(Path dirPath) {
		return dirPath != null && Files.exists(dirPath) && Files.isDirectory(dirPath);
	}

	public static Path getPath(String filePath) {
		return Paths.get(filePath);
	}

	/**
	 * 判断目录是否存在
	 */
	public static boolean isDirectoryExists(File dir) {
		return isDirectoryExists(dir.toPath());
	}

	/**
	 * 判断文件是否存在
	 */
	public static boolean isFileExists(File file) {
		return isFileExists(file.toPath());
	}

	// 获取文件信息
	// -------------------------------------------------------------------------------------------------

	/**
	 * 判断文件是否存在
	 */
	public static boolean isFileExists(String fileName) {
		return isFileExists(getPath(fileName));
	}

	/**
	 * 前序递归列出所有文件, 包含文件与目录，及根目录本身. 前序即先列出父目录，在列出子目录. 如要后序遍历, 直接使用Files.fileTreeTraverser()
	 */
	public static List<File> listAll(File rootDir) {
		Iterable<File> iterable = com.google.common.io.Files.fileTraverser()
			.depthFirstPreOrder(rootDir);
		ArrayList<File> list = Lists.newArrayList(iterable);
		return list.stream().filter(item -> item.exists()).collect(Collectors.toList());
	}

	/**
	 * 前序递归列出所有文件, 只包含文件.
	 */
	public static List<File> listFile(File rootDir) {
		Iterable<File> iterable = com.google.common.io.Files.fileTraverser()
			.depthFirstPreOrder(rootDir);
		ArrayList<File> list = Lists.newArrayList(iterable);
		return list.stream().filter(com.google.common.io.Files.isFile())
			.collect(Collectors.toList());
	}

	/**
	 * 前序递归列出所有文件, 列出后缀名匹配的文件. （后缀名不包含.）
	 */
	public static List<File> listFileWithExtension(final File rootDir,
		final String extension) {
		Iterable<File> iterable = com.google.common.io.Files.fileTraverser()
			.depthFirstPreOrder(rootDir);
		ArrayList<File> list = Lists.newArrayList(iterable);
		return list.stream().filter(new FileExtensionFilter(extension))
			.collect(Collectors.toList());
	}

	// listFile
	// -------------------------------------------------------------------------------------------------

	/**
	 * 前序递归列出所有文件, 列出文件名匹配正则表达式的文件 如 ("/a/b/hello.txt", "he.*\.txt") 将被返回
	 */
	public static List<File> listFileWithRegexFileName(final File rootDir,
		final String regexFileNamePattern) {
		Iterable<File> iterable = com.google.common.io.Files.fileTraverser()
			.depthFirstPreOrder(rootDir);
		ArrayList<File> list = Lists.newArrayList(iterable);
		return list.stream().filter(new RegexFileNameFilter(regexFileNamePattern))
			.collect(Collectors.toList());
	}

	/**
	 * 前序递归列出所有文件, 列出文件名匹配通配符的文件 如 ("/a/b/hello.txt", "he*") 将被返回
	 */
	public static List<File> listFileWithWildcardFileName(final File rootDir,
		final String fileNamePattern) {
		Iterable<File> iterable = com.google.common.io.Files.fileTraverser()
			.depthFirstPreOrder(rootDir);
		ArrayList<File> list = Lists.newArrayList(iterable);
		return list.stream().filter(new WildcardFileNameFilter(fileNamePattern))
			.collect(Collectors.toList());
	}

	/**
	 * 确保目录存在, 如不存在则创建
	 */
	public static void makesureDirExists(String dirPath) throws IOException {
		makesureDirExists(getPath(dirPath));
	}

	/**
	 * 确保目录存在, 如不存在则创建.
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

	// move
	// -------------------------------------------------------------------------------------------------

	/**
	 * Moves a directory.
	 * <p>
	 * When the destination directory is on another file system, do a "copy and delete".
	 *
	 * @param srcDir  the directory to be moved
	 * @param destDir the destination directory
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if the destination directory exists
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs moving the file
	 * @since 1.4
	 */
	public static void moveDirectory(final File srcDir, final File destDir) throws IOException {
		if (srcDir == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (destDir == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (!srcDir.exists()) {
			throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
		}
		if (!srcDir.isDirectory()) {
			throw new IOException("Source '" + srcDir + "' is not a directory");
		}
		if (destDir.exists()) {
			throw new IOException("Destination '" + destDir + "' already exists");
		}
		final boolean rename = srcDir.renameTo(destDir);
		if (!rename) {
			if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath() + File.separator)) {
				throw new IOException("Cannot move directory: " + srcDir + " to a subdirectory of itself: " + destDir);
			}
			copyDirectory(srcDir, destDir);
			deleteDirectory(srcDir);
			if (srcDir.exists()) {
				throw new IOException("Failed to delete original directory '" + srcDir +
					"' after copy to '" + destDir + "'");
			}
		}
	}

	/**
	 * Moves a directory to another directory.
	 *
	 * @param src           the file to be moved
	 * @param destDir       the destination file
	 * @param createDestDir If {@code true} create the destination directory, otherwise if {@code false} throw an
	 *                      IOException
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if the directory exists in the destination directory
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs moving the file
	 * @since 1.4
	 */
	public static void moveDirectoryToDirectory(final File src, final File destDir, final boolean createDestDir)
		throws IOException {
		if (src == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (destDir == null) {
			throw new NullPointerException("Destination directory must not be null");
		}
		if (!destDir.exists() && createDestDir) {
			destDir.mkdirs();
		}
		if (!destDir.exists()) {
			throw new FileNotFoundException("Destination directory '" + destDir +
				"' does not exist [createDestDir=" + createDestDir + "]");
		}
		if (!destDir.isDirectory()) {
			throw new IOException("Destination '" + destDir + "' is not a directory");
		}
		moveDirectory(src, new File(destDir, src.getName()));
	}

	/**
	 * Moves a file.
	 * <p>
	 * When the destination file is on another file system, do a "copy and delete".
	 *
	 * @param srcFile  the file to be moved
	 * @param destFile the destination file
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if the destination file exists
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs moving the file
	 * @since 1.4
	 */
	public static void moveFile(final File srcFile, final File destFile) throws IOException {
		if (srcFile == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (destFile == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (!srcFile.exists()) {
			throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
		}
		if (srcFile.isDirectory()) {
			throw new IOException("Source '" + srcFile + "' is a directory");
		}
		if (destFile.exists()) {
			throw new IOException("Destination '" + destFile + "' already exists");
		}
		if (destFile.isDirectory()) {
			throw new IOException("Destination '" + destFile + "' is a directory");
		}
		final boolean rename = srcFile.renameTo(destFile);
		if (!rename) {
			copyFile(srcFile, destFile);
			if (!srcFile.delete()) {
				FileExtUtils.deleteQuietly(destFile);
				throw new IOException("Failed to delete original file '" + srcFile +
					"' after copy to '" + destFile + "'");
			}
		}
	}

	/**
	 * Moves a file to a directory.
	 *
	 * @param srcFile       the file to be moved
	 * @param destDir       the destination file
	 * @param createDestDir If {@code true} create the destination directory, otherwise if {@code false} throw an
	 *                      IOException
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if the destination file exists
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs moving the file
	 * @since 1.4
	 */
	public static void moveFileToDirectory(final File srcFile, final File destDir, final boolean createDestDir)
		throws IOException {
		if (srcFile == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (destDir == null) {
			throw new NullPointerException("Destination directory must not be null");
		}
		if (!destDir.exists() && createDestDir) {
			destDir.mkdirs();
		}
		if (!destDir.exists()) {
			throw new FileNotFoundException("Destination directory '" + destDir +
				"' does not exist [createDestDir=" + createDestDir + "]");
		}
		if (!destDir.isDirectory()) {
			throw new IOException("Destination '" + destDir + "' is not a directory");
		}
		moveFile(srcFile, new File(destDir, srcFile.getName()));
	}

	/**
	 * Moves a file or directory to the destination directory.
	 * <p>
	 * When the destination is on another file system, do a "copy and delete".
	 *
	 * @param src           the file or directory to be moved
	 * @param destDir       the destination directory
	 * @param createDestDir If {@code true} create the destination directory, otherwise if {@code false} throw an
	 *                      IOException
	 * @throws NullPointerException if source or destination is {@code null}
	 * @throws IOException          if the directory or file exists in the destination directory
	 * @throws IOException          if source or destination is invalid
	 * @throws IOException          if an IO error occurs moving the file
	 * @since 1.4
	 */
	public static void moveToDirectory(final File src, final File destDir, final boolean createDestDir)
		throws IOException {
		if (src == null) {
			throw new NullPointerException("Source must not be null");
		}
		if (destDir == null) {
			throw new NullPointerException("Destination must not be null");
		}
		if (!src.exists()) {
			throw new FileNotFoundException("Source '" + src + "' does not exist");
		}
		if (src.isDirectory()) {
			moveDirectoryToDirectory(src, destDir, createDestDir);
		} else {
			moveFileToDirectory(src, destDir, createDestDir);
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

	public static final List<String> readAllLines(final File file) throws IOException {
		return Files.readAllLines(file.toPath());
	}

	public static final String readString(final File file) throws IOException {
		List<String> lines = Files.readAllLines(file.toPath());
		return StringExtUtils.join(lines, "\n");
	}

	/**
	 * 创建文件或更新时间戳.
	 */
	public static void touch(String filePath) throws IOException {
		touch(new File(filePath));
	}

	/**
	 * 创建文件或更新时间戳.
	 */
	public static void touch(File file) throws IOException {
		com.google.common.io.Files.touch(file);
	}

	public static final void write(final File file, final byte[] content) throws IOException {
		com.google.common.io.Files.write(content, file);
	}

	public static final void write(final File file, final String content) throws IOException {
		com.google.common.io.Files.write(content.getBytes(), file);
	}

	/**
	 * Internal copy directory method.
	 *
	 * @param srcDir           the validated source directory, must not be {@code null}
	 * @param destDir          the validated destination directory, must not be {@code null}
	 * @param filter           the filter to apply, null means copy all directories and files
	 * @param preserveFileDate whether to preserve the file date
	 * @param exclusionList    List of files and directories to exclude from the copy, may be null
	 * @throws IOException if an error occurs
	 * @since 1.1
	 */
	private static void doCopyDirectory(final File srcDir, final File destDir, final FileFilter filter,
		final boolean preserveFileDate, final List<String> exclusionList)
		throws IOException {
		// recurse
		final File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
		if (srcFiles == null) {  // null if abstract pathname does not denote a directory, or if an I/O error occurs
			throw new IOException("Failed to list contents of " + srcDir);
		}
		if (destDir.exists()) {
			if (destDir.isDirectory() == false) {
				throw new IOException("Destination '" + destDir + "' exists but is not a directory");
			}
		} else {
			if (!destDir.mkdirs() && !destDir.isDirectory()) {
				throw new IOException("Destination '" + destDir + "' directory cannot be created");
			}
		}
		if (destDir.canWrite() == false) {
			throw new IOException("Destination '" + destDir + "' cannot be written to");
		}
		for (final File srcFile : srcFiles) {
			final File dstFile = new File(destDir, srcFile.getName());
			if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
				if (srcFile.isDirectory()) {
					doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList);
				} else {
					doCopyFile(srcFile, dstFile, preserveFileDate);
				}
			}
		}

		// Do this last, as the above has probably affected directory metadata
		if (preserveFileDate) {
			destDir.setLastModified(srcDir.lastModified());
		}
	}

	/**
	 * Lists files in a directory, asserting that the supplied directory satisfies exists and is a directory
	 *
	 * @param directory The directory to list
	 * @return The files in the directory, never null.
	 * @throws IOException if an I/O error occurs
	 */
	private static File[] verifiedListFiles(final File directory) throws IOException {
		if (!directory.exists()) {
			final String message = directory + " does not exist";
			throw new IllegalArgumentException(message);
		}

		if (!directory.isDirectory()) {
			final String message = directory + " is not a directory";
			throw new IllegalArgumentException(message);
		}

		final File[] files = directory.listFiles();
		if (files == null) {  // null if security restricted
			throw new IOException("Failed to list contents of " + directory);
		}
		return files;
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
			return file.isFile() && extension.equals(FileExtUtils.getFileExtension(file));
		}

	}

}

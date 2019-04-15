package com.obatis.core.compile;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.tools.*;

/**
 * Java 编译工厂类，可以直接用来编译字符串形式的Java源代码
 * @author HuangLongPu
 */
public class JavaCompilerFactory {

	/**
	 * 编译 Java 文件
	 * @param packageName  包名
	 * @param fileName     Java文件名
	 * @param javaSource   Java源代码
	 * @return
	 * @throws URISyntaxException
	 * @throws ClassNotFoundException
	 */
	public static Class<?> compiler(String packageName, String fileName, String javaSource) throws URISyntaxException, ClassNotFoundException {

		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
		ClassJavaFileManager classJavaFileManager = new ClassJavaFileManager(standardJavaFileManager);
		HandleJavaFileObject handleJavaFileObject = new HandleJavaFileObject(new URI(fileName + ".java"), JavaFileObject.Kind.SOURCE, javaSource);

		JavaCompiler.CompilationTask task = javaCompiler.getTask(null, classJavaFileManager, null, null, null, Arrays.asList(handleJavaFileObject));
		if (task.call()) {
			CreateJavaClassFile javaFileObject = classJavaFileManager.getClassJavaFileObject();
			ClassLoader classLoader = new MemoryJavaClassLoader(javaFileObject);
			return classLoader.loadClass(packageName + "." + fileName);
		}
		return null;
	}

	/**
	 * 自定义fileManager
	 * @author HuangLongPu
	 */
	static class ClassJavaFileManager extends ForwardingJavaFileManager {

		private CreateJavaClassFile classJavaFileObject;

		public ClassJavaFileManager(JavaFileManager fileManager) {
			super(fileManager);
		}

		public CreateJavaClassFile getClassJavaFileObject() {
			return classJavaFileObject;
		}

		@Override
		public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject fileObject) {
			return (classJavaFileObject = new CreateJavaClassFile(className, kind));
		}
	}

	/**
	 * 生成 class 文件，放置于缓存中
	 * @author HuangLongPu
	 */
	static class CreateJavaClassFile extends SimpleJavaFileObject {

		private ByteArrayOutputStream outputStream;

		public CreateJavaClassFile(String className, Kind kind) {
			super(URI.create(className + kind.extension), kind);
			this.outputStream = new ByteArrayOutputStream();
		}

		@Override
		public OutputStream openOutputStream() {
			return this.outputStream;
		}

		public byte[] getBytes() {
			return this.outputStream.toByteArray();
		}
	}

	/**
	 * 存储源文件
	 * @author HuangLongPu
	 */
	static class HandleJavaFileObject extends SimpleJavaFileObject {

		private String content;

		public HandleJavaFileObject(URI uri, Kind kind, String content) {
			super(uri, kind);
			this.content = content;
		}

		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return this.content;
		}
	}

	/**
	 * 定义内存加载器
	 * @author HuangLongPu
	 */
	static class MemoryJavaClassLoader extends ClassLoader {
		private CreateJavaClassFile createJavaClassFile;

		public MemoryJavaClassLoader(CreateJavaClassFile createJavaClassFile) {
			this.createJavaClassFile = createJavaClassFile;
		}

		@Override
		protected Class<?> findClass(String name) {
			byte[] bytes = this.createJavaClassFile.getBytes();
			return defineClass(name, bytes, 0, bytes.length);
		}
	}
}

package com.sbatis.core.compile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CompileFactory {

	public static Class<?> compile(String packageName, String fileName, String javaSource) throws URISyntaxException, ClassNotFoundException {

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
		ClassJavaFileManager classJavaFileManager = new ClassJavaFileManager(standardFileManager);
		StringObject source = new StringObject(new URI(fileName + ".java"), JavaFileObject.Kind.SOURCE, javaSource);

		JavaCompiler.CompilationTask task = compiler.getTask(null, classJavaFileManager, null, null, null, Arrays.asList(source));
		if (task.call()) {
			ClassJavaFileObject javaFileObject = classJavaFileManager.getClassJavaFileObject();
			ClassLoader classLoader = new MemoryClassLoader(javaFileObject);
			return classLoader.loadClass(packageName + "." + fileName);
		}
		return null;
	}

	/**
	 * 自定义fileManager
	 */
	@SuppressWarnings("rawtypes")
	static class ClassJavaFileManager extends ForwardingJavaFileManager {

		private ClassJavaFileObject classJavaFileObject;

		@SuppressWarnings("unchecked")
		public ClassJavaFileManager(JavaFileManager fileManager) {
			super(fileManager);
		}

		public ClassJavaFileObject getClassJavaFileObject() {
			return classJavaFileObject;
		}

		// 这个方法一定要自定义
		@Override
		public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling)
				throws IOException {
			return (classJavaFileObject = new ClassJavaFileObject(className, kind));
		}
	}

	/**
	 * class文件（不需要存到文件中）
	 */
	static class ClassJavaFileObject extends SimpleJavaFileObject {

		ByteArrayOutputStream outputStream;

		public ClassJavaFileObject(String className, Kind kind) {
			super(URI.create(className + kind.extension), kind);
			this.outputStream = new ByteArrayOutputStream();
		}

		// 这个也要实现
		@Override
		public OutputStream openOutputStream() throws IOException {
			return this.outputStream;
		}

		public byte[] getBytes() {
			return this.outputStream.toByteArray();
		}
	}

	/**
	 * 存储源文件
	 */
	static class StringObject extends SimpleJavaFileObject {

		private String content;

		public StringObject(URI uri, Kind kind, String content) {
			super(uri, kind);
			this.content = content;
		}

		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
			return this.content;
		}
	}

	// 自定义classloader
	static class MemoryClassLoader extends ClassLoader {
		private ClassJavaFileObject stringObject;

		public MemoryClassLoader(ClassJavaFileObject stringObject) {
			this.stringObject = stringObject;
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			byte[] bytes = this.stringObject.getBytes();
			return defineClass(name, bytes, 0, bytes.length);
		}
	}
}

package com.obatis.core.logger;

public class LogPrintFactory {

	private LogPrintFactory() {}
	
	public static LogPrinter getLogPrint(Class<?> clz) {
		return new LogPrinter(clz.getCanonicalName());
	}
	public static LogPrinter getLogPrint(String className) {
		return new LogPrinter(className);
	}
	
}

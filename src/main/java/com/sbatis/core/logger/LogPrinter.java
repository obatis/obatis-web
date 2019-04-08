package com.sbatis.core.logger;

import org.springframework.beans.factory.annotation.Value;

public class LogPrinter {

	@Value("${system.logdebug}")
	private Boolean debug;

	private String classCalName;

	public LogPrinter(String canonicalName) {
		this.classCalName = canonicalName;
	}

	public void print(Object object) {
		if (null==debug || debug) {
			System.out.print("[" + this.classCalName + "]" + object);
		}
	}
}

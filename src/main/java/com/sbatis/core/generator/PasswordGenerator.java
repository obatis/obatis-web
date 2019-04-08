package com.sbatis.core.generator;

import com.sbatis.convert.date.DateCommonConvert;
import com.sbatis.encrypt.base64.Base64EncoderTool;
import com.sbatis.encrypt.md5.Md5EncryptTool;

import java.util.Date;

/**
 * md5密码生成库，建议sha值是个动态值，或者是不对用户展示的值，提高暴力破解的难度
 * @author HuangLongPu
 */
public class PasswordGenerator {
	
	private PasswordGenerator() {}
	
	private static String PREFIX = "00R9_";
	private static String SUFFIX = "108+&C";

	public static final String getPassword(String shaName, String userName, String password) {
		String prefixMd5Encrypt = Md5EncryptTool.encrypt16(PREFIX + shaName + userName);
		String suffixMd5Encrypt = Md5EncryptTool.encrypt16( shaName + userName + SUFFIX);
		String pwdMd5Encrypt = Md5EncryptTool.encrypt32(PREFIX + password + SUFFIX);
		String minMd5Encrypt = Md5EncryptTool.encrypt32(prefixMd5Encrypt + pwdMd5Encrypt + suffixMd5Encrypt);
		return getBinaryBase(prefixMd5Encrypt + pwdMd5Encrypt) + getBinaryBase(minMd5Encrypt);
	}

	private static String getBinaryBase(String str) {
		char[] strChar = str.toCharArray();
		String temp = "";

		for (int i = 0; i < strChar.length; ++i) {
			temp = temp + Integer.toBinaryString(strChar[i]) + " ";
		}

		return new String(Base64EncoderTool.encode((Md5EncryptTool.encrypt32(temp) + Md5EncryptTool.encrypt16(temp.hashCode() + "")).getBytes())).replace("M", "a").replace("=", "").toLowerCase().replace("zz", "m").replace("tq", "a").replace("yy", "b").replace("nm", "a");
	}

	public static final boolean verifyPwd(String sha, String userName, String inputPassword, String password) {
		return getPassword(sha, userName, inputPassword).equals(password);
	}

}
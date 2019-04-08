package com.sbatis.core.generator;

import com.sbatis.convert.date.DateCommonConvert;
import com.sbatis.encrypt.base64.Base64EncoderTool;
import com.sbatis.encrypt.md5.Md5EncryptTool;

import java.util.Date;

public class PasswordGenerator {
	
	private PasswordGenerator() {}
	
	private static String PREFIX = "0000_";
	private static String SUFFIX = "99+&123Q_WE";

	public static final String getPassword(String sha, String userName, String password) {
		StringBuffer prePWD = new StringBuffer(Md5EncryptTool.encrypt16(PREFIX));
		StringBuffer sufPWD = new StringBuffer(Md5EncryptTool.encrypt32(SUFFIX));

		StringBuffer lastLoginTimePWD = new StringBuffer(Md5EncryptTool.encrypt16(sha));
		StringBuffer userNamePWD = new StringBuffer(Md5EncryptTool.encrypt32(userName));
		StringBuffer passwordPWD = new StringBuffer(Md5EncryptTool.encrypt16(password));

		String prefixPWD = getPrefixPWD(prePWD, lastLoginTimePWD, userNamePWD);
		String centerPWD = getCenterPWD(prePWD, sufPWD, lastLoginTimePWD, userNamePWD, passwordPWD);
		String suffixPWD = getSuffixPWD(lastLoginTimePWD, passwordPWD, sufPWD);
		return prefixPWD + centerPWD + suffixPWD
				+ getBinaryBase(new StringBuilder(sha).append(userName)
						.append(password).toString());
	}

	private static String getPrefixPWD(StringBuffer prePWD, StringBuffer lastLoginTimePWD, StringBuffer userNamePWD) {
		return Md5EncryptTool.encrypt32(prePWD.append(lastLoginTimePWD).append(userNamePWD).toString().toUpperCase());
	}

	private static String getCenterPWD(StringBuffer prePWD, StringBuffer sufPWD, StringBuffer lastLoginTimePWD,
			StringBuffer userNamePWD, StringBuffer passwordPWD) {
		return Md5EncryptTool.encrypt16(Md5EncryptTool.encrypt32(
				prePWD.append(lastLoginTimePWD).append(userNamePWD).append(passwordPWD).append(sufPWD).toString()));
	}

	private static String getSuffixPWD(StringBuffer lastLoginTimePWD, StringBuffer passwordPWD, StringBuffer sufPWD) {
		return Md5EncryptTool.encrypt32(Md5EncryptTool.encrypt16(lastLoginTimePWD.append(passwordPWD).append(sufPWD).toString()));
	}

	private static String getBinaryBase(String str) {
		char[] strChar = str.toCharArray();
		String result = "";

		for (int i = 0; i < strChar.length; ++i) {
			result = result + Integer.toBinaryString(strChar[i]) + " ";
		}

		return new String(Base64EncoderTool.encode((Md5EncryptTool.encrypt16(result) + result.hashCode()).getBytes())).replace("M", "019a").replace("=", "").replace("j", "8").toLowerCase();
	}

	public static final boolean verifyPwd(String sha, String userName, String inputPassword, String password) {
		return getPassword(sha, userName, inputPassword).equals(password);
	}

}
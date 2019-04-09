package com.sbatis.core.generator;

import com.sbatis.encrypt.base64.Base64EncoderTool;
import com.sbatis.encrypt.md5.Md5EncryptTool;

/**
 * md5密码生成库，建议sha值是个变化值(比如上次登录时间)，或者是不对用户展示的值，提高暴力破解的难度。
 * 此工具主要提供密码的生成和校验，为提高用户密码强度和系统安全体系，建议每登录一次重新生成一次密码，这样哪怕之前的密码泄露也相对安全。
 * 若是下载源码后自行编译生成jar包后使用，建议修改预设的 PREFIX(前缀) 和 SUFFIX(后缀)值，防止密码猜测。
 * @author HuangLongPu
 */
public class PasswordGenerator {
	
	private PasswordGenerator() {}
	
	private static String PREFIX = "00R9_";
	private static String SUFFIX = "108+&C";

	/**
	 * 传入sha值、用户名和密码明文，得到密码加密串
	 * @author HuangLongPu
	 * @param shaName   sha值，自定义，建议sha值是个变化值(比如上次登录时间)，这样每登录(建议每登录一次修改一次密码)一次密码变化一次
	 * @param userName  用户名，也可以传入跟用户绑定的全局唯一属性值
	 * @param password  用户的明文密码
	 * @return
	 */
	public static final String getPassword(String shaName, String userName, String password) {
		String prefixMd5Encrypt = Md5EncryptTool.encrypt16(PREFIX + shaName + userName);
		String suffixMd5Encrypt = Md5EncryptTool.encrypt16( shaName + userName + SUFFIX);
		String pwdMd5Encrypt = Md5EncryptTool.encrypt32(PREFIX + password + SUFFIX);
		String minMd5Encrypt = Md5EncryptTool.encrypt32(prefixMd5Encrypt + pwdMd5Encrypt + suffixMd5Encrypt);
		return handleBinaryBase(prefixMd5Encrypt + pwdMd5Encrypt) + handleBinaryBase(minMd5Encrypt);
	}

	/**
	 * 对 md5 串码进行再一次处理，加入 base64 机制和替换规则，提高暴力破解密码的难度
	 * @author HuangLongPu
	 * @param md5Encrypt
	 * @return
	 */
	private static String handleBinaryBase(String md5Encrypt) {
		char[] strChar = md5Encrypt.toCharArray();
		String temp = "";

		for (int i = 0; i < strChar.length; ++i) {
			temp = temp + Integer.toBinaryString(strChar[i]) + " ";
		}

		return new String(Base64EncoderTool.encode((Md5EncryptTool.encrypt32(temp) + Md5EncryptTool.encrypt16(temp.hashCode() + "")).getBytes())).replace("M", "a").replace("=", "").toLowerCase().replace("zz", "m").replace("tq", "a").replace("yy", "b").replace("nm", "a");
	}

	/**
	 * 密码比对，需传入明文(若前端进行加密传输，需解密)和之前加密后的密文，根据规则进行比对
	 * @param sha               自定义的sha值，建议sha值是个变化值(比如上次登录时间)
	 * @param userName          用户名，也可以传入跟用户绑定的全局唯一属性值
	 * @param inputPassword     用户提交的明文(若前端进行加密传输，需解密)
	 * @param password          根据规则生成的密文
	 * @return
	 */
	public static final boolean verifyPwd(String sha, String userName, String inputPassword, String password) {
		return getPassword(sha, userName, inputPassword).equals(password);
	}

}
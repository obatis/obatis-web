package com.sbatis.core.generator;

import com.sbatis.convert.date.DateCommonConvert;

import java.math.BigInteger;
import java.util.*;

/**
 * 获取28长度的纯数字编号，可用作ID、订单号、流水号等
 * @author HuangLongPu
 */
public class NumberGenerator {

	private NumberGenerator() {}

	private static final int maxLength = 11;

//	private static final Map<String, Integer> code = new HashMap<>();
//
//	static {
//		code.put("a", 1);
//		code.put("b", 2);
//		code.put("c", 3);
//		code.put("d", 4);
//		code.put("e", 5);
//		code.put("f", 6);
//		code.put("g", 7);
//		code.put("h", 8);
//		code.put("i", 9);
//		code.put("j", 10);
//		code.put("k", 11);
//		code.put("l", 12);
//		code.put("m", 13);
//		code.put("n", 14);
//		code.put("o", 15);
//		code.put("p", 16);
//		code.put("q", 17);
//		code.put("r", 18);
//		code.put("s", 19);
//		code.put("t", 20);
//		code.put("u", 21);
//		code.put("v", 22);
//		code.put("w", 23);
//		code.put("x", 24);
//		code.put("y", 25);
//		code.put("z", 26);
//	}

	/**
	 * 获取类型为BigInteger，位数28位
	 * @return
	 */
	public static final BigInteger getNumber() {
		return new BigInteger(DateCommonConvert.formatCurDateTimeMillis() + handleNumber());
	}

//	private static String handleNumber() {
//		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
//		int uuidSize = uuid.length();
//		for (int i = 0; i < uuidSize; i++) {
//			String key = uuid.charAt(i) + "";
//			if(code.containsKey(key)) {
//				uuid = uuid.replace(key, code.get(key) + "");
//			}
//		}
//
//		System.out.println(uuid);
////		return uuid.substring(0, maxLength);
//		return uuid;
//	}

	private static String handleNumber() {

		String hashCodeValue = getHashCode() + "";
		int lenth = hashCodeValue.length();
		StringBuffer s = new StringBuffer(hashCodeValue);
		if(lenth > maxLength) {
			s.deleteCharAt(maxLength);
			return s.toString();
		}
		int offset = maxLength - lenth;
		for (int i = 0; i < offset; i++) {
			s.insert(0, new Random().nextInt(10));
//			s.insert(0, 0);
		}
		
		return s.toString();
	}

	private static int getHashCode() {
		int hashCode = UUID.randomUUID().hashCode();
		if(hashCode < 0) {
			hashCode = 0 - hashCode;
		}
		return hashCode;
	}

//	public static Map<BigInteger, BigInteger> map = new HashMap<>();
//	public static void main(String[] args) {
//
//		for (int i =0; i < 100; i++) {
//			new TestNumber().start();
////			System.out.println(getHashCode());;
//		}
//
//		System.out.println(" exe ");
//
//		try {
//			Thread.sleep(15000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println(map.size());
//	}
//
//}
//
//class TestNumber extends Thread {
//	@Override
//	public void run() {
//		for (int i =0; i < 1000; i++) {
//			BigInteger number = NumberGenerator.getNumber();
////			if(NumberGenerator.map.containsKey(number)) {
////				System.out.println(number);
////			}
//			new TestAdd(number).start();
//		}
//	}
//}
//
//class TestAdd extends Thread {
//
//	private BigInteger number;
//
//	public TestAdd(BigInteger number) {
//		this.number = number;
//	}
//
//	@Override
//	public void run() {
//		synchronized (NumberGenerator.map) {
//			NumberGenerator.map.put(number, number);
//		}
//	}
}
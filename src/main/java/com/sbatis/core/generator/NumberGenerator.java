package com.sbatis.core.generator;

import com.sbatis.convert.date.DateCommonConvert;

import java.math.BigInteger;
import java.util.*;

/**
 * 获取28长度的纯数字编号，可用作ID、订单号、流水号等。
 * 默认生成28位的数字串，若是下载源码编译jar 包使用的，可自行修改位数。
 * 经过测试，目前在快速生成10万次没发现有重复，同时保留了测试方法(注释部分)，欢迎测试并提出改进意见。
 * 如果你有更好的建议，也可以在线留言(留言途径有github项目、sbatis项目主页)，我们会及时改进和调整，感谢你的支持 更多建议及意见，可以通过邮箱留言：huanglongpu@126.com。
 * 注：测试案例仅为业务实现，未考虑性能问题，测试时可考虑加入线程池或者队列效果会更好。
 * @author HuangLongPu
 */
public class NumberGenerator {

	private NumberGenerator() {}

	private static final int maxLength = 11;

	/**
	 * 获取类型为BigInteger，位数28位
	 * @author HuangLongPu
	 * @return
	 */
	public static final BigInteger getNumber() {
		return new BigInteger(DateCommonConvert.formatCurDateTimeMillis() + handleNumber());
	}

	/**
	 * 获取number后缀，根据uuid并且补全位数
	 * @author HuangLongPu
	 * @return
	 */
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

	/**
	 * 获取uuid值的hashcode
	 * @author HuangLongPu
	 * @return
	 */
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
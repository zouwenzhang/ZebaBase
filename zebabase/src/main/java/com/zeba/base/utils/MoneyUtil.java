package com.zeba.base.utils;

import java.math.BigDecimal;

//这个类不能实例化 

/** 商业计算 */
public class MoneyUtil {

	/**
	 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
	 */
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	private MoneyUtil() {
	}

	/**
	 * 比较a是否大于b,true-a大,false-b大或相等
	 * */
	public static boolean isABigB(String a, String b) {
		try{
		int r = new BigDecimal(a).compareTo(new BigDecimal(b));
		if (r == 1) {
			return true;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 比较a是否大于或等于b,true-a大,false-b大或相等
	 * */
	public static boolean isABigEqualB(String a, String b) {
		try{
		int r = new BigDecimal(a).compareTo(new BigDecimal(b));
		if (r >=0) {
			return true;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 比较a是否小于b,true-a小,false-b小或相等
	 * */
	public static boolean isALessB(String a, String b) {
		try{
		int r = new BigDecimal(a).compareTo(new BigDecimal(b));
		if (r == -1) {
			return true;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 比较a是否等于b,true-相等,false-不相等
	 * */
	public static boolean isAEqualB(String a, String b) {
		int r = new BigDecimal(a).compareTo(new BigDecimal(b));
		if (r == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 将0-1之间的小数转化为折扣，如0.95-9.5
	 * */
	public static String getZheKouString(String n) {
		String s = "";
		int ps = n.indexOf(".");
		if (ps >= 0) {
			n = n.substring(ps + 1);
		}
		boolean last = true;
		for (int i = n.length() - 1; i >= 0; i--) {
			if ('0' == n.charAt(i) && last) {
				n = n.substring(0, i);
			} else {
				break;
			}
		}
		if (n.length() == 0) {
			return s;
		}
		s = n.substring(0, 1);
		if (n.length() > 1) {
			s = s + "." + n.substring(1);
		}
		return s;
	}

	public static String getString(String s) {
		return new BigDecimal(s).toPlainString();
	}

	public static String getNoZeroNumber(float number) {
		return getNoZeroNumber(String.valueOf(number));
	}

	public static String getNoZeroNumber(double number) {
		return getNoZeroNumber(String.valueOf(number));
	}
	
	public static float getFloat(String number) {
		try{
			return Float.parseFloat(number);
		}catch(Exception e){
			e.printStackTrace();
			return 0f;
		}
	}
	public static String max(String n1,String n2){
		BigDecimal b1;
		try{
			b1=new BigDecimal(n1);
		}catch(Exception e){
			e.printStackTrace();
			b1=new BigDecimal("0");
		}
		BigDecimal b2;
		try{
			b2=new BigDecimal(n2);
		}catch(Exception e){
			e.printStackTrace();
			b2=new BigDecimal("0");
		}
		return b1.max(b2).toPlainString();
	}
	public static String getNoZeroNumber(String s){
		try {
			if(s==null||"".equals(s)){
				return "0";
			}
			BigDecimal decimal= new BigDecimal(s);
			if(decimal.compareTo(BigDecimal.ZERO)==0){
				return "0";
			}
			return decimal.stripTrailingZeros().toPlainString();
		}catch (Exception e){

		}
		return s;
	}
//	/**
//	 * 如果没有小数，则抹掉小数位
//	 * */
//	public static String getNoZeroNumber(String number) {
//		try {
//			if(number==null){
//				return "0";
//			}
//			int end = number.indexOf(".");
//			if (end <= 0) {
//				return number;
//			}
//			if (end == number.length() - 1) {
//				return number;
//			}
//			String st = number.substring(end + 1);
//			String string = number.substring(end + 1);
//			for (int i = st.length() - 1; i >= 0; i--) {
//				if (st.charAt(i) == '0') {
//					string = string.substring(0, string.length() - 1);
//				} else {
//					break;
//				}
//			}
//			String start = number.substring(0, number.indexOf("."));
//			if ("".equals(string)) {
//				return start;
//			}
//			return start + "." + string;
//		} catch (Exception e) {
//			return number;
//		}
//	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	public static String add(String v1, String v2) {
		if(v1==null){
			v1="0";
		}
		if(v2==null){
			v2="0";
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).toPlainString();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * v1-v2
	 * @return 两个参数的差
	 */
	public static String sub(String v1, String v2) {
		try{
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).toPlainString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "0";
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	public static String mul(String v1, String v2) {
		try {
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2);
			return b1.multiply(b2).toString();
		} catch (Exception e) {
			return "0";
		}
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * v1/v2 提供（相对）精确的除法运算。
	 * 
	 * @param //scale
	 *            //表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static String divNoScale(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2).toPlainString();
	}

	public static String div(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, 2, BigDecimal.ROUND_DOWN).toPlainString();
	}

	public static String div(String v1, String v2,int scale) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).toPlainString();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 将输入的数取n位小数
	 * */
	public static String toScale(String number,int n){
		if(number==null||"".equals(number)){
			return "0.0";
		}
		return new BigDecimal(number).setScale(n,BigDecimal.ROUND_DOWN).toPlainString();
	}

	public static String toLongString(String number){
		if(number==null||"".equals(number)){
			return "0";
		}
		return new BigDecimal(number).longValue()+"";
	}

	public static String toScaleHalfUp(String number,int n){
		if(number==null||number.length()==0){
			number="0";
		}
		try{
			return new BigDecimal(number).setScale(n,BigDecimal.ROUND_HALF_UP).toPlainString();
		}catch (Exception e){

		}
		return number;
	}
}

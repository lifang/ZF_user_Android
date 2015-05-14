package com.examlpe.zf_android.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.example.zf_android.Config;

import android.util.Base64;
import android.util.Log;

/***
 * 对字符串 加密，解析
 * 
 * @author Lijinpeng
 * 
 *         comdo
 */
public class StringUtil {

	public static String timeUtil(String time) {
		String t = time;
		System.out.println(time.length());
		if (time.length() > 16) {
			t = time.substring(0, 16);
		}
		// 2014-12-10
		t = t.split(" ")[0];
		t = t.substring(5);
		String T1 = t.split("-")[1];
		if (T1.equals("01")) {
			T1 = T1 + "st,";
		}else 
			if (T1.equals("02")) {
				T1 = T1 + "nd,";
			}
			else if (T1.equals("03")) {
				T1 = T1 + "rd,";
			} else {
				T1 = T1 + "th,";
			}
		String T2 = t.split("-")[0];
		if (T2.equals("01")) {
			T2 = "Jan";
		}
		if (T2.equals("04")) {
			T2 = "Apr";
		}
		if (T2.equals("02")) {
			T2 = "Feb";
		}
		if (T2.equals("03")) {
			T2 = "Mar";
		}
		if (T2.equals("05")) {
			T2 = "May";
		}
		if (T2.equals("06")) {
			T2 = "Jun";
		}
		if (T2.equals("07")) {
			T2 = "Jul";
		}

		if (T2.equals("08")) {
			T2 = "Aug";
		}
		if (T2.equals("09")) {
			T2 = "Sep";
		}
		if (T2.equals("10")) {
			T2 = "Oct";
		}
		if (T2.equals("11")) {
			T2 = "Nov";
		}
		if (T2.equals("12")) {
			T2 = "Dec";
		}
		t = T1 + T2;
		return t;
	}

	public static String mobileUtil(String time) {
		String t = time;
		System.out.println(time.length());
		if (time.length() == 11) {
			t = time.substring(0, 3) + "*****" + time.substring(7, 11);
		}

		return t;
	}

	/**
	 * MD5加密算法
	 * 
	 * @param plainText
	 * @return
	 */
	public static String Md5(String plainText) {
		String md5Password = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			md5Password = buf.toString();

			System.out.println("result: " + buf.toString());// 32位的加密
			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5Password;
	}

	// 去除所有空格
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	//判断是否为空
	public static boolean isNull(String s) {
		if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}
	/**
	 * email格式验证
	 * 
	 * @param string
	 * @return 验证通过返回true
	 */
	public static boolean checkEmail(String email) {
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}
	/**
	 * 判断邮编
	 * @param paramString
	 * @return
	 */
	public static boolean isZipNO(String zipString){
		String str = "^[1-9][0-9]{5}$";
		return Pattern.compile(str).matcher(zipString).matches();
	}
	/**
	 * 手机号验证
	 * 
	 * @param string
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {

		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}
	/**
	 * 密码加密
	 * 
	 * @param src
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptThreeDESECB(String src, String key)
			throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(src.getBytes());

		Log.e("code", android.util.Base64.encodeToString(b, Base64.DEFAULT)
				.replaceAll("\r", "").replaceAll("\n", ""));
		System.out.println("code```"
				+ android.util.Base64.encodeToString(b, Base64.DEFAULT)
				.replaceAll("\r", "").replaceAll("\n", ""));
		return android.util.Base64.encodeToString(b, Base64.DEFAULT)
				.replaceAll("\r", "").replaceAll("\n", "");
	}

	/***
	 * 参数加密
	 * 
	 * @param paramValues
	 * @param secret
	 * @return 加密sign
	 */
	public static String sign(Map<String, String> paramValues, String secret) {
		StringBuilder sign = new StringBuilder();
		try {
			byte[] sha1Digest = null;
			StringBuilder sb = new StringBuilder();
			List<String> paramNames = new ArrayList<String>(paramValues.size());
			paramNames.addAll(paramValues.keySet());
			Collections.sort(paramNames);
			sb.append(secret);
			for (String paramName : paramNames) {
				sb.append(paramName).append(paramValues.get(paramName));
			}
			sb.append(secret);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			sha1Digest = md.digest(sb.toString().getBytes("UTF-8"));
			for (int i = 0; i < sha1Digest.length; i++) {
				String hex = Integer.toHexString(sha1Digest[i] & 0xFF);
				if (hex.length() == 1) {
					sign.append("0");
				}
				sign.append(hex.toUpperCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("sign", sign.toString());
		return sign.toString();
	}

	public static String getMoneyString(int money){
		return String.format("%.2f", money/100f);

	}
	public static String getBigImage(String url){
		return MessageFormat.format(Config.POS_PIC_URL, url);
	}
	public static String getImage(String url){
		return MessageFormat.format(Config.FILE_URL, url);
	}
	public static String replaceNum(String num){
		if(num==null||"".equals(num)||num.length()<8){
			return num;
		}
		int length=num.length();
		String start=num.substring(0,length-8);
		String end=num.substring(length-4, length);
		
		return start+"****"+end;
	}
	public static String replaceName(String num){
		if(num==null||"".equals(num)||num.length()<2){
			return num;
		}
		int length=num.length();
		String start=num.substring(0,length-2);
		String end=num.substring(length-1, length);
		
		return start+"*"+end;
	}
}

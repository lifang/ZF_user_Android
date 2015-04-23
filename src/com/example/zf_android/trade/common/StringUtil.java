package com.example.zf_android.trade.common;

import java.text.MessageFormat;
import java.util.List;

import android.text.TextUtils;

import com.example.zf_android.Config;


/**
 * Created by Leo on 2015/3/5.
 */
public class StringUtil {
	

	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence cs) {
		return !StringUtil.isBlank(cs);
	}

	public static String join(List<String> strs, String split) {
		if (null == strs || strs.size() == 0 || isBlank(split))
			return "";
		if (strs.size() == 1)
			return strs.get(0);

		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str).append(split);
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String formatNull(String str){
		if(TextUtils.isEmpty(str))
			return "";
		return str;
	}
}

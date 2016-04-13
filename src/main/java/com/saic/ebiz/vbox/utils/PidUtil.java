package com.saic.ebiz.vbox.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math.util.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.saic.ebiz.vbox.exception.ParsePidException;

public class PidUtil {

	@SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(PidUtil.class);
	
	private static Properties PIDRULES = null;
	private static Pattern RULE_PATTERN = Pattern.compile("[BHDF]\\{\\d+,\\d+\\}");
	private static Pattern CALC_PATTERN = Pattern.compile("[/*+-]");

	static {
		try {
			PIDRULES = new Properties();
			InputStream inputStream = PidUtil.class
					.getResourceAsStream("/pid_rule.properties");
			PIDRULES.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, Object> parse(String pid, byte[] result) throws ParsePidException {
		//logger.debug("解析：{} {}", pid, StringUtil.toHexStringPadded(result));
		Map<String, Object> values = new HashMap<String, Object>();
		
		String rule = PIDRULES.getProperty(pid);
		
		// 按;分解规则
		if (rule != null) {
			for (String split : rule.split(";")) {
				String[] params = split.split("=");
				if (params.length > 1) {
					values.put(params[0].trim(), parseRule(params[1].trim(), result));
				}
			}
		} else {
			throw new ParsePidException("不支持的PID类型");
		}
		
		return values;
	}
	
	private static Object parseRule(String rule, byte[] result) throws ParsePidException {
		String rule0 = findFirstMatch(RULE_PATTERN, rule);
		
        //解析出处理类型，及处理的起止位
        String type = rule0.substring(0, 1);
        int pos = rule0.indexOf(",");
        
        Integer start = Integer.parseInt(rule0.substring(2, pos));
        Integer end = Integer.parseInt(rule0.substring(pos + 1, rule0.length() - 1));
        
	    if(result.length * 8 >= end){
	        Long value = bit2Long(result, start, end);//计算值
	        
	        if ("B".equals(type)) {//根据类型进行单独处理
	        	return value.toString();
	        } else if ("F".equals(type)) {
	        	return calcExpress(rule.replaceAll("[BHDF]\\{\\d+,\\d+\\}", value.toString())).floatValue();
	        } else if ("H".equals(type)) {//针对二进制转换十六进制后首位缺0的情况,例如:0A->A
	        	String strHexVal = Long.toHexString(value).toUpperCase();
	        	
	        	int iDeafultHexLen = (end - start)/4;//默认十六进制长度
	        	int iHexValLen = strHexVal.length();//实际十六进制长度
	        	if(strHexVal!=null && iHexValLen<iDeafultHexLen){//一个十六进制字符占4位二进制
	        		strHexVal = padHexString(strHexVal, iDeafultHexLen - iHexValLen);
	        	}
	        	
	        	return strHexVal;
	        } else if ("D".equals(type)) {
	        	return calcExpress(rule.replaceAll("[BHDF]\\{\\d+,\\d+\\}", value.toString())).intValue();
	        }
	    } else {
	    	throw new ParsePidException("解析PID规则出错");
	    }
        return null;
	}
	
	private static Long bit2Long(byte[] result, int start, int end) {
		int b_start = start / 8;
		int b_end = (end - 1) / 8;
		long l = 0;
		int count = b_end - b_start;
		for (int i = 0; i < result.length; i ++) {
			if (i > b_end) {
				break;
			}
			
			if (i < b_start) {
				continue;
			}
			
			if (i == b_start || i == b_end) {
				if (i == b_start) {
					l |= (long)(result[i]&(MathUtils.pow(2, (8 - (start % 8))) - 1)) << (count * 8);
				}
				
				if (i == b_end) {
					if (b_start == b_end) {
						l |= (long)(l&0xFF) << (count * 8);
					} else {
						l |= (long)(result[i]&0xFF) << (count * 8);
					}
					
					l >>= ((i + 1) * 8 - end);
				} 
			} else {
				l |= (long)(result[i]&0xFF) << (count * 8);
			}
			
			count --;
		}

		return l;
	}

	private static String findFirstMatch(Pattern pattern, String str) {
		Matcher m = pattern.matcher(str);
		
        while (m.find()) {
        	return m.group();
		}
        
        return null;
	}
	
	private static String padHexString(String str, int size) {
		StringBuilder builder = new StringBuilder();
		return builder.append(StringUtils.repeat('0', size)).append(str).toString();
	}
	/**
	 * 
	 * 功能描述: 计算表达式<br>
	 *
	 * @return  
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private static Float calcExpress(String express) {
		String[] numbers = express.split("[/*+-]");
		Matcher m = CALC_PATTERN.matcher(express);
		BigDecimal bgCur = new BigDecimal(numbers[0]);
		int i = 1;
		while(m.find()) {
			String op = m.group();
			if ("/".equals(op)) {
				bgCur = bgCur.divide(new BigDecimal(numbers[i ++]),10,BigDecimal.ROUND_HALF_UP);//针对除不尽的情况下四舍五入保留小数点后10位
			} else if ("*".equals(op)) {
				bgCur = bgCur.multiply(new BigDecimal(numbers[i ++]));
			} else if ("+".equals(op)) {
				bgCur = bgCur.add(new BigDecimal(numbers[i ++]));
			} else if ("-".equals(op)) {
				bgCur = bgCur.subtract(new BigDecimal(numbers[i ++]));
			}
		} 
		return bgCur.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();//四舍五入保留两位小数
	}
	
}

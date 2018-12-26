package com.gg.ssm.framework.utils;



import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.context.request.NativeWebRequest;

import com.fasterxml.jackson.annotation.JsonFormat;




/**
 * 
 * 测试加密算法
 * MD5Test
 * 创建人:Tengguang Yang
 * 手机：18734153794
 * 时间：2017年7月6日-下午10:36:49 
 * @version 1.0.0
 *
 */
public class MD5Test {
	
	public static void main(String[] args) {
		String plainPsd = "ytgytg123";
		//md5加密  不可逆
		String newPsd = DigestUtils.md5Hex(plainPsd.getBytes());
		//SHA1 不可逆
		String shaPsd = DigestUtils.sha1Hex(plainPsd.getBytes());
		//可逆  加密算法
		String BasePsd = Base64.encodeBase64String(plainPsd.getBytes());
		//解密  eXRneXRnMTIz
		String jm = "eXRneXRnMTIz";
		byte[] jmPsd = Base64.decodeBase64(jm);
		//System.out.println(newPsd+"======"+shaPsd+"======"+BasePsd);
		//System.out.println("解密:"+new String(jmPsd));
		
		
		/**
		 *  加密步骤:
		 *  	1:生成一个随机数
		 *  	2:用可逆的加密算法加密随机数(hex)
		 *  	3:将随机数和我们的密码用 不可逆加密算法加密 (sha1)
		 *  	4:将第三部得到的字符串用可逆的加密算法加密
		 *		5:将第二步和第四步的值拼在一起就是我们需要的加密密码
		 */
		String psd = "ytgytg321";
		//1
		byte[] radom = EncryptUtil.generateSalt(8);
		//2
		String radomHex = EncryptUtil.encodeHex(radom);
		//3
		byte[] thirdEn = EncryptUtil.sha1(psd.getBytes(), radom, 1024);
		//4
		String foursEn = EncryptUtil.encodeHex(thirdEn);
		//5  75482c3e0c7cffe048c8c527deef6279ebc6a3a856434c7639aa669b
		String finalEn = radomHex + foursEn;
		//System.out.println(finalEn);
		
		//匹配密码
		String nPsd = "ytgytg321";
		String enPsd = "79673f463dd7f5d61a85204e181471fcf5dc855ebb5a6d292d9884d1";
		byte[] salt = EncryptUtil.decodeHex(enPsd.substring(0, 16));
		//再次根据  密码   salt  迭代次数   生成新加密密码
		byte[] nthridEn = EncryptUtil.sha1(nPsd.getBytes(), salt, 1024);
		String newEn = EncryptUtil.encodeHex(salt)+EncryptUtil.encodeHex(nthridEn);
		//System.out.println(newEn);
		System.out.println("79673f463dd7f5d61a85204e181471fcf5dc855ebb5a6d292d9884d1".equals("79673f463dd7f5d61a85204e181471fcf5dc855ebb5a6d292d9884d1"));
	}
	
}

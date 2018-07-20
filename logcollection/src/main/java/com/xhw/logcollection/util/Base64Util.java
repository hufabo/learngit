package com.xhw.logcollection.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@SuppressWarnings({ "restriction", "unused" })
public class Base64Util {

    public static final  String SKEY = "2017100862701666"; // 固定密钥
    public static final String IVPARAMETER = "xhwqh2017nbiot23"; // 密钥偏移量IVPARAMETER
    public static final String ENCODEINGFORMAT="utf-8";

	/**
	 * base64加密
	 * @param string
	 * @return
	 */
	public static String encode(final String string) {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 补码方式
			byte[] raw = SKEY.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES"); // 使用算法名字
			IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); // 初始化模式为加密模式，并指定密匙
			byte[] encrypted = cipher.doFinal(string.getBytes(ENCODEINGFORMAT));
			return new BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * base64解密
	 * @param string
	 * @return
	 */
	public static String decode(final String string) {
		try {
			byte[] raw = SKEY.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES"); // 使用算法名
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // 使用CBC模式，PKCS5Padding补全方式
			IvParameterSpec iv = new IvParameterSpec(IVPARAMETER.getBytes());// 偏移量
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv); // 初始化模式为解密模式，并指定密匙
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(string);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, ENCODEINGFORMAT);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

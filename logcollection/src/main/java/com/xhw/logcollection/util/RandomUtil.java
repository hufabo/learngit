package com.xhw.logcollection.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * <P>随机数</P>
 * @author wanghaiyang
 * @Time 2017.14.41
 */
public class RandomUtil {
	private final Random RANDOM;

	private RandomUtil() {
		RANDOM = new Random();
	}

	private static class RandomHolder {
		private static final RandomUtil INSTANCE = new RandomUtil();
	}

	public static final RandomUtil getInstance() {
		return RandomHolder.INSTANCE;
	}

	public Random getRandom() {
		return getInstance().RANDOM;
	}

	public int nextInt() {
		return getRandom().nextInt();
	}

	public int nextInt(int bound) {
		return getRandom().nextInt(bound);
	}

	public long nextLong() {
		return getRandom().nextLong();
	}

	public double nextDouble() {
		return getRandom().nextDouble();
	}

	public double nextFloat() {
		return getRandom().nextFloat();
	}

	public boolean nextBoolean() {
		return getRandom().nextBoolean();
	}

	public double nextGaussian() {
		return getRandom().nextGaussian();
	}

	public void nextBytes(byte[] bytes) {
		getRandom().nextBytes(bytes);
	}

	public static String getRandomCode() {
		String[] beforeShuffle = new String[] {"0","1", "2", "3", "4", "5", "6", "7",
				"8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };
		List<String> list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(3, 9);
		return result;
	}

}

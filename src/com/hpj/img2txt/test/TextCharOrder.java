package com.hpj.img2txt.test;

public class TextCharOrder {

	public static void main(String[] args) {
		char c = 0;
		StringBuilder sb = new StringBuilder();
		//		for (; c < Character.MAX_VALUE; c++) {
		for (; c < 256; c++) {
			sb.append(c);
			if (c % 100 == 0)
				sb.append(System.lineSeparator());
		}
		System.out.println(sb);
	}
}

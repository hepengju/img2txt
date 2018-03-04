package com.hpj.img2txt.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.hpj.img2txt.Config;
import com.hpj.img2txt.App;
import com.hpj.img2txt.Style;

public class AppTest {

	private Config config;
	private App app;
	
	@Before
	public void Before() {
		String filePath = "d:/china.png";
		filePath = "d:/00.png";
		filePath = "d:/01.jpg";
		config = new Config(filePath);
		config.setTextChar(Style.BASIC.toString());
		config.setTextChar(Style.ASCII.toString());
		config.setTextChar(Style.ASCII_NO_ALPHA_NUMBER.toString());
		//config.setTextChar(Style.UNICODE.toString());
		//config.setTextChar(Style.UNICODE_SIMPLE.toString());
		config.setTextChar(Style.HE.toString());
		config.setTextWidth(100);
		app = new App(config);
	}

	@Test
	public void test01() throws IOException {
		String str1 = app.makeText();
		System.out.println(str1);
	}

}

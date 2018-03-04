package com.hpj.img2txt;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.hpj.img2txt.util.ImageUtil;

/**
 * 主函数入口.
 * <br>
 * <b>构造函数需要传入配置</b>
 *
 * @author he_pe 2017-12-29
 *
 */
public class App {
	
	private Config config;
	
	public App(Config config) {
		this.config = config;
	}
	
	public String makeText() throws IOException{
		BufferedImage image = ImageUtil.readImage(config.getFilePath());
		BufferedImage compressImage = ImageUtil.compressImage(image, config.getTextWidth(), config.getTextScale());
		String text = fillText(compressImage);
		return text;
	}
	
	/** 填充文本  */
	private String fillText(BufferedImage image) {
		ImageRGB imageRGB = ImageUtil.calcImageRGB(image);
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < image.getHeight(); x++) {
			for (int y = 0; y < image.getWidth(); y++) {
				Color color = new Color(image.getRGB(y, x));
				int average = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
				fillChar(sb, imageRGB, average);
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	
	/** 填充单个字符 */
	private void fillChar(StringBuilder sb, ImageRGB imageRGB, int average) {
		char[] charArr = (config.getTextChar().trim() + " ").toCharArray();
		
		//字符串为单个字符时的优化策略(不用去计算占比,+1是因为追加了空格)
		if (charArr.length <= 1 + 1) {
			if (charArr.length == 1 ) charArr = new char[] {'*'};
			if(average < imageRGB.getMaxRGB()) {
				sb.append(charArr[0]);
			}else {
				sb.append(" ");
			}
		//字符串为多个字符时,按照RGB均值占比填充占比一致的字符
		}else {
			int index = 0;
			if (imageRGB.getMaxRGB() - imageRGB.getMinRGB() != 0) {
				//当average=max时,填充最浅的颜色,index=charArr.length -1
				//当average=min时,填充最深的颜色,index=0
				index = (charArr.length - 1) * (average - imageRGB.getMinRGB())
						/ (imageRGB.getMaxRGB() - imageRGB.getMinRGB());
			}

			//处理脚标越界异常
			index = index < 0 ? 0 : index;
			index = index > charArr.length - 1? charArr.length - 1 : index;
			sb.append(charArr[index]);
		}
	}
}

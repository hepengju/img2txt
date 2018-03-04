package com.hpj.img2txt;

/**
 * 配置选项
 * 
 * @author he_pe 2017-12-28
 */
public class Config {

	private String filePath  = ""  ; //图片文件路径
	private int    textWidth = 100 ; //文字的宽度. 数值越大图形越精细(查看时文字会过小)
	private double textScale = 0.45; //文字的宽高比例. 因为一比一缩放,宽高比例异常,按照Notepad++比例基本上为0.5比较合适
	private String textChar  = Style.HE.toString()  ; //填充的字符

	public Config() {
		super();
	}

	////////////////////////////////////////////////////
	public Config(String filePath) {
		super();
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public int getTextWidth() {
		return textWidth;
	}

	public void setTextWidth(int textWidth) {
		this.textWidth = textWidth;
	}

	public double getTextScale() {
		return textScale;
	}

	public void setTextScale(double textScale) {
		this.textScale = textScale;
	}

	public String getTextChar() {
		return textChar;
	}

	public void setTextChar(String textChar) {
		this.textChar = textChar;
	}
	
}

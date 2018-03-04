package com.hpj.img2txt;

import java.awt.image.BufferedImage;

public class ImageRGB {

	private BufferedImage image; // 图片
	private Integer minRGB; // 图片所有像素点的RGB均值的最小值
	private Integer midRGB; // 平均RGB
	private Integer maxRGB; // 最大RGB

	private Integer countRGB; // 像素点数量
	private Long sumRGB; // RGB总和

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Integer getMinRGB() {
		return minRGB;
	}

	public void setMinRGB(Integer minRGB) {
		this.minRGB = minRGB;
	}

	public Integer getMidRGB() {
		return midRGB;
	}

	public void setMidRGB(Integer midRGB) {
		this.midRGB = midRGB;
	}

	public Integer getMaxRGB() {
		return maxRGB;
	}

	public void setMaxRGB(Integer maxRGB) {
		this.maxRGB = maxRGB;
	}

	public Long getSumRGB() {
		return sumRGB;
	}

	public void setSumRGB(Long sumRGB) {
		this.sumRGB = sumRGB;
	}

	public Integer getCountRGB() {
		return countRGB;
	}

	public void setCountRGB(Integer countRGB) {
		this.countRGB = countRGB;
	}

	@Override
	public String toString() {
		return "ImageRGB [minRGB=" + minRGB + ", midRGB=" + midRGB + ", maxRGB=" + maxRGB
				+ ", countRGB=" + countRGB + ", sumRGB=" + sumRGB + "]";
	}

}

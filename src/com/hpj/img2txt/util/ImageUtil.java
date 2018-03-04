package com.hpj.img2txt.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.hpj.img2txt.ImageRGB;

public class ImageUtil {

	/**
	 * 读取图片
	 */
	public static BufferedImage readImage(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			System.err.println("文件不存在: " + file.getAbsolutePath());
			System.exit(1);
		}
		return ImageIO.read(file);
	}

	/**
	 * 写出图片
	 */
	public static void writeIamge(BufferedImage image, String destPath) throws IOException {
		File file = new File(destPath);
		//当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		ImageIO.write(image, "jpg", file);
	}
	
	/**
	 * 绘制图片
	 */
	public static BufferedImage createImage(String content,Font font,int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, width, height);
		g2.setPaint(Color.BLACK);

		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(content, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;

        //g2.drawString(content, (int)x, (int)baseY ); 
		g2.drawString(content, (int)x - 1, (int)baseY + 2); 
		return bi;
	}

	/**
	 * 计算图片RGB信息
	 */
	public static ImageRGB calcImageRGB(BufferedImage image) {
		ImageRGB imageRGB = new ImageRGB();

		imageRGB.setImage(image);

		int count = 0;
		long sumRGB = 0;

		for (int x = 0; x < image.getHeight(); x++) {
			for (int y = 0; y < image.getWidth(); y++) {
				Color pixel = new Color(image.getRGB(y, x));
				int pixelRGB = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
				
				if(x == 0 && y == 0) {
					imageRGB.setMinRGB(pixelRGB);
					imageRGB.setMaxRGB(pixelRGB);
				}
				
				imageRGB.setMinRGB(Math.min(pixelRGB, imageRGB.getMinRGB()));
				imageRGB.setMaxRGB(Math.max(pixelRGB, imageRGB.getMaxRGB()));

				count++;
				sumRGB += pixelRGB;
			}
		}
		imageRGB.setSumRGB(sumRGB);
		imageRGB.setCountRGB(count);
		imageRGB.setMidRGB((int)(sumRGB / count));
		//System.err.println(imageRGB);
		return imageRGB;
	}

	/**
	 * 根据配置压缩图片
	 */
	public static BufferedImage compressImage(BufferedImage image, int newWidth, double textScale) {
		//宽度对比
		int srcWidth = image.getWidth();
		if (srcWidth <= newWidth)
			return image;

		//计算新的高度: 考虑配置的文字的宽高对比
		int srcHeight = image.getHeight();
		int newHeight = (int) (textScale * newWidth * srcHeight / srcWidth);

		//创建新图片
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, image.getType());
		Image scaledInstance = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		newImage.getGraphics().drawImage(scaledInstance, 0, 0, null);

		return newImage;
	}

	public static String orderByRGB(String str) {
		char[] chars = str.toCharArray();
		Map<Character,Long> map = new TreeMap<>();
		
		//生成单个小图片计算
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			Font font = new Font("微软雅黑", Font.BOLD, 10);
			BufferedImage image = ImageUtil.createImage(String.valueOf(c),font, 10, 14);
			ImageRGB imageRGB = ImageUtil.calcImageRGB(image);
			map.put(c, imageRGB.getSumRGB());

		}
		//输出查看
		//map.forEach((k,v) -> System.out.println(k + " --> " + v));
		
		//排序后返回
		List<Character> list = map.keySet().stream().collect(Collectors.toList());
		list.sort(Comparator.comparing(c -> map.get(c)));
		String collect = list.stream().map(String::valueOf).collect(Collectors.joining());
		return collect;
	}
}

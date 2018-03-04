package com.hpj.img2txt.test;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.junit.Test;

import com.hpj.img2txt.ImageRGB;
import com.hpj.img2txt.Style;
import com.hpj.img2txt.util.ImageUtil;

public class ImageUtilTest {

	private Font font = new Font("Courier New", Font.BOLD, 10);
	
	@Test //测试单个字符写出文件,查看效果
	public void fun01() throws IOException {
		BufferedImage image = ImageUtil.createImage("C",font, 10, 14);
		ImageUtil.writeIamge(image, "d:/new.jpg");
	}
	
	@Test //测试计算单个字符的RGB均值
	public void fun02() throws IOException {
		String c = "#";
		BufferedImage image = ImageUtil.createImage(c,font, 10, 14);
		ImageRGB imageRGB = ImageUtil.calcImageRGB(image);
		System.out.println(imageRGB);
		System.out.println(imageRGB.getMidRGB());
	}
	
	@Test //打印所有ASCII码,手动找出可供使用的(因为有些是不可见字符,有些看起来像乱码..)
	public void printAscii() {
		char c = 0;
		for (; c < 256; c++) {
			//if(c >= 32 && c <=125)
			if(c >= 32 && c <=125)
				if(!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >='0' && c <='9'))
			System.out.print(c);
			//System.out.print(String.valueOf(c) + (int)c);
		}
	}
	
	@Test //测试ASCII字符的RGB总值(均值很多一样了),集成到ImageUtil中去
	public void fun03() throws IOException {
		String str = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
		
		char[] chars = str.toCharArray();
		Map<Character,Long> map = new TreeMap<>();
		
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			BufferedImage image = ImageUtil.createImage(String.valueOf(c),font, 10, 14);
			ImageRGB imageRGB = ImageUtil.calcImageRGB(image);
			map.put(c, imageRGB.getSumRGB());

		}
		//输出查看
		map.forEach((k,v) -> System.out.println(k + " --> " + v));
		
		//排序后输出
		List<Character> list = map.keySet().stream().collect(Collectors.toList());
		list.sort(Comparator.comparing(c -> map.get(c)));
		String collect = list.stream().map(String::valueOf).collect(Collectors.joining());
		System.out.println(collect);
	}
	
	@Test 
	public void fun04() {
		String str = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
		str = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}";
		str = Style.UNICODE.toString();
		str = Style.UNICODE_SIMPLE.toString();
		String orderByRGB = ImageUtil.orderByRGB(str);
		System.out.println(orderByRGB);
	}	
	
	
	@Test //打印所有Unicode码,手动找出可供使用的(因为有些是不可见字符,有些看起来像乱码..)
	public void printUnicode() {
		char c = 0;
		for (; c < Character.MAX_VALUE; c++) {
			//if(c >= 32 && c <=125)
			//if(c >= 32 && c <=125)
				//if(!(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >='0' && c <='9'))
			System.out.print(c);
			if (c % 100 == 0)
				System.out.println();
		}
	}
	/*
	 * 手工挑选的一些可供使用的
	▁▂▃▄▅▆▇█▉▊▋▌▍▎▏▐░▒▓▔▕▖▗▘▙▚▛▜▝▞▟■□▢▣▤▥▦▧▨▩▪▫▬▭▮▯▰▱▲△▴▵▶▷▸▹►▻▼▽▾▿◀◁◂◃◄◅◆◇◈◉◊○◌◍◎●◐◑◒◓◔◕◖◗◘◙◚◛◜◝◞◟◠◡◢◣◤
	◥◦◧◨◩◪◫◬◭◮◯◰◱◲◳◴◵◶◷◸◹◺◻◼◽◾◿☀☁☂☃☄★☆☇☈☉☊☋☌☍☎☏☐☑☒☓☔☕☖☗☘☙☚☛☜☝☞☟☠☡☢☣☤☥☦☧☨☩☪☫☬☭☮☯☰☱☲☳☴☵☶☷☸☹☺☻☼☽☾☿♀♁♂♃♄♅♆♇♈
	♉♊♋♌♍♎♏♐♑♒♓♔♕♖♗♘♙♚♛♜♝♞♟♠♡♢♣♤♥♦♧♨♩♪♫♬♭♮♯♰♱♲♳♴♵♶♷♸♹♺♻♼♽♾♿⚀⚁⚂⚃⚄⚅⚆⚇⚈⚉⚊⚋⚌⚍⚎⚏⚐⚑⚒⚓⚔⚕⚖⚗⚘⚙⚚⚛⚜⚝⚞⚟⚠⚡⚢⚣⚤⚥⚦⚧⚨⚩⚪⚫⚬
	⚭⚮⚯⚰⚱⚲⚳⚴⚵⚶⚷⚸⚹⚺⚻⚼⚽⚾⚿⛀⛁⛂⛃⛄⛅⛆⛇⛈⛉⛊⛋⛌⛍⛎⛏⛐⛑⛒⛓⛔⛕⛖⛗⛘⛙⛚⛛⛜⛝⛞⛟⛠⛡⛢⛣⛤⛥⛦⛧⛨⛩⛪⛫⛬⛭⛮⛯⛰⛱⛲⛳⛴⛵⛶⛷⛸⛹⛺⛻⛼⛽⛾⛿✀✁✂✃✄✅✆✇✈✉✊✋✌✍✎✏✐
	✑✒✓✔✕✖✗✘✙✚✛✜✝✞✟✠✡✢✣✤✥✦✧✨✩✪✫✬✭✮✯✰✱✲✳✴✵✶✷✸✹✺✻✼✽✾✿❀❁❂❃❄❅❆❇❈❉❊❋❌❍❎❏❐❑❒❓❔❕❖❗❘❙❚❛❜❝❞❟❠❡❢❣❤❥❦❧❨❩❪❫❬❭❮❯❰❱❲❳❴
	❵➔➕➖➗➘➙➚➛➜➝➞➟➠➡➢➣➤➥➦➧➨➩➪➫➬➭➮➯➰➱➲➳➴➵➶➷➸➹➺➻➼➽➾➿⟀⟁⟂⟃⟄⟅⟆⟇⟈⟉⟊⟋⟌⟍⟎⟏⟐⟑⟒⟓⟔⟕⟖⟗⟘
	⠁⠂⠃⠄⠅⠆⠇⠈⠉⠊⠋⠌⠍⠎⠏⠐⠑⠒⠓⠔⠕⠖⠗⠘⠙⠚⠛⠜⠝⠞⠟⠠⠡⠢⠣⠤⠥⠦⠧⠨⠩⠪⠫⠬⠭⠮⠯⠰⠱⠲⠳⠴⠵⠶⠷⠸⠹⠺⠻⠼
	⠽⠾⠿⡀⡁⡂⡃⡄⡅⡆⡇⡈⡉⡊⡋⡌⡍⡎⡏⡐⡑⡒⡓⡔⡕⡖⡗⡘⡙⡚⡛⡜⡝⡞⡟⡠⡡⡢⡣⡤⡥⡦⡧⡨⡩⡪⡫⡬⡭⡮⡯⡰⡱⡲⡳⡴⡵⡶⡷⡸⡹⡺⡻⡼⡽⡾⡿⢀⢁⢂⢃⢄⢅⢆⢇⢈⢉⢊⢋⢌⢍⢎⢏⢐⢑⢒⢓⢔⢕⢖⢗⢘⢙⢚⢛⢜⢝⢞⢟⢠
	⢡⢢⢣⢤⢥⢦⢧⢨⢩⢪⢫⢬⢭⢮⢯⢰⢱⢲⢳⢴⢵⢶⢷⢸⢹⢺⢻⢼⢽⢾⢿⣀⣁⣂⣃⣄⣅⣆⣇⣈⣉⣊⣋⣌⣍⣎⣏⣐⣑⣒⣓⣔⣕⣖⣗⣘⣙⣚⣛⣜⣝⣞⣟⣠⣡⣢⣣⣤⣥⣦⣧⣨⣩⣪⣫⣬⣭⣮⣯⣰⣱⣲⣳⣴⣵⣶⣷⣸⣹⣺⣻⣼⣽⣾⣿
	*/
}

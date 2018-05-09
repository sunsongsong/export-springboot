package com.song.export.util.verificationCode;

import com.github.bingoohuang.patchca.background.BackgroundFactory;
import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.*;
import com.github.bingoohuang.patchca.font.RandomFontFactory;
import com.github.bingoohuang.patchca.service.Captcha;
import com.github.bingoohuang.patchca.text.renderer.BestFitTextRenderer;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ValidatCodeUtils {
	private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
	private static Random random = new Random();
	static {
		cs.setColorFactory(new ColorFactory() {
			@Override
			public Color getColor(int x) {
				int[] c = new int[3];
				int i = random.nextInt(c.length);
				for (int fi = 0; fi < c.length; fi++) {
					if (fi == i) {
						c[fi] = random.nextInt(71);
					} else {
						c[fi] = random.nextInt(200);
					}
				}
				return new Color(c[0], c[1], c[2]);
			}
		});

		cs.setBackgroundFactory(new BackgroundFactory() {
			@Override
			public void fillBackground(BufferedImage bufferedImage) {
				Graphics graphics = bufferedImage.getGraphics();

				// 验证码图片的宽高
				int imgWidth = bufferedImage.getWidth();
				int imgHeight = bufferedImage.getHeight();

				// 填充为白色背景
				graphics.setColor(getRandColor(230, 240));
				graphics.fillRect(0, 0, imgWidth, imgHeight);

				// 画100个噪点(颜色及位置随机)
				for (int i = 0; i < 80; i++) {
					// 随机颜色
					int rInt = random.nextInt(255);
					int gInt = random.nextInt(255);
					int bInt = random.nextInt(255);
					graphics.setColor(new Color(rInt, gInt, bInt));
					// 随机位置
					int xInt = random.nextInt(imgWidth - 4);
					int yInt = random.nextInt(imgHeight - 3);
					// 随机旋转角度
					int sAngleInt = random.nextInt(180);
					int eAngleInt = random.nextInt(180);
					// 随机大小
					int wInt = random.nextInt(2);
					int hInt = random.nextInt(1);
					graphics.fillArc(xInt, yInt, wInt, hInt, sAngleInt, eAngleInt);
					// 画3条干扰线
					if (i % 30 == 0) {
						int xInt2 = random.nextInt(imgWidth - 3);
						int yInt2 = random.nextInt(imgHeight - 2);
						graphics.drawLine(xInt, yInt, xInt2, yInt2);
					}
				}
			}
		});

		RandomWordFactory wf = new RandomWordFactory();
		wf.setCharacters("2345678abcdefhkmnpqrstuvwxyABCDEFGHGKLMNPQRSTUVWXYZ");
		wf.setMaxLength(4);
		wf.setMinLength(4);
		cs.setWordFactory(wf);

		// 验证码图片的大小
		cs.setWidth(100);
		cs.setHeight(38);

		// 随机字体生成器
		RandomFontFactory fontFactory = new RandomFontFactory();
		fontFactory.setMaxSize(23);
		fontFactory.setMinSize(22);
		cs.setFontFactory(fontFactory);

		// // 图片滤镜设置
		// ConfigurableFilterFactory filterFactory = new
		// ConfigurableFilterFactory();
		//
		// List<BufferedImageOp> filters = new ArrayList<BufferedImageOp>();
		// WobbleImageOp wobbleImageOp = new WobbleImageOp();
		// wobbleImageOp.setEdgeMode(AbstractImageOp.EDGE_MIRROR);
		// wobbleImageOp.setxAmplitude(2.0);
		// wobbleImageOp.setyAmplitude(1.0);
		// filters.add(wobbleImageOp);
		// filterFactory.setFilters(filters);
		// cs.setFilterFactory(filterFactory);

		// 文字渲染器设置
		BestFitTextRenderer textRenderer = new BestFitTextRenderer();
		textRenderer.setBottomMargin(6);
		textRenderer.setTopMargin(6);
		textRenderer.setLeftMargin(8);
		textRenderer.setRightMargin(8);
		cs.setTextRenderer(textRenderer);

	}

	/**
	 * 给定范围获得随机颜色
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	 public static String create(OutputStream os) throws IOException {
		 switch (random.nextInt(2)) {
			 case 0:
				 cs.setFilterFactory(new DoubleRippleFilterFactory());
				 break;
			 case 1:
				 cs.setFilterFactory(new WobbleRippleFilterFactory());
				 break;
			 case 2:
				 cs.setFilterFactory(new MarbleRippleFilterFactory());
				 break;
		 }
		 return EncoderHelper.getChallangeAndWriteImage(cs, "jpeg", os);
	 }

	/**
	 * 生成image图片
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Map createImage() throws IOException {
		switch (random.nextInt(2)) {
		case 0:
			cs.setFilterFactory(new DoubleRippleFilterFactory());
			break;
		case 1:
			cs.setFilterFactory(new WobbleRippleFilterFactory());
			break;
		case 2:
			cs.setFilterFactory(new MarbleRippleFilterFactory());
			break;
		}
		Map map = new HashMap();
		Captcha captcha = cs.getCaptcha();
		// 验证码
		String code = captcha.getChallenge();
		map.put("code", code);
		// 生成图片验证码 base64编码
		BufferedImage image = captcha.getImage();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "png", outputStream);
		BASE64Encoder encoder = new BASE64Encoder();
		map.put("image", encoder.encode(outputStream.toByteArray()));
		return map;
	}

	/**
	 * 生成UUID作为唯一标识
	 * 
	 * @return
	 */
	public static synchronized String generateUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		String uuidStr = str.replace("-", "");
		return uuidStr;
	}
}

package com.spon.utils.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.struts.Globals;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
public class SponMark extends HttpServlet{
	private static final String CONTENT_TYPE = "image/jpeg";
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Add by John 2014/02/20
		//判斷語言並國際套用國際化Resource
		//若新增其他語言, 請記得BA_MENULISTA.revmenu()也要同時異動
		Locale languagetype = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
		ResourceBundle reesource = null;
		if(languagetype.toString().equals("zh_TW")){
			reesource = ResourceBundle.getBundle("EMSResources_zh_TW");
		}else if(languagetype.toString().equals("zh_CN")){
			reesource = ResourceBundle.getBundle("EMSResources_zh_CN");
		}else if(languagetype.toString().equals("en_US")){
			reesource = ResourceBundle.getBundle("EMSResources_en");
		}else if(languagetype.toString().equals("ja_JP")){
			reesource = ResourceBundle.getBundle("EMSResources_ja");
		}else{
			reesource = ResourceBundle.getBundle("EMSResources");
		}
		
		//Add by John 2014/02/20
		//從EMSResource取得國際化程式名稱
		//若新增其他語言, 請記得BA_MENULISTA.revmenu()也要同時異動
		String text = reesource.getString(request.getParameter("text"));
		
		request.setCharacterEncoding("utf-8");
		
		String type = (request.getParameter("type") == null)?"":request.getParameter("type");
		if (type.equals("t")){
			type = "enabled";
		}else{
			type = "disabled";
		}

		BufferedImage im = new BufferedImage(90, 35, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = im.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("新細明體", Font.BOLD , 15));
		
		TextLayout tl = new TextLayout(text, g.getFont(), g.getFontRenderContext());
		//由文字框取得文字邊界
		Rectangle2D bounds = tl.getBounds();
		double width = bounds.getWidth();
		URL url = null;
		if (width >= 160){
			im = ImageIO.read(new File(request.getRealPath("") + "/config/btn_210_" + type + "_1.jpg"));
		}else if (width >= 130){
			im = ImageIO.read(new File(request.getRealPath("") + "/config/btn_180_" + type + "_1.jpg"));
		}else if (width >= 100){
			im = ImageIO.read(new File(request.getRealPath("") + "/config/btn_150_" + type + "_1.jpg"));
		}else if (width >= 70){
			im = ImageIO.read(new File(request.getRealPath("") + "/config/btn_120_" + type + "_1.jpg"));
		}else if (width >= 40){
			im = ImageIO.read(new File(request.getRealPath("") + "/config/btn_90_" + type + "_1.jpg"));
		}else{
			im = ImageIO.read(new File(request.getRealPath("") + "/config/btn_60_" + type + "_1.jpg"));
		}
		//im = ImageIO.read(url);
		g = im.createGraphics();
		
		double x = (im.getWidth() - bounds.getWidth()) / 2 - bounds.getX();
		double y = (im.getHeight() - bounds.getHeight()) / 2 - bounds.getY();
		//重疊邊界的偏移大小
		Shape outline = tl.getOutline(AffineTransform.getTranslateInstance(
				x + 1, y + 1));
		
		g.setPaint(Color.BLACK);
		//畫出重疊邊界
		//g.draw(outline);
		//畫出文字區塊，並設置顏色偏移
		g.setPaint(new GradientPaint(0, 0, Color.BLUE  , 30, 20, Color.BLUE, true));
		tl.draw(g, (float) x, (float) y);
		
		ServletOutputStream output=response.getOutputStream();
		response.setContentType(CONTENT_TYPE);
		
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
		JPEGEncodeParam jep = encoder.getDefaultJPEGEncodeParam(im);
		jep.setQuality(1.0f, false);
		encoder.setJPEGEncodeParam(jep);

		encoder.encode(im);
		
		output.close();

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doGet(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {

		// TODO Auto-generated method stub
		String text = DateFormat.getDateInstance(DateFormat.SHORT).format(
				new Date());
		
		text = "新增新增新增";
		String type = "enabled";
			
		BufferedImage im = new BufferedImage(90, 35, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = im.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("新細明體", Font.BOLD , 15));
		TextLayout tl = new TextLayout(text, g.getFont(), g.getFontRenderContext());
		//由文字框取得文字邊界
		Rectangle2D bounds = tl.getBounds();
		double width = bounds.getWidth();
		URL url = null;
		if (width >= 70){
			im = ImageIO.read(new File("D:/workspace/SPOS/WebRoot" + "/config/btn_120_" + type + ".jpg"));
		}else{
			im = ImageIO.read(new File("D:/workspace/SPOS/WebRoot" + "/config/btn_90_" + type + ".jpg"));
		}
		//im = ImageIO.read(url);

		g = im.createGraphics();
		
		double x = (im.getWidth() - bounds.getWidth()) / 2 - bounds.getX();
		double y = (im.getHeight() - bounds.getHeight()) / 2 - bounds.getY();
		//重疊邊界的偏移大小
		Shape outline = tl.getOutline(AffineTransform.getTranslateInstance(
				x + 2, y + 1));
		//透明色彩
		//g.setComposite(AlphaComposite
		//		.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		
		g.setPaint(Color.WHITE );
		//畫出重疊邊界
		//g.draw(outline);
		//g.setPaint(new GradientPaint(0, 0, Color.BLACK , 30, 20, new Color(128,
		//		128, 255), true));
		g.setPaint(new GradientPaint(0, 0, Color.BLACK , 30, 20, Color.WHITE, true));
		tl.draw(g, (float) x, (float) y);
		
		g.dispose();
		display(im);
		
	}
	
	public static void display(BufferedImage image) {
		JFrame f = new JFrame("SponMark");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new JLabel(new ImageIcon(image)));
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
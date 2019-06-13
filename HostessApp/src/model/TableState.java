package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class TableState {
private TableState() {}
	
	/**
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static Image ocupiedTable(String image) throws IOException {
		File photoFile = new File(image);
		BufferedImage bi = ImageIO.read(photoFile);
		BufferedImage bi2 = new BufferedImage(
				bi.getWidth(),
				bi.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i < bi.getHeight(); i++) {
			for(int j = 0; j < bi.getWidth(); j++) {
				//get the value of the pixel at co-ordinate (x,y) using thegetRGB(x,y) method. 
				//Then we will extract the Alpha and Green value from the pixel value
				int pixels = bi.getRGB(j, i);
				int alpha = (pixels>>24)&0xff;
				int red = (pixels>>16)&0xff;
				//To convert a color image into Red image 
				pixels = (alpha<<24) | (red<<16) | (0<<8) | 0;
				bi2.setRGB(j, i, pixels);
			}
		}
		Image img =  SwingFXUtils.toFXImage(bi2, null);
		return img;
	}
	
	/**
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static Image dirtyTable(String image) throws IOException {
		File photoFile = new File(image);
		BufferedImage bi = ImageIO.read(photoFile);
		BufferedImage bi2 = new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i < bi.getHeight(); i++) {
			for(int j = 0; j < bi.getWidth(); j++) {
				//get the value of the pixel at co-ordinate (x,y) using thegetRGB(x,y) method. 
				//Then we will extract the Alpha and Green value from the pixel value
				int pixels = bi.getRGB(j, i);
				int alpha = (pixels>>24)&0xff;
				int blue = (pixels)&0xff;
				//To convert a color image into Yellow image 
				pixels = (alpha<<24) | (0<<16) | (0<<8) | blue;
				bi2.setRGB(j, i, pixels);
			}
		}
		Image img =  SwingFXUtils.toFXImage(bi2, null);
		return img;
	}
	
	/**
	 * 
	 * @param image
	 * @return
	 * @throws IOException
	 */
	public static Image cleanTable(String image) throws IOException {
		File photoFile = new File(image);
		BufferedImage bi = ImageIO.read(photoFile);
		BufferedImage bi2 = new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i < bi.getHeight(); i++) {
			for(int j = 0; j < bi.getWidth(); j++) {
				//get the value of the pixel at co-ordinate (x,y) using thegetRGB(x,y) method. 
				//Then we will extract the Alpha and Green value from the pixel value
				int pixels = bi.getRGB(j, i);
				int alpha = (pixels>>24)&0xff;
				int green = (pixels>>8)&0xff;
				//To convert a color image into Green image 
				pixels = (alpha<<24) | (0<<16) | (green<<8) | 0;
				bi2.setRGB(j, i, pixels);
			}
		}
		Image img =  SwingFXUtils.toFXImage(bi2, null);
		return img;
	}
}

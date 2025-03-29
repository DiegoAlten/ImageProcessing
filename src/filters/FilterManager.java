package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class FilterManager {
	
    public static BufferedImage grayscale(BufferedImage image) {
    	
    	// (Cor R + Cor G + Cor B)/3
    	
    	int imgWidth  = image.getWidth();
        int imgHeight = image.getHeight();
    	
        BufferedImage imgGrayscale = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
            	
                Color color = new Color(image.getRGB(x, y));
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                imgGrayscale.setRGB(x, y, new Color(gray, gray, gray).getRGB());
                
            }
        }
        
        return imgGrayscale;
    }
    
}

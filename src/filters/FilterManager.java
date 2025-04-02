package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

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
    
    public static BufferedImage brightnessContrast(BufferedImage image, int brightness, int Contrast) {
    	
    	// contraste * img de origem + brilho
    	// se o novo valor passar de 255, novo valor = 255
    	// se o novo valor ficar abaixo de 0, novo valor = 0
    	
    	int imgWidth  = image.getWidth();
        int imgHeight = image.getHeight();
    	
        BufferedImage imgBrilho = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        
        for (int x = 0; x < imgWidth; x++) {
            for (int y = 0; y < imgHeight; y++) {
            	
                Color color = new Color(image.getRGB(x, y)); 
                
                int vermelho = limitarValor(Contrast * color.getRed() + brightness);
                int verde = limitarValor(Contrast * color.getGreen() + brightness);
                int azul = limitarValor(Contrast * color.getBlue() + brightness);      
                
                imgBrilho.setRGB(x, y, new Color(vermelho,verde,azul).getRGB());
                
            }
        }
        
        return imgBrilho;
    }
    
    // Limita valores entre 0 e 255
    private static int limitarValor(int valor) {
        return Math.max(0, Math.min(255, valor));
    }
    
}

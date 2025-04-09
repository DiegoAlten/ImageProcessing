package transforms;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class TransformManager {
	
	public static BufferedImage translated(BufferedImage image) {
    	
    	/*
    	 * [x']   1  0  x   [x]
    	 * [y'] = 0  1  y   [y]
    	 * [1 ]   0  0  1   [1]
    	*/
    	int imgWidth  = image.getWidth();
        int imgHeight = image.getHeight();
        
        BufferedImage translatedImage = new BufferedImage(imgWidth, imgHeight, image.getType());
        
    	int translateX = Integer.parseInt(JOptionPane.showInputDialog(null,"Informe o valor x"));
        int translateY = Integer.parseInt(JOptionPane.showInputDialog(null,"Informe o valor y"));

        double[][] matrizTranslate = {  { 1, 0, translateX },
                                        { 0, 1, translateY },
                                        { 0, 0, 1 } };
        
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
            	
                int newX = (int) Math.round(x * matrizTranslate[0][0] + y * matrizTranslate[0][1] + matrizTranslate[0][2]);
                int newY = (int) Math.round(x * matrizTranslate[1][0] + y * matrizTranslate[1][1] + matrizTranslate[1][2]);

                // Verifica se a nova posição está dentro dos limites da imagem
                if (newX >= 0 && newX < imgWidth && newY >= 0 && newY < imgHeight) {
                    translatedImage.setRGB(newX, newY, image.getRGB(x, y));
                }
                
            }
        }
        
        return translatedImage;
    }

	public static BufferedImage scale(BufferedImage image) {
    	
    	/*
    	 * [x']   x  0  0   [x]
    	 * [y'] = 0  y  0   [y]
    	 * [1 ]   0  0  1   [1]
    	*/
    	int imgWidth  = image.getWidth();
        int imgHeight = image.getHeight();
        
        BufferedImage translatedImage = new BufferedImage(imgWidth, imgHeight, image.getType());
        
    	double scale = Double.parseDouble(JOptionPane.showInputDialog(null,"Informe o valor da escala"));

        double[][] matrizScaled = {  { scale, 0,     0 },
                                     { 0,     scale, 0 },
                                     { 0,     0,     1 } };
        
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
            	
                int newX = (int) Math.round(x * matrizScaled[0][0] + y * matrizScaled[0][1] + matrizScaled[0][2]);
                int newY = (int) Math.round(x * matrizScaled[1][0] + y * matrizScaled[1][1] + matrizScaled[1][2]);

                // Verifica se a nova posição está dentro dos limites da imagem
                if (newX >= 0 && newX < imgWidth && newY >= 0 && newY < imgHeight) {
                    translatedImage.setRGB(newX, newY, image.getRGB(x, y));
                }
                
            }
        }
        
        return translatedImage;
    }

    public static BufferedImage rotate(BufferedImage image, double angle) {
    	
    	/*
    	 * [x']   cos  -sen  0   [x]
    	 * [y'] = sen  cos   0   [y]
    	 * [1 ]    0    0    1   [1]
    	*/
    	
        int imgWidth  = image.getWidth();
        int imgHeight = image.getHeight();
        
        BufferedImage imgRotated = new BufferedImage(imgWidth, imgHeight, image.getType());
        
        double[][] matrixRotation = { { Math.cos(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle)), 0 },
						              { Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0},
						              { 0, 0, 1 } };
        
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
            	
                double halfX = imgWidth / 2.0;
                double halfY = imgHeight / 2.0;
                double tmpX = x - halfX;
                double tmpY = y - halfY;
                double newX = Math.round(tmpX * matrixRotation[0][0] + tmpY * matrixRotation[0][1] + matrixRotation[0][2]);
                double newY = Math.round(tmpX * matrixRotation[1][0] + tmpY * matrixRotation[1][1] + matrixRotation[1][2]);
                newX += halfX;
                newY += halfY;

                if (newX < imgWidth && newY < imgHeight && newX >= 0 && newY >= 0) {
                	imgRotated.setRGB((int) newX,(int) newY, image.getRGB(x, y));
                }
            }
        }
        
        return imgRotated;
    }
    
    public static BufferedImage mirror(BufferedImage image) {
    	
    	/*
    	 * [x']   -1  0  0   [x]
    	 * [y'] =  0  1  0   [y]
    	 * [1 ]    0  0  1   [1]
    	*/
    	
    	int imgWidth  = image.getWidth();
        int imgHeight = image.getHeight();
        
        BufferedImage imgMirrored= new BufferedImage(imgWidth, imgHeight, image.getType());
        
        for (int y = 0; y < imgHeight; y++) { 
            for (int leftX = 0, rightX = imgWidth - 1; leftX < imgWidth; leftX++, rightX--) { 
                  
                int p = image.getRGB(leftX, y); 
  
                imgMirrored.setRGB(rightX, y, p); 
            } 
        } 
        
        return imgMirrored;
    }
    
    
    
}

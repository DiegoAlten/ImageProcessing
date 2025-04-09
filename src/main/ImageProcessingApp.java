package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import filters.FilterManager;
import transforms.TransformManager;

public class ImageProcessingApp {
    private JFrame frame;
    private JLabel originalImageLabel;
    private JLabel transformedImageLabel;
    private BufferedImage originalImage;
    private BufferedImage transformedImage; // Para armazenar a imagem processada

    public ImageProcessingApp() {
        frame = new JFrame("Processamento de Imagens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Criando a barra de menus
        JMenuBar menuBar = new JMenuBar();

        // Menu Arquivo
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem openItem  = new JMenuItem("Abrir imagem");
        JMenuItem saveItem  = new JMenuItem("Salvar imagem");
        JMenuItem aboutItem = new JMenuItem("Sobre");
        JMenuItem exitItem  = new JMenuItem("Sair");

        openItem.addActionListener(e -> openImage());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(aboutItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // Menu Transformações Geométricas
        JMenu transformMenu = new JMenu("Transformações Geométricas");
        JMenuItem transladarItem = new JMenuItem("Transladar");
        JMenuItem rotacionarItem = new JMenuItem("Rotacionar");
        JMenuItem espelharItem   = new JMenuItem("Espelhar");
        JMenuItem escalarItem   = new JMenuItem("Escalar");
        
        transformMenu.add(transladarItem);
        transformMenu.add(rotacionarItem);
        transformMenu.add(espelharItem);
        transformMenu.add(escalarItem);

        menuBar.add(transformMenu);
        
        // Chamando as funcoes
        transladarItem.addActionListener(e -> applyTranslate());
        rotacionarItem.addActionListener(e -> applyRotate());
        espelharItem.addActionListener(e -> applyMirror());
        escalarItem.addActionListener(e -> applyScale());
        
        // Menu Filtros
        JMenu filterMenu = new JMenu("Filtros");
        JMenuItem grayscaleItem  = new JMenuItem("Grayscale");
        JMenuItem brightnessItem = new JMenuItem("Brilho");
        JMenuItem contrastItem 	 = new JMenuItem("Contraste");
        JMenuItem passaBaixaItem = new JMenuItem("Passa Baixa");
        JMenuItem passaAltaItem  = new JMenuItem("Passa Alta");
        JMenuItem thresholdItem  = new JMenuItem("Threshold");
        
        filterMenu.add(grayscaleItem);
        filterMenu.add(brightnessItem);
        filterMenu.add(contrastItem);
        filterMenu.add(passaBaixaItem);
        filterMenu.add(passaAltaItem);
        filterMenu.add(thresholdItem);
        
        menuBar.add(filterMenu);
        
        // Chamando as funcoes
        grayscaleItem.addActionListener(e -> applyGrayscale()); 
        brightnessItem.addActionListener(e -> applyBrightnessContrast(Integer.parseInt(JOptionPane.showInputDialog(null,"Informe o brilho")),1)); 
        contrastItem.addActionListener(e -> applyBrightnessContrast(0,Integer.parseInt(JOptionPane.showInputDialog(null,"Informe o constraste")))); 
        passaBaixaItem.addActionListener(e -> applyLowPass());
        passaAltaItem.addActionListener(e -> applyHighPass());
        
        // Menu Morfologia Matemática
        /*JMenu morphologyMenu = new JMenu("Morfologia Matemática");
        morphologyMenu.add(new JMenuItem("Dilatação"));
        morphologyMenu.add(new JMenuItem("Erosão"));
        morphologyMenu.add(new JMenuItem("Abertura"));
        morphologyMenu.add(new JMenuItem("Fechamento"));
        menuBar.add(morphologyMenu);*/

        frame.setJMenuBar(menuBar);

        // Painel para exibição das imagens
        JPanel originalPanel = new JPanel(new BorderLayout());
        JLabel originalTitle = new JLabel("Imagem Original", SwingConstants.CENTER);
        originalImageLabel = new JLabel();
        originalPanel.add(originalTitle, BorderLayout.NORTH);
        originalPanel.add(originalImageLabel, BorderLayout.CENTER);

        JPanel transformedPanel = new JPanel(new BorderLayout());
        JLabel transformedTitle = new JLabel("Imagem Transformada", SwingConstants.CENTER);
        transformedImageLabel = new JLabel();
        transformedPanel.add(transformedTitle, BorderLayout.NORTH);
        transformedPanel.add(transformedImageLabel, BorderLayout.CENTER);

        JPanel imagePanel = new JPanel(new GridLayout(1, 2));
        imagePanel.add(originalPanel);
        imagePanel.add(transformedPanel);

        frame.add(imagePanel, BorderLayout.CENTER);

        // Carregar imagem padrão
        loadDefaultImage();

        frame.setVisible(true);
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                originalImage = ImageIO.read(file);
                originalImageLabel.setIcon(new ImageIcon(originalImage));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao abrir a imagem.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadDefaultImage() {
        try {
            originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resources/default.jpg")));
            originalImageLabel.setIcon(new ImageIcon(originalImage));
        } catch (IOException | NullPointerException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar imagem padrão.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void applyGrayscale() {
        if (originalImage == null) {
            JOptionPane.showMessageDialog(frame, "Abra uma imagem primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        transformedImage = FilterManager.grayscale(originalImage);
        transformedImageLabel.setIcon(new ImageIcon(transformedImage)); // Atualizando a tela
    }
    
    private void applyBrightnessContrast(int brightness, int Contrast) {
        if (originalImage == null) {
            JOptionPane.showMessageDialog(frame, "Abra uma imagem primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        transformedImage = FilterManager.brightnessContrast(originalImage, brightness, Contrast );
        transformedImageLabel.setIcon(new ImageIcon(transformedImage)); // Atualizando a tela
    }
    
    private void applyRotate() {
    	if (originalImage == null) {
            JOptionPane.showMessageDialog(frame, "Abra uma imagem primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    	
    	int angle = Integer.parseInt(JOptionPane.showInputDialog(null,"Informe o angulo da rotação"));
    	
    	transformedImage = TransformManager.rotate(originalImage, angle); 
        transformedImageLabel.setIcon(new ImageIcon(transformedImage)); // Atualizando a tela
    }
    
    private void applyTranslate() {
    	if (originalImage == null) {
            JOptionPane.showMessageDialog(frame, "Abra uma imagem primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    	
    	transformedImage = TransformManager.translated(originalImage); 
        transformedImageLabel.setIcon(new ImageIcon(transformedImage)); // Atualizando a tela
    }
    
    private void applyScale() {
    	if (originalImage == null) {
            JOptionPane.showMessageDialog(frame, "Abra uma imagem primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    	
    	transformedImage = TransformManager.scale(originalImage);
        transformedImageLabel.setIcon(new ImageIcon(transformedImage)); // Atualizando a tela
    }
    
    private void applyMirror() {
    	if (originalImage == null) {
            JOptionPane.showMessageDialog(frame, "Abra uma imagem primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    	
    	transformedImage = TransformManager.mirror(originalImage);
        transformedImageLabel.setIcon(new ImageIcon(transformedImage)); // Atualizando a tela
    }
    
    private void applyLowPass() {
    	if (originalImage == null) {
            JOptionPane.showMessageDialog(frame, "Abra uma imagem primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    	
    	float[][] lowPassKernel = {
		    {1f/9, 1f/9, 1f/9},
		    {1f/9, 1f/9, 1f/9},
		    {1f/9, 1f/9, 1f/9}
		};
    	
    	transformedImage = FilterManager.passFilter(transformedImage, lowPassKernel);
        transformedImageLabel.setIcon(new ImageIcon(transformedImage)); // Atualizando a tela
    }
    
    private void applyHighPass() {
    	if (originalImage == null) {
            JOptionPane.showMessageDialog(frame, "Abra uma imagem primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    	
    	// Kernel 3x3 passa-alto
    	float[][] highPassKernel = {
    	    {-1, -1, -1},
    	    {-1,  8, -1},
    	    {-1, -1, -1}
    	};

    	transformedImage = FilterManager.passFilter(transformedImage, highPassKernel);
        transformedImageLabel.setIcon(new ImageIcon(transformedImage)); // Atualizando a tela
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ImageProcessingApp::new);
    }
}

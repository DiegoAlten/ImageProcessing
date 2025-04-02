package dpi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class main {
    private JFrame frame;
    private JLabel originalImageLabel;
    private JLabel transformedImageLabel;
    private BufferedImage originalImage;
    
    public main() {
        frame = new JFrame("Processamento de Imagens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        
        // Criando a barra de menus
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Arquivo
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem openItem = new JMenuItem("Abrir imagem");
        JMenuItem saveItem = new JMenuItem("Salvar imagem");
        JMenuItem aboutItem = new JMenuItem("Sobre");
        JMenuItem exitItem = new JMenuItem("Sair");
        
        openItem.addActionListener(e -> openImage());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(aboutItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        
        // Menu Transformações Geométricas
        JMenu transformMenu = new JMenu("Transformações Geométricas");
        // Criando os itens do menu
        JMenuItem transladarItem = new JMenuItem("Transladar");
        JMenuItem rotacionarItem = new JMenuItem("Rotacionar");
        JMenuItem espelharItem 	 = new JMenuItem("Espelhar");
        JMenuItem aumentarItem   = new JMenuItem("Aumentar");
        JMenuItem diminuirItem 	 = new JMenuItem("Diminuir");

        // Adicionando ActionListener a cada item do menu
        transladarItem.addActionListener(e -> trasnlateImage());
        rotacionarItem.addActionListener(e -> System.out.println("Rotação aplicada"));
        espelharItem.addActionListener(e -> System.out.println("Espelhamento aplicado"));
        aumentarItem.addActionListener(e -> System.out.println("Aumento aplicado"));
        diminuirItem.addActionListener(e -> System.out.println("Redução aplicada"));

        // Adicionando os itens ao menu
        transformMenu.add(transladarItem);
        transformMenu.add(rotacionarItem);
        transformMenu.add(espelharItem);
        transformMenu.add(aumentarItem);
        transformMenu.add(diminuirItem);

        menuBar.add(transformMenu);
        
        
        // Menu Filtros
        JMenu filterMenu = new JMenu("Filtros");
        filterMenu.add(new JMenuItem("Grayscale"));
        filterMenu.add(new JMenuItem("Passa Baixa"));
        filterMenu.add(new JMenuItem("Passa Alta"));
        filterMenu.add(new JMenuItem("Threshold"));
        menuBar.add(filterMenu);
        
        // Menu Morfologia Matemática
        JMenu morphologyMenu = new JMenu("Morfologia Matemática");
        morphologyMenu.add(new JMenuItem("Dilatação"));
        morphologyMenu.add(new JMenuItem("Erosão"));
        morphologyMenu.add(new JMenuItem("Abertura"));
        morphologyMenu.add(new JMenuItem("Fechamento"));
        menuBar.add(morphologyMenu);
        
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
    
    private void trasnlateImage() {
    	 int x = Integer.parseInt(JOptionPane.showInputDialog(null,"inform o valor x"));
         int y = Integer.parseInt(JOptionPane.showInputDialog(null,"inform o valor y"));

         double[][] matrizTranslate = {  { 1, 0, x },
                                         { 0, 1, y },
                                         { 0, 0, 1 } };
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(main::new);
    }
}
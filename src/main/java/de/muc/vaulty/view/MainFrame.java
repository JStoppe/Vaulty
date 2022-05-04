package de.muc.vaulty.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class MainFrame extends JFrame {
	
	private final JSplitPane splitPane;
	private final JPanel topPanel;
	private final JPanel bottomPanel;
//	private final JScrollPane scrollPane;
//	private final JPanel walletA;
//	private final JPanel walletB;
//	private final JLabel label;
	
	private final JButton transfer, preview, receive; // transferB, previewB, receiveB
	private final JLabel balance;
	private final JTextField amount;
	
	private ImageIcon img;
	private JLabel walletLabelA;
	private ImageIcon img2;
	private JLabel walletLabelB;
	
	
	public MainFrame() {
		
		splitPane = new JSplitPane();
		
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		
		walletLabelA = new JLabel();
		walletLabelB = new JLabel();
		
		// transfer Button
		
		ImageIcon normal = new ImageIcon(getClass().getResource("/resources/TransferA.png"));
		ImageIcon hover = new ImageIcon(getClass().getResource("/resources/TransferAHover.png"));
		
		Image image = normal.getImage(); // transform it 
		Image newimg = image.getScaledInstance(153, 45,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		normal = new ImageIcon(newimg);  // transform it back
		
		Image image2 = hover.getImage(); // transform it 
		Image newimg2 = image2.getScaledInstance(153, 45,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		hover = new ImageIcon(newimg2);
		

		transfer = new JButton(normal);
		transfer.setRolloverIcon(hover);
		
		transfer.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		transfer.setContentAreaFilled(false);
		
	
		// preview A Button
		
		ImageIcon previewSized = new ImageIcon(getClass().getResource("/resources/PreviewA.png"));
		ImageIcon previewHover = new ImageIcon(getClass().getResource("/resources/PreviewAHover.png"));
		
		Image image3 = previewSized.getImage(); // transform it 
		Image newimg3 = image3.getScaledInstance(46, 55,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		previewSized = new ImageIcon(newimg3);  // transform it back
		
		Image image4 = previewHover.getImage(); // transform it 
		Image newimg4 = image4.getScaledInstance(46, 55,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		previewHover = new ImageIcon(newimg4);
		
		preview = new JButton(previewSized);
		preview.setRolloverIcon(previewHover);
		
		preview.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		preview.setContentAreaFilled(false);
		
		// receive A Button
		
		ImageIcon receiveSized = new ImageIcon(getClass().getResource("/resources/ReceiveA.png"));
		ImageIcon receiveHover = new ImageIcon(getClass().getResource("/resources/ReceiveAHover.png"));
		
		Image image5 = receiveSized.getImage(); // transform it 
		Image newimg5 = image5.getScaledInstance(46, 55,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		receiveSized = new ImageIcon(newimg5);  // transform it back
		
		Image image6 = receiveHover.getImage(); // transform it 
		Image newimg6 = image6.getScaledInstance(46, 55,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		receiveHover = new ImageIcon(newimg6);
		
		receive = new JButton(receiveSized);
		receive.setRolloverIcon(receiveHover);
		
		receive.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		receive.setContentAreaFilled(false);
	
		// balance
		
		balance = new JLabel("1000");
		balance.setFont(new Font("Avenir Next", Font.PLAIN, 24));
		balance.setForeground(Color.LIGHT_GRAY);
		balance.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// sending amount
		
		amount = new JTextField();
		amount.setFont(new Font("Avenir Next", Font.PLAIN, 24));
		amount.setForeground(Color.LIGHT_GRAY);
		amount.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		amount.setOpaque(false);
		amount.setBorder(null);


		// Wallets - image resized
		
		img = new ImageIcon(getClass().getResource("/resources/WalletA.png"));
		Image icon = img.getImage();
		Image imgScale = icon.getScaledInstance(380, 410, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(imgScale);
		walletLabelA.setIcon(scaledIcon);
		walletLabelA.setHorizontalAlignment(JLabel.CENTER);
		
		img2 = new ImageIcon(getClass().getResource("/resources/WalletB.png"));
		Image icon2 = img2.getImage();
		Image imgScale2 = icon2.getScaledInstance(380, 410, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon2 = new ImageIcon(imgScale2);
		walletLabelB.setIcon(scaledIcon2);
		walletLabelB.setHorizontalAlignment(JLabel.CENTER);
		
		// Wallet A Label
		walletLabelA.setLayout(null);
		
		transfer.setBounds(228, 292, 153, 45);
		balance.setBounds(132, 131, 200, 35);
		amount.setBounds(132, 224, 200, 35);
		preview.setBounds(229, 344, 44, 53);
		receive.setBounds(283, 344, 44, 53);
		
		walletLabelA.add(transfer);
		walletLabelA.add(balance);
		walletLabelA.add(amount);
		walletLabelA.add(preview);
		walletLabelA.add(receive);
		
		Color customColor = new Color(58, 217, 255);
		
		// top Panel
		topPanel.setLayout(new GridLayout(1,2));
		topPanel.setBackground(customColor);
		topPanel.add(walletLabelA);
		topPanel.add(walletLabelB);
				
		// bottom Panel
		bottomPanel.setBackground(Color.black);
		

		//frame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(850, 650));
		setResizable(false);
		setTitle("Blockchain Transaction");
		getContentPane().setLayout(new GridLayout());
		getContentPane().add(splitPane);
		
		// splitPane
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(420);
		splitPane.setTopComponent(topPanel);
		splitPane.setBottomComponent(bottomPanel);
		
		// frame final
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
//	public void scaledImage() {
//		img = new ImageIcon(getClass().getResource("/resources/WalletA.jpg"));
//		Image icon = img.getImage();
//		Image imgScale = icon.getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
//		ImageIcon scaledIcon = new ImageIcon(imgScale);
//		imgLabel.setIcon(scaledIcon);
//	}

}

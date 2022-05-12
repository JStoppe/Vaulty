package de.muc.vaulty.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.beans.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import de.muc.vaulty.Miner;
import de.muc.vaulty.StringUtil;
import de.muc.vaulty.VaultyChain;
import de.muc.vaulty.Wallet;

	public class View {
	   private Control control;
	   private JTextField stateField = new JTextField(10);
	   private JPanel mainPanel = new JPanel();
	   private JPanel topPanel;
	   private JPanel bottomPanel;
	   public static JTextField amountA,feeA,amountB,feeB;
	   public static JLabel walletNameA, balanceA, totalA, walletNameB, balanceB, totalB;
	   public static JTextArea  bcDiscplay, minerDisplay;
	   public JScrollPane blockchainOut, minerOut;
	   
	   public View(Model model) {
	      // add a property change listener to the model to listen and 
	      // respond to changes in the model's state
	      model.addPropertyChangeListener(new PropertyChangeListener() {
	         public void propertyChange(PropertyChangeEvent evt) {
	            // if the state change is the one we're interested in...
	            if (evt.getPropertyName().equals(Model.STATE_PROP_NAME)) {
	               stateField.setText(evt.getNewValue().toString()); // show it in the GUI
	            }
	         }
	      });
	      
	      // hovered Buttons
	      JButton transferA = hoveredButton("/resources/TransferA.png","/resources/TransferAHover.png", 317, 46);
	      JButton transferB = hoveredButton("/resources/TransferB.png", "/resources/TransferBHover.png", 317, 46);
	      JButton qrA = hoveredButton("/resources/QR-A.png", "/resources/QR-AHover.png", 22, 22);
	      JButton qrB = hoveredButton("/resources/QR-B.png", "/resources/QR-BHover.png", 22, 22);
	      JButton changeWalletA = hoveredButton("/resources/ChangeA.png", "/resources/ChangeAHover.png", 75, 43);
	      JButton changeWalletB = hoveredButton("/resources/ChangeB.png", "/resources/ChangeBHover.png", 75, 43);
	      JButton recipientA = hoveredButton("/resources/ReceiverA.png", "/resources/ReceiverAHover.png", 74, 41);
	      JButton recipientB = hoveredButton("/resources/ReceiverB.png", "/resources/ReceiverBHover.png", 74, 41);
	      
	      // textFields amount & fee
	      amountA = textField();
	      amountB = textField();
	      feeA = textField();
	      feeB = textField();
	      
	      numbersOnly(amountA);
	      numbersOnly(amountB);
	      numbersOnly(feeA);
	      numbersOnly(feeB);
	       
	      // labels balance & total
	      balanceA = label();
	      balanceB = label();
	      totalA = label();
	      totalB = label();
	      
	      // blockchain & miner output
	      JTextArea bcDisplay = new JTextArea();
	      JTextArea minerDisplay = new JTextArea();

	      
//	      String usr = String.valueOf(walletNameB);
	      
//	      while(usernameWB.equals(walletNameA.getText())) {
//	    	  String usr =
//	    	  Wallet.username.getText();
//	      }
//	      "Dexter".equals(name.getText())

	      
	      
	      	      
//	      totalA.setText("100");
	      
	      
	      transferA.addActionListener(new ActionListener() {
	         // all the buttons do is call methods of the control
	         public void actionPerformed(ActionEvent e) {
	            if (control != null) {
	               control.transferVaultyA(e);
	            }
	         }
	      });
	      transferB.addActionListener(new ActionListener() {
		         // all the buttons do is call methods of the control
		         public void actionPerformed(ActionEvent e) {
		            if (control != null) {
		               control.transferVaultyB(e);
		            }
		         }
		      });

	      changeWalletA.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            if (control != null) {
	               control.changeWallet(e); // e.g., and here
	            }
	         }
	      });
	      changeWalletB.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		            if (control != null) {
		               control.changeWalletB(e); // e.g., and here
		            }
		         }
		      });
	      
	      recipientA.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent e) {
		            if (control != null) {
		               control.selectRecipient(e); // e.g., and here
		            }
		         }
		      });
		  recipientB.addActionListener(new ActionListener() {
			     public void actionPerformed(ActionEvent e) {
			        if (control != null) {
			           control.selectRecipient(e); // e.g., and here
		            }
			     }
			  });
		  
		  qrA.addActionListener(new ActionListener() {
			     public void actionPerformed(ActionEvent e) {
				        if (control != null) {
				           control.showWalletInfo(e); // e.g., and here
			            }
				     }
				  });
		  qrB.addActionListener(new ActionListener() {
			     public void actionPerformed(ActionEvent e) {
				        if (control != null) {
				           control.showWalletInfo(e); // e.g., and here
			            }
				     }
				  });

	      // make our GUI pretty
	      	      
	      // walletName
			
		  walletNameA = new JLabel("Wallet A");
		  walletNameA.setFont(new Font("Avenir Next", Font.PLAIN, 24));
		  walletNameA.setForeground(Color.LIGHT_GRAY);
		  walletNameA.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
		  walletNameB = new JLabel("Wallet B");
		  walletNameB.setFont(new Font("Avenir Next", Font.PLAIN, 24));
		  walletNameB.setForeground(Color.LIGHT_GRAY);
		  walletNameB.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		  

		  
		  // Bounds
		  walletNameA.setBounds(61, 55, 400, 45);
		  balanceA.setBounds(141, 117, 200, 35);
		  amountA.setBounds(143, 178, 200, 35);
		  feeA.setBounds(143, 232, 200, 35);
		  totalA.setBounds(141, 285, 200, 35);
		  recipientA.setBounds(50, 284, 74, 41);
		  transferA.setBounds(50, 334, 317, 46);
		  qrA.setBounds(261, 69, 22, 22);
		  changeWalletA.setBounds(289, 63, 75, 43);
		  
		  walletNameB.setBounds(61, 55, 400, 45);
		  balanceB.setBounds(141, 117, 200, 35);
		  amountB.setBounds(143, 178, 200, 35);
		  feeB.setBounds(143, 232, 200, 35);
		  totalB.setBounds(141, 285, 200, 35);
		  recipientB.setBounds(50, 284, 74, 41);
		  transferB.setBounds(50, 334, 317, 46);
		  qrB.setBounds(261, 69, 22, 22);
		  changeWalletB.setBounds(289, 63, 75, 43);
	      
	      // wallet-labels
	      JLabel walletLabelA = new JLabel();
	      JLabel walletLabelB = new JLabel();
	      
	      walletLabelA.setIcon(scaledButtonImage("/resources/WalletA-short.png", 425, 416));
	      walletLabelA.setHorizontalAlignment(JLabel.CENTER);
			
	      walletLabelB.setIcon(scaledButtonImage("/resources/WalletB-short.png", 425, 416));
	      walletLabelB.setHorizontalAlignment(JLabel.CENTER);
	      
	      walletLabelA.add(walletNameA);
	      walletLabelA.add(transferA);
	      walletLabelA.add(balanceA);
	      walletLabelA.add(amountA);
	      walletLabelA.add(feeA);
	      walletLabelA.add(totalA);
	      walletLabelA.add(qrA);
	      walletLabelA.add(changeWalletA);
	      walletLabelA.add(recipientA);
	      
	      walletLabelB.add(walletNameB);
	      walletLabelB.add(transferB);
	      walletLabelB.add(balanceB);
	      walletLabelB.add(amountB);
	      walletLabelB.add(feeB);
	      walletLabelB.add(totalB);
	      walletLabelB.add(qrB);
	      walletLabelB.add(changeWalletB);
	      walletLabelB.add(recipientB);
	      
	      // topPanel
	      
	      JPanel topPanel = new JPanel();
	      Color customColor = new Color(1, 162, 255);
	      topPanel.setPreferredSize(new Dimension(850, 416));
	      topPanel.setLayout(new GridLayout(1,2));
	      topPanel.setBackground(customColor);
	      topPanel.setSize(850, 425);
	      topPanel.add(walletLabelA);
	      topPanel.add(walletLabelB);
	      
	      // bottomPanel
	      
	      JPanel bottomPanel = new JPanel();
	      bottomPanel.setBackground(customColor);
			
	      JScrollPane blockchain = new JScrollPane();
	      JScrollPane miner = new JScrollPane();
			
	      blockchain.getViewport().setBackground(Color.black);
	      miner.getViewport().setBackground(Color.black);
	      blockchain.setPreferredSize(new Dimension(850, 275));
	      miner.setPreferredSize(new Dimension(850, 275));
			
	      JTabbedPane tabs = new JTabbedPane();
	      tabs.setPreferredSize(new Dimension(850, 275));
	      tabs.add("Blockchain", blockchain);
		  tabs.add("Miner-Activity", miner);
		  
		  bottomPanel.add(tabs, BorderLayout.NORTH);

		  amountA.getDocument().addDocumentListener(new DocListener(this));
		  feeA.getDocument().addDocumentListener(new DocListener(this));
		  
	      
	      // mainPanel
	      
	      mainPanel.setLayout(new BorderLayout());
	      mainPanel.add(topPanel, BorderLayout.NORTH);
	      mainPanel.add(bottomPanel, BorderLayout.CENTER);
	      
	   }

	   // set the control for this view
	   public void setGuiControl(Control control) {
	      this.control = control;
	   }

	   // get the main gui and its components for display
	   public JComponent getMainPanel() {
	      return mainPanel;
	   }
	   
	   public ImageIcon scaledButtonImage(String resource, int x, int y) {
			
			ImageIcon img = new ImageIcon(getClass().getResource(resource));
			Image image = img.getImage(); // transform it 
			Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			img = new ImageIcon(newimg);  // transform it back
					
			return img;
		}
	   
	   public JButton hoveredButton(String path, String hover, int w, int h) {
		   JButton button  = new JButton(scaledButtonImage(path, w, h));
		   Icon buttonHover = scaledButtonImage(hover, w, h);
		   button.setRolloverIcon(buttonHover);
		   button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		   button.setContentAreaFilled(false);
		   return button;
	   }
	   
	   public JTextField textField() {
		   JTextField text = new JTextField();
		   text.setFont(new Font("Avenir Next", Font.PLAIN, 24));
		   text.setForeground(Color.LIGHT_GRAY);
		   text.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		   text.setOpaque(false);
		   text.setBorder(null);
		   return text;
	   }
	   
	   public JTextField numbersOnly(JTextField text) {
		   text.addKeyListener(new KeyAdapter() {
			    public void keyTyped(KeyEvent e) {
			        char c = e.getKeyChar();
			        if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE && c != ',' && c != '.')) {
			            e.consume();  // if it's not a number, ignore the event
			        }
			     }
			});
		   return text;
	   }
	   
	   public JLabel label() {
		   JLabel label = new JLabel();
		   label.setFont(new Font("Avenir Next", Font.PLAIN, 24));
		   label.setForeground(Color.LIGHT_GRAY);
		   label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		   return label;
	   }
	   
//	   public String setBalanceA(Wallet w) {
//		   String balance = String.valueOf(VaultyChain.wallets.get(StringUtil.getStringFromKey(w.publicKey)).getBalance()); 
//
//		   return balance;
//	   }
	   
//	   private void updateTextArea(final String text) {
//		    SwingUtilities.invokeLater(new Runnable() {
//		      public void run() {
//		    	  bcDisplay.append(text);
//		      }
//		    });
//		  }
//		 
		//Followings are The Methods that do the Redirect, you can simply Ignore them. 
//		  private void redirectSystemStreams() {
//		    OutputStream out = new OutputStream() {
//		      @Override
//		      public void write(int b) throws IOException {
//		        updateTextArea(String.valueOf((char) b));
//		      }
//		 
//		      @Override
//		      public void write(byte[] b, int off, int len) throws IOException {
//		        updateTextArea(new String(b, off, len));
//		      }
//		 
//		      @Override
//		      public void write(byte[] b) throws IOException {
//		        write(b, 0, b.length);
//		      }
//		    };
//	   public BufferedImage scaleIMG(String path, int x, int y) {
//		   BufferedImage img = null;
//		   try {
//			    img = ImageIO.read(new File(path));
////			    BufferedImage after = new BufferedImage(getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH));
//			    BufferedImage after = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
//			    AffineTransform at = new AffineTransform();
//			    at.scale(2.0, 2.0);
//			    AffineTransformOp scaleOp = 
//			       new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//			    after = scaleOp.filter(img, after);
//			} catch (IOException e) {
//			}
//		   return img;
//	   }
	   
//	   public BufferedImage scaleIMG() {
//		   
//		   BufferedImage before = getBufferedImage(String encoded);
//		   int w = before.getWidth();
//		   int h = before.getHeight();
//		   BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//		   AffineTransform at = new AffineTransform();
//		   at.scale(2.0, 2.0);
//		   AffineTransformOp scaleOp = 
//		      new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//		   after = scaleOp.filter(before, after);
//	   }

	}

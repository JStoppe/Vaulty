package de.muc.vaulty.view;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocListener implements DocumentListener{
	
	View v;
	
	public DocListener(View v) {
		this.v = v;
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		calcTotal(e);
	}
	
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		calcTotal(e);
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		calcTotal(e);
	}
	
	public void calcTotal(DocumentEvent e) {
	
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			 @Override
             public void run() {
//				 DecimalFormat numberFormat = new DecimalFormat("#.00");
				 double amount = 0;
				 double fee = 0;
				 try {
					 String amSt = v.amountA.getText();
					 String feSt = v.feeA.getText();
					 
					 if(amSt.length() != 0) {
						  amount = Double.parseDouble(amSt);
						 v.totalA.setText(amSt);
					 }
					 if(feSt.length() != 0) {
						  fee = Double.parseDouble(feSt);
						 v.totalA.setText(feSt);
					 } 
				 } catch (NumberFormatException e){
					 JOptionPane.showMessageDialog(null, e, null, 0);
				 }
				 double txTotal = amount + fee;
				 String total = Double.toString(txTotal);
//				 String total = String.valueOf(numberFormat.format(txTotal));
				 
				 v.totalA.setText(total);
				    
			 }
		});
	}
}



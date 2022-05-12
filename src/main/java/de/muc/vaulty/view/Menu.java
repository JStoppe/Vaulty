package de.muc.vaulty.view;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class Menu {
   private JMenuBar menuBar = new JMenuBar();
   private Control control;

   @SuppressWarnings("serial")
   public Menu(Control cntrl) {
      this.control = cntrl;

      JMenu menu = new JMenu("Start");
      menu.add(new JMenuItem(new AbstractAction("Add Wallet") {
         public void actionPerformed(ActionEvent ae) {
            if (control != null) {
               control.addWallet(ae);
            }
         }
      }));
      menu.add(new JMenuItem(new AbstractAction("Add Miner") {
         public void actionPerformed(ActionEvent ae) {
            if (control != null) {
               control.addMiner(ae);
            }
         }
      }));
      menu.add(new JMenuItem(new AbstractAction("Add FullNode") {
          public void actionPerformed(ActionEvent ae) {
             if (control != null) {
                control.addFullNode(ae);
             }
          }
       }));

      menuBar.add(menu);
   }

   public JMenuBar getMenuBar() {
      return menuBar;
   }
}

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class MainPanel extends JPanel{
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    JTextArea log;
    GuiController contr;
    
    public MainPanel(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = null;
        try{
            lbl = new JLabel("<html> " + contr.getMarketName() + "<br>"
                    + "User: " + contr.getClient().getName() + "</html>");
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
        lbl.setFont(Title);
        add(lbl, "span 2");
        JButton deRegButton = new JButton("DeRegister");
        deRegButton.setFont(Title);
        deRegButton.addActionListener(contr.new DeRegisterListener());
        add(deRegButton, "span 2, gaptop 15");
        lbl = new JLabel("Your notifications and history");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        log = new JTextArea("");
        log.setLineWrap(true);
        log.setEditable(false);
        log.setFont(Plain);
        JScrollPane logPane = new JScrollPane(log);
        logPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logPane.setPreferredSize(new Dimension(400, 250));
        add(logPane, "span 2, gaptop 20");
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    log.setText("");
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        clearButton.setFont(Plain);
        add(clearButton, "span 2, gaptop 10");
        updateLog();
    }
    private void updateLog(){
       for(String s : contr.getLog()){
           log.setText(log.getText() + s + "\n");
       }
    }
}
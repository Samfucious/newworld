/*
 * Copyright (C) 2020 samfucious
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class StartupSelector extends JFrame {
    public void start() {
        this.setTitle("Game Launcher");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbcHeader = new GridBagConstraints();
        gbcHeader.gridwidth = GridBagConstraints.REMAINDER;
        gbcHeader.anchor = GridBagConstraints.NORTH;

        panel.add(new JLabel("<html><h1><strong><i>Game Launcher</i></strong></h1><hr></html>"), gbcHeader);

        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridwidth = GridBagConstraints.REMAINDER;
        gbcButtons.anchor = GridBagConstraints.CENTER;
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;
        gbcButtons.insets = new Insets(25, 25, 25, 25);

        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.add(newButton("Start Game", new StartSelectionActionListener(this)), gbcButtons);
        buttons.add(newButton("Connect To Server", new ClientSelectionActionListener(this)), gbcButtons);
        buttons.add(newButton("Launch Headless Server", new ServerSelectionActionListener(this)), gbcButtons);

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.gridwidth = GridBagConstraints.REMAINDER;
        gbcPanel.weighty = 1;
        panel.add(buttons, gbcPanel);
        
        GridBagConstraints gbcFooter = new GridBagConstraints();
        gbcFooter.gridwidth = GridBagConstraints.REMAINDER;
        gbcFooter.anchor = GridBagConstraints.SOUTH;
        panel.add(new JLabel("<html>Game Launcher</html>"), gbcFooter);

        this.getContentPane().add(panel);
        
        showAndWait(this);
        this.setVisible(false);
    }
    
    private synchronized void showAndWait(JFrame frame) {
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        try {
            this.wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(StartupSelector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private JButton newButton(String buttonText, ActionListener listener) {
        JButton button = new JButton(buttonText);
        button.addActionListener(listener);
        return button;
    }
    
    abstract class BaseActionListener implements ActionListener {
        final StartupSelector selector;
        
        public BaseActionListener(StartupSelector selector) {
            this.selector = selector;
        }
        
        protected void notifySelector() {
            synchronized(selector) {
                selector.notify();
            }
        }
    }
    
    class StartSelectionActionListener extends BaseActionListener {
        public StartSelectionActionListener(StartupSelector selector) {
            super(selector);
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            notifySelector();
        }
    }
    
    class ClientSelectionActionListener extends BaseActionListener {
        public ClientSelectionActionListener(StartupSelector selector) {
            super(selector);
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            String address = (String) JOptionPane.showInputDialog(selector, "Server address and port:", 
                    "Connect to server", JOptionPane.PLAIN_MESSAGE, 
                    null, null, "localhost:" + Configuration.DEFAULT_PORT);
            
            String[] addressAndPort = address.split(":");
            Configuration.setConfigurationValue(Configuration.Configurations.CONNECT, addressAndPort[0]);
            Configuration.setConfigurationValue(Configuration.Configurations.PORT, addressAndPort[1]);
            
            notifySelector();
        }
    }
    
    class ServerSelectionActionListener extends BaseActionListener {
        public ServerSelectionActionListener(StartupSelector selector) {
            super(selector);
        }
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            String port = (String) JOptionPane.showInputDialog(selector, "Listen on port:", 
                    "Start server", JOptionPane.PLAIN_MESSAGE, 
                    null, null, Configuration.DEFAULT_PORT);
            
            Configuration.setConfigurationValue(Configuration.Configurations.SERVER, "true");
            Configuration.setConfigurationValue(Configuration.Configurations.PORT, port);
            
            notifySelector();
        }
    }
}

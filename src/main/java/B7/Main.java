package B7;

import B7.swing.LoginFrame;

import javax.swing.*;

public class Main {
    void main(){
        SwingUtilities.invokeLater(() ->{
            new LoginFrame().setVisible(true);
        });
    }
}

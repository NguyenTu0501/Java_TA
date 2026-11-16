package B7.swing;

import B7.dao.AuthDAO;
import B7.entity.Account;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnGoRegister;

    public LoginFrame() {
        setTitle("Login");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 18));
        btnLogin.setPreferredSize(new Dimension(130, 40));

        btnGoRegister = new JButton("Register");
        btnGoRegister.setFont(new Font("Arial", Font.PLAIN, 18));
        btnGoRegister.setPreferredSize(new Dimension(130, 40));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnGoRegister);

        add(buttonPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> handleLogin());
        btnGoRegister.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });

        getRootPane().setDefaultButton(btnLogin);
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter username and password!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Account account = AuthDAO.login(username, password);

        if (account == null) {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Welcome, " + account.getFirstName() + " " + account.getLastName() + "!",
                    "Login Success", JOptionPane.INFORMATION_MESSAGE);
            new AdminFrame().setVisible(true);
            dispose();
        }
    }
}
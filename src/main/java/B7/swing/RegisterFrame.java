package B7.swing;

import B7.dao.AuthDAO;
import B7.entity.Account;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JButton btnRegister;
    private JButton btnGoLogin;

    public RegisterFrame() {
        setTitle("Register");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("REGISTER", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setFont(new Font("Arial", Font.PLAIN, 18));
        txtFirstName = new JTextField();
        txtFirstName.setFont(new Font("Arial", Font.PLAIN, 18));

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setFont(new Font("Arial", Font.PLAIN, 18));
        txtLastName = new JTextField();
        txtLastName.setFont(new Font("Arial", Font.PLAIN, 18));

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);
        formPanel.add(lblFirstName);
        formPanel.add(txtFirstName);
        formPanel.add(lblLastName);
        formPanel.add(txtLastName);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

        btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 18));
        btnRegister.setPreferredSize(new Dimension(130, 40));

        btnGoLogin = new JButton("Back to Login");
        btnGoLogin.setFont(new Font("Arial", Font.PLAIN, 18));
        btnGoLogin.setPreferredSize(new Dimension(160, 40));

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnGoLogin);

        add(buttonPanel, BorderLayout.SOUTH);

        btnRegister.addActionListener(e -> handleRegister());
        btnGoLogin.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        getRootPane().setDefaultButton(btnRegister);
    }

    private void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();

        if (username.isEmpty() || password.isEmpty()
                || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Account account = new Account(0, username, password, firstName, lastName);

        if (AuthDAO.register(account)) {
            JOptionPane.showMessageDialog(this,
                    "Register successful! Please login.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Register failed! Username may already exist.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
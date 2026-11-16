package B6.swing_ui;

import B6.dao.AccountDAOImpl;
import B6.entity.Account;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class AuthFormApp extends JFrame {
    private final Font font = new Font("Arial", Font.BOLD, 16);
    private final Color color = Color.RED;
    private final Random random = new Random();
    private final AccountDAOImpl accountDAO = new AccountDAOImpl();

    public AuthFormApp() {
        this.setTitle("Hệ Thống Xác Thực");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Đăng Nhập", createLoginPanel());
        tabbedPane.addTab("Đăng Ký", createRegisterPanel());

        add(tabbedPane);
    }

    private JPanel createLoginPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial",Font.BOLD,18));
        lblTitle.setForeground(new Color(200,26,200));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));

        formPanel.add(new JLabel("Tên đăng nhập:"));
        JTextField txtUsername = new JTextField();
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Mật khẩu:"));
        JPasswordField txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnLogin = new JButton("Đăng Nhập");
        buttonPanel.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());

            if(username.isBlank() || password.isBlank()){
                JOptionPane.showMessageDialog(this,
                        "Please enter username or password",
                        "Warning",JOptionPane.WARNING_MESSAGE);
                return;
            }

            Account account = accountDAO.login(username,password);
            if(account == null){
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password",
                        "Warning",JOptionPane.WARNING_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,
                        account,
                        "Account info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createRegisterPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("TẠO TÀI KHOẢN MỚI", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Tên đăng nhập:"));
        JTextField txtRegUsername = new JTextField();
        formPanel.add(txtRegUsername);

        formPanel.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Mật khẩu:"));
        JPasswordField txtRegPassword = new JPasswordField();
        formPanel.add(txtRegPassword);

        formPanel.add(new JLabel("Xác nhận mật khẩu:"));
        JPasswordField txtConfirmPassword = new JPasswordField();
        formPanel.add(txtConfirmPassword);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRegister = new JButton("Đăng Ký");
        buttonPanel.add(btnRegister);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    public static void main(String[] args) {
        // Chạy chương trình trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new AuthFormApp().setVisible(true);
        });
    }
}
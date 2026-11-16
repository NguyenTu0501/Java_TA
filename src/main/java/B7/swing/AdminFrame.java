package B7.swing;

import B7.dao.AccountDAO;
import B7.dao.AuthDAO;
import B7.entity.Account;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class AdminFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnLogout;

    public AdminFrame() {
        setTitle("Admin Panel - Account Management");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("ACCOUNT MANAGEMENT", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnAdd = createButton("Add");
        btnEdit = createButton("Edit");
        btnDelete = createButton("Delete");
        btnRefresh = createButton("Refresh");
        btnLogout = createButton("Logout");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnLogout);

        topPanel.add(buttonPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Username", "First Name", "Last Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> handleAdd());
        btnEdit.addActionListener(e -> handleEdit());
        btnDelete.addActionListener(e -> handleDelete());
        btnRefresh.addActionListener(e -> loadData());
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        loadData();
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(110, 40));
        return btn;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        ArrayList<Account> accounts = AccountDAO.getAllAccounts();
        for (Account acc : accounts) {
            tableModel.addRow(new Object[]{
                    acc.getAccountId(),
                    acc.getUsername(),
                    acc.getFirstName(),
                    acc.getLastName()
            });
        }
    }

    private void handleAdd() {
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JTextField txtFirstName = new JTextField();
        JTextField txtLastName = new JTextField();

        Object[] message = {
                "Username:", txtUsername,
                "Password:", txtPassword,
                "First Name:", txtFirstName,
                "Last Name:", txtLastName
        };

        int option = JOptionPane.showConfirmDialog(this, message,
                "Add Account", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();

        if (username.isEmpty() || password.isEmpty()
                || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Account newAcc = new Account(0, username, password, firstName, lastName);
        boolean ok = AuthDAO.register(newAcc);

        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Account added successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Add failed! Username may already exist.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleEdit() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an account to edit!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String username = (String) tableModel.getValueAt(row, 1);
        String firstName = (String) tableModel.getValueAt(row, 2);
        String lastName = (String) tableModel.getValueAt(row, 3);

        JTextField txtUsername = new JTextField(username);
        JPasswordField txtPassword = new JPasswordField();
        JTextField txtFirstName = new JTextField(firstName);
        JTextField txtLastName = new JTextField(lastName);

        Object[] message = {
                "ID: " + id,
                "Username:", txtUsername,
                "Password (leave blank to keep):", txtPassword,
                "First Name:", txtFirstName,
                "Last Name:", txtLastName
        };

        int option = JOptionPane.showConfirmDialog(this, message,
                "Edit Account", JOptionPane.OK_CANCEL_OPTION);
        if (option != JOptionPane.OK_OPTION) return;

        String newUsername = txtUsername.getText().trim();
        String newPassword = new String(txtPassword.getPassword()).trim();
        String newFirstName = txtFirstName.getText().trim();
        String newLastName = txtLastName.getText().trim();

        if (newUsername.isEmpty() || newFirstName.isEmpty() || newLastName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username, First Name, Last Name cannot be empty!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Account updated = new Account(id, newUsername, newPassword,
                newFirstName, newLastName);

        if (AccountDAO.updateAccount(updated)) {
            JOptionPane.showMessageDialog(this,
                    "Account updated successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Update failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select an account to delete!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String username = (String) tableModel.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete account \"" + username + "\" (ID=" + id + ")?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        if (AccountDAO.deleteAccount(id)) {
            JOptionPane.showMessageDialog(this,
                    "Account deleted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadData();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Delete failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
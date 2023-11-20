package org.example.ui;

import org.example.dao.DormitoryDAO;
import org.example.model.Dormitory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DormitoryFrame extends JFrame {
    private DormitoryDAO dormitoryDAO = new DormitoryDAO();
    private JTextField nameTextField;
    private JTextField addressTextField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private int idCounter = 1;

    public DormitoryFrame() {
        // Настройка основного окна
        setTitle("CRUD Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Создание модели таблицы
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Название");
        tableModel.addColumn("Адрес");
        initializeTable();

        // Создание таблицы
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Панель ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel("Название:");
        nameTextField = new JTextField(20);
        JLabel addressLabel = new JLabel("Адрес:");
        addressTextField = new JTextField(20);

        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressTextField);

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        createButton = new JButton("Создать");
        editButton = new JButton("Редактировать");
        deleteButton = new JButton("Удалить");

        buttonPanel.add(createButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Добавление компонентов на основное окно
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Обработка событий кнопок
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String address = addressTextField.getText();

                if (!name.isEmpty() && !address.isEmpty()) {
                    dormitoryDAO.create(new Dormitory(name, address));
                    tableModel.addRow(new Object[]{idCounter, name, address});
                    idCounter++;
                    nameTextField.setText("");
                    addressTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(DormitoryFrame.this, "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String name = nameTextField.getText();
                    String address = addressTextField.getText();

                    if (!name.isEmpty() && !address.isEmpty()) {
                        tableModel.setValueAt(name, selectedRow, 1);
                        tableModel.setValueAt(address, selectedRow, 2);
                        nameTextField.setText("");
                        addressTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(DormitoryFrame.this, "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(DormitoryFrame.this, "Выберите запись для редактирования", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                    nameTextField.setText("");
                    addressTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(DormitoryFrame.this, "Выберите запись для удаления", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void initializeTable() {
        List<Dormitory> dormitoryList = dormitoryDAO.getAll();
        for(Dormitory dormitory: dormitoryList){
            tableModel.addRow(new Object[]{dormitory.getId(), dormitory.getName(), dormitory.getAddress()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DormitoryFrame window = new DormitoryFrame();
                window.setVisible(true);
            }
        });
    }
}
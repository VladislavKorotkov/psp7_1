package org.example.ui;

import org.example.dao.DormitoryDAO;
import org.example.dao.RoomDAO;
import org.example.model.Dormitory;
import org.example.model.Room;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoomsFrame extends JFrame {
    private DormitoryDAO dormitoryDAO;
    private RoomDAO roomDAO;
    private JTextField numberTextField;
    private JTextField floorTextField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JComboBox<String> dormitoryComboBox;

    public RoomsFrame() {
        this.dormitoryDAO = new DormitoryDAO();
        this.roomDAO = new RoomDAO();
        // Настройка основного окна
        setTitle("Комнаты");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Создание модели таблицы
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Номер комнаты");
        tableModel.addColumn("Этаж");
        tableModel.addColumn("Общежитие");
        initializeTable();

        // Создание таблицы
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Панель ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Номер комнаты:");
        numberTextField = new JTextField(20);
        JLabel addressLabel = new JLabel("Этаж:");
        floorTextField = new JTextField(20);

        JLabel dormitoryLabel = new JLabel("Общежитие:");
        dormitoryComboBox = new JComboBox<>();
        List<Dormitory> dormitories = dormitoryDAO.getAll();
        for (Dormitory dormitory : dormitories) {
            dormitoryComboBox.addItem(String.valueOf(dormitory.getName()));
        }

        inputPanel.add(nameLabel);
        inputPanel.add(numberTextField);
        inputPanel.add(addressLabel);
        inputPanel.add(floorTextField);
        inputPanel.add(dormitoryLabel);
        inputPanel.add(dormitoryComboBox);
        inputPanel.add(dormitoryLabel);
        inputPanel.add(dormitoryComboBox);


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
                String number = numberTextField.getText();
                String floor = floorTextField.getText();
                if(dormitoryComboBox.getItemCount()!=0){
                    if (!floor.isEmpty() && !number.isEmpty()&&number.matches("\\d+")&&floor.matches("\\d+")) {
                        Dormitory dormitory = dormitoryDAO.getByName((String) dormitoryComboBox.getSelectedItem());
                        Room room = new Room(Integer.parseInt(number), Integer.parseInt(floor), dormitory);
                        room = roomDAO.create(room);
                        tableModel.addRow(new Object[]{room.getId(), room.getNumber(), room.getFloor(), room.getDormitory().getName()});
                        numberTextField.setText("");
                        floorTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(RoomsFrame.this, "Заполните все поля корректно", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(RoomsFrame.this, "Общежития отсутствуют", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String number = numberTextField.getText();
                    String floor = floorTextField.getText();
                    if (!floor.isEmpty() && !number.isEmpty()&&number.matches("\\d+")&&floor.matches("\\d+")) {
                        Dormitory dormitory = dormitoryDAO.getByName((String) dormitoryComboBox.getSelectedItem());
                        Room room = new Room((Integer) tableModel.getValueAt(selectedRow, 0), Integer.parseInt(number), Integer.parseInt(floor), dormitory );
                        roomDAO.update(room);
                        tableModel.setValueAt(number, selectedRow, 1);
                        tableModel.setValueAt(floor, selectedRow, 2);
                        tableModel.setValueAt(dormitory.getName(), selectedRow, 3);
                        numberTextField.setText("");
                        floorTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(RoomsFrame.this, "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(RoomsFrame.this, "Выберите запись для редактирования", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    Dormitory dormitory = dormitoryDAO.getByName((String) dormitoryComboBox.getSelectedItem());
                    roomDAO.delete(new Room((Integer) tableModel.getValueAt(selectedRow, 0), (Integer) tableModel.getValueAt(selectedRow, 1), (Integer) tableModel.getValueAt(selectedRow, 2), dormitory));
                    tableModel.removeRow(selectedRow);
                    numberTextField.setText("");
                    floorTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(RoomsFrame.this, "Выберите запись для удаления", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    Integer number = (Integer) tableModel.getValueAt(selectedRow, 1);
                    Integer floor = (Integer) tableModel.getValueAt(selectedRow, 2);

                    numberTextField.setText(String.valueOf(number));
                    floorTextField.setText(String.valueOf(floor));
                }
            }
        });
    }

    private void initializeTable() {
        List<Room> roomList = roomDAO.getAll();
        for(Room room: roomList){
            tableModel.addRow(new Object[]{room.getId(), room.getNumber(), room.getFloor(), room.getDormitory().getName()});
        }
    }
}

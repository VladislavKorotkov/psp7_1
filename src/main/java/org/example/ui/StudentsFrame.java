package org.example.ui;

import org.example.dao.RoomDAO;
import org.example.dao.StudentsDAO;
import org.example.model.Dormitory;
import org.example.model.Room;
import org.example.model.Student;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentsFrame extends JFrame {
    private RoomDAO roomDAO;
    private StudentsDAO studentsDAO;
    private JTextField firstnameTextField;
    private JTextField lastnameTextField;
    private JTextField patronymicTextField;
    private JTextField groupTextField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JComboBox<String> roomComboBox;

    public StudentsFrame() {
        this.roomDAO = new RoomDAO();
        this.studentsDAO = new StudentsDAO();
        // Настройка основного окна
        setTitle("Студенты");
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Создание модели таблицы
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Имя");
        tableModel.addColumn("Фамилия");
        tableModel.addColumn("Отчество");
        tableModel.addColumn("Группа");
        tableModel.addColumn("Комната");
        initializeTable();

        // Создание таблицы
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Панель ввода
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        JLabel firstnameLabel = new JLabel("Имя:");
        firstnameTextField = new JTextField(20);
        JLabel lastnameLabel = new JLabel("Фамилия:");
        lastnameTextField = new JTextField(20);
        JLabel patronymicLabel = new JLabel("Отчество:");
        patronymicTextField = new JTextField(20);
        JLabel groupLabel = new JLabel("Номер группы:");
        groupTextField = new JTextField(20);

        JLabel roomLabel = new JLabel("Комната:");
        roomComboBox = new JComboBox<>();
        List<Room> rooms = roomDAO.getAll();
        for (Room room: rooms) {
            roomComboBox.addItem(String.valueOf(room.getNumber()));
        }

        inputPanel.add(firstnameLabel);
        inputPanel.add(firstnameTextField);
        inputPanel.add(lastnameLabel);
        inputPanel.add(lastnameTextField);
        inputPanel.add(patronymicLabel);
        inputPanel.add(patronymicTextField);
        inputPanel.add(groupLabel);
        inputPanel.add(groupTextField);
        inputPanel.add(roomLabel);
        inputPanel.add(roomComboBox);


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
                String firstname = firstnameTextField.getText();
                String lastname = lastnameTextField.getText();
                String patr = patronymicTextField.getText();
                String group = groupTextField.getText();
                if(roomComboBox.getItemCount()!=0){
                    if (!lastname.isEmpty() && !firstname.isEmpty()&&!patr.isEmpty() && !group.isEmpty()) {
                        Room room = roomDAO.getByNumber(Integer.parseInt((String) roomComboBox.getSelectedItem()));
                        Student student = new Student((String) firstname, (String) lastname, (String) patr, (String) group, room);
                        student = studentsDAO.create(student);
                        tableModel.addRow(new Object[]{student.getId(), student.getFirstname(), student.getLastname(), student.getPatronymic(), student.getGroup(), student.getRoom().getNumber()});
                        firstnameTextField.setText("");
                        lastnameTextField.setText("");
                        patronymicTextField.setText("");
                        groupTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(StudentsFrame.this, "Заполните все поля корректно", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(StudentsFrame.this, "Комнаты отсутствуют", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String firstname = firstnameTextField.getText();
                    String lastname = lastnameTextField.getText();
                    String patr = patronymicTextField.getText();
                    String group = groupTextField.getText();
                    if (roomComboBox.getItemCount() != 0) {
                        if (!lastname.isEmpty() && !firstname.isEmpty() && !patr.isEmpty() && !group.isEmpty()) {
                            Room room = roomDAO.getByNumber(Integer.parseInt((String) roomComboBox.getSelectedItem()));
                            Student student = new Student((Integer) tableModel.getValueAt(selectedRow, 0),(String) firstname, (String) lastname, (String) patr, (String) group, room);
                            student = studentsDAO.update(student);
                            firstnameTextField.setText("");
                            lastnameTextField.setText("");
                            patronymicTextField.setText("");
                            groupTextField.setText("");
                            tableModel.setValueAt(firstname, selectedRow, 1);
                            tableModel.setValueAt(lastname, selectedRow, 2);
                            tableModel.setValueAt(patr, selectedRow, 3);
                            tableModel.setValueAt(group, selectedRow, 4);
                            tableModel.setValueAt(student.getRoom().getNumber(), selectedRow, 5);
                        } else {
                            JOptionPane.showMessageDialog(StudentsFrame.this, "Заполните все поля корректно", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(StudentsFrame.this, "Комнаты отсутствуют", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {

                    Room room = roomDAO.getByNumber(((Integer) tableModel.getValueAt(selectedRow, 5)));
                    Student student = new Student((Integer) tableModel.getValueAt(selectedRow, 0), (String) tableModel.getValueAt(selectedRow, 1), (String) tableModel.getValueAt(selectedRow, 2), (String) tableModel.getValueAt(selectedRow, 3),(String) tableModel.getValueAt(selectedRow, 4), room);
                    studentsDAO.delete(student);
                    tableModel.removeRow(selectedRow);
                    firstnameTextField.setText("");
                    lastnameTextField.setText("");
                } else {
                    JOptionPane.showMessageDialog(StudentsFrame.this, "Выберите запись для удаления", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String firstname = (String) tableModel.getValueAt(selectedRow, 1);
                    String lastname = (String) tableModel.getValueAt(selectedRow, 2);
                    String patr = (String) tableModel.getValueAt(selectedRow, 3);
                    String group = (String) tableModel.getValueAt(selectedRow, 4);
                    int number = (Integer) tableModel.getValueAt(selectedRow, 5);

                    firstnameTextField.setText(firstname);
                    lastnameTextField.setText(lastname);
                    patronymicTextField.setText(patr);
                    groupTextField.setText(group);
                }
            }
        });
    }

    private void initializeTable() {
        List<Student> studentList = studentsDAO.getAll();
        for(Student student: studentList){
            tableModel.addRow(new Object[]{student.getId(), student.getFirstname(), student.getLastname(), student.getPatronymic(), student.getGroup(), student.getRoom().getNumber()});
        }
    }
}

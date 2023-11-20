package org.example;
import org.example.dao.DormitoryDAO;
import org.example.ui.DormitoryFrame;
import org.example.ui.RoomsFrame;
import org.example.ui.StudentsFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    private DormitoryDAO dormitoryDAO = new DormitoryDAO();
    private JPanel mainPanel;
    private JButton studentsButton;
    private JButton roomsButton;
    private JButton dormitoriesButton;

    public MainApp() {
        setTitle("Выбор экрана");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Центрирование окна

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        studentsButton = new JButton("Работа со студентами");
        roomsButton = new JButton("Работа с комнатами");
        dormitoriesButton = new JButton("Работа с общежитиями");

        mainPanel.add(studentsButton);
        mainPanel.add(roomsButton);
        mainPanel.add(dormitoriesButton);

        add(mainPanel);

        studentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStudentsScreen();
            }
        });

        roomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRoomsScreen();
            }
        });

        dormitoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDormitoriesScreen();
            }
        });
    }

    private void openStudentsScreen() {
        StudentsFrame window = new StudentsFrame();
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

    private void openRoomsScreen() {
        RoomsFrame window = new RoomsFrame();
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

    private void openDormitoriesScreen() {
        DormitoryFrame window = new DormitoryFrame();
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainApp app = new MainApp();
                app.setVisible(true);
            }
        });
    }
}

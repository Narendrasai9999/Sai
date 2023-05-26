import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentAttendancePortal extends JFrame {
    private JTextField studentNameField;
    private JButton submitButton;
    private JTextArea attendanceTextArea;
    private Connection connection;
    private Statement statement;

    public StudentAttendancePortal() {
        setTitle("Student Attendance Portal");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel("Student Name:");
        studentNameField = new JTextField();
        JLabel emptyLabel = new JLabel();
        submitButton = new JButton("Submit");

        inputPanel.add(nameLabel);
        inputPanel.add(studentNameField);
        inputPanel.add(emptyLabel);
        inputPanel.add(submitButton);

        attendanceTextArea = new JTextArea();
        attendanceTextArea.setEditable(false);

        add(inputPanel, BorderLayout.NORTH);
        add(attendanceTextArea, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentName = studentNameField.getText();
                String attendanceRecord = "Name: " + studentName + "\n";
                attendanceTextArea.append(attendanceRecord);
                studentNameField.setText("");

                try {
                    // Establish a connection to the database
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "username", "password");
                    statement = connection.createStatement();

                    // Construct the SQL query to insert attendance details
                    String sql = "INSERT INTO attendance (student_name, date) VALUES ('" + studentName + "', CURDATE())";
                    statement.executeUpdate(sql);

                    // Close the database resources
                    statement.close();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StudentAttendancePortal portal = new StudentAttendancePortal();
                portal.setVisible(true);
            }
        });
    }
}
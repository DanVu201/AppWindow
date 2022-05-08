package com.danvu.appwindow;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Form {
    private JTextField idInput;
    private JTextField nameInput;
    private JTextField phoneInput;
    private JTextField addressInput;
    private JButton addButton;
    private JButton resetButton;
    private JPanel buttonPanel;
    private JPanel InfoPanel;
    private JTable table;
    private JPanel tablePanel;
    private JPanel panelMain;
    private ButtonGroup sexButton;
    private JDateChooser dateChoose;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private JButton deleteButton;
    private List<Student> studentList = new ArrayList<>();
    private DefaultTableModel model;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Form() {
        createGroupButton();
        createTable();
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nhập Id");
                    return;
                }
                if (!isNumeric(idInput.getText())) {
                    JOptionPane.showMessageDialog(null, "Id chỉ chứa số");
                    return;
                }
                if (nameInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nhập Tên");
                    return;
                }
                try {
                    sexButton.getSelection().getActionCommand();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Chọn giới tính");
                    return;
                }
                try {
                    dateFormat.format(dateChoose.getDate());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Chọn ngày sinh");
                    return;
                }
                if (phoneInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nhập số điện thoại");
                    return;
                }
                if (!isNumeric(phoneInput.getText())) {
                    JOptionPane.showMessageDialog(null, "Số điện thoại không được chứa chữ");
                    return;
                }
                if (addressInput.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nhập địa chỉ");
                    return;
                }
                Student student = Student.builder()
                        .Id(Integer.parseInt(idInput.getText()))
                        .Name(nameInput.getText())
                        .Sex(sexButton.getSelection().getActionCommand())
                        .BirthDay(dateFormat.format(dateChoose.getDate()))
                        .Phone(phoneInput.getText())
                        .Address(addressInput.getText())
                        .build();
                studentList.add(student);
                studentList.sort((o1, o2) -> {
                    return o1.getId().compareTo(o2.getId());
                });
                model.getDataVector().removeAllElements();
                for (Student s : studentList) {
                    model.addRow(new Object[]{s.getId(), s.getName(), s.getSex(),
                            s.getBirthDay(), s.getPhone(), s.getAddress()});
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                Student student = student = Student.builder()
                        .Id(Integer.valueOf(table.getModel().getValueAt(row, 0).toString()))
                        .Name(table.getModel().getValueAt(row, 1).toString())
                        .Sex(table.getModel().getValueAt(row, 2).toString())
                        .BirthDay(table.getModel().getValueAt(row, 3).toString())
                        .Phone(table.getModel().getValueAt(row, 4).toString())
                        .Address(table.getModel().getValueAt(row, 5).toString()).build();
                studentList.remove(student);
                model.getDataVector().removeAllElements();
                if (studentList.isEmpty()) {
                    model.addRow((Vector<?>) null);
                }
                for (Student s : studentList) {
                    model.addRow(new Object[]{s.getId(), s.getName(), s.getSex(),
                            s.getBirthDay(), s.getPhone(), s.getAddress()});
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getDataVector().removeAllElements();
                model.addRow((Vector<?>) null);
            }
        });
    }

    private void createGroupButton() {
        sexButton = new ButtonGroup();
        maleButton.setActionCommand("Male");
        femaleButton.setActionCommand("Female");
        sexButton.add(maleButton);
        sexButton.add(femaleButton);
    }


    private void createTable() {
        model = new DefaultTableModel(null, new String[]{"Id", "Name", "Sex", "Birthday", "Phone", "Address"});
        table.setModel(model);
        TableColumnModel columns = table.getColumnModel();
        columns.getColumn(0).setMaxWidth(55);
        columns.getColumn(1).setMaxWidth(120);
        columns.getColumn(2).setMaxWidth(50);

    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Danh sách sinh viên");
        jFrame.setContentPane(new Form().panelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void createUIComponents() {
        dateChoose = new JDateChooser();
        dateChoose.setDateFormatString("dd/MM/yyyy");
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

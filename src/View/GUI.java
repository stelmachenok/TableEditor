package View;

import Controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by y50-70 on 22.04.2017.
 */
public class GUI {
    JFrame jfrm;
    JPanel jpn;
    JTable jtab;
    Controller controller;
    SelectionMenu selectionMenu;

    public void createMenu(){
        JMenuBar jmb = new JMenuBar();

        JMenu jmenu;
        jmenu = new JMenu("File");
        JMenuItem jmenuitem;
        jmenuitem = new JMenuItem("New");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(jfrm);
                File workFile = fileChooser.getSelectedFile();
                if (workFile != null) {
                    controller.setWorkFile(workFile);
                    int numberOfTrains = controller.getNumberOfTrains();
                    for (int i = numberOfTrains - 1; i >= 0 ; i--){
                        controller.removeGlobalTrain(i);
                    }
                    controller.updateList(selectionMenu);
                    selectionMenu.firstPage(selectionMenu);
                    selectionMenu.changeNumberOfPages(selectionMenu);
                    selectionMenu.changePageInfoLabel(selectionMenu);
                    selectionMenu.refreshTable();
                }
            }
        });
        jmenu.add(jmenuitem);
        jmenuitem = new JMenuItem("Open");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(jfrm);
                File workFile = fileChooser.getSelectedFile();
                if (workFile != null) {
                    controller.setWorkFile(workFile);
                    controller.openFromFile();
                    controller.updateList(selectionMenu);



                    selectionMenu.firstPage(selectionMenu);
                    selectionMenu.changeNumberOfPages(selectionMenu);
                    selectionMenu.changePageInfoLabel(selectionMenu);

                    selectionMenu.refreshTable();
                }
            }
        });
        jmenu.add(jmenuitem);
        jmenuitem = new JMenuItem("Save");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.getWorkFile() != null){
                    controller.saveToFile();
                }
                else{
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.showSaveDialog(jfrm);
                    File workFile = fileChooser.getSelectedFile();
                    if (workFile != null)
                        controller.setWorkFile(workFile);
                }
            }
        });
        jmenu.add(jmenuitem);
        jmenuitem = new JMenuItem("Close");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setWorkFile(null);
                int numberOfTrains = controller.getNumberOfTrains();
                for (int i = numberOfTrains - 1; i >= 0 ; i--){
                    controller.removeGlobalTrain(i);
                }
                controller.updateList(selectionMenu);

                selectionMenu.firstPage(selectionMenu);
                selectionMenu.changeNumberOfPages(selectionMenu);
                selectionMenu.changePageInfoLabel(selectionMenu);

                selectionMenu.refreshTable();
            }
        });
        jmenu.add(jmenuitem);
        jmenuitem = new JMenuItem("Exit");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jmenu.add(jmenuitem);
        jmb.add(jmenu);

        jmenu = new JMenu("Edit");
        jmenuitem = new JMenuItem("Add");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDialog dialog = new AddDialog(GUI.this);
            }
        });
        jmenu.add(jmenuitem);
        jmenuitem = new JMenuItem("Find");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindDialog dialog = new FindDialog(GUI.this);
            }
        });
        jmenu.add(jmenuitem);
        jmenuitem = new JMenuItem("Delete");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveDialog dialog = new RemoveDialog(GUI.this);
            }
        });
        jmenu.add(jmenuitem);
        jmb.add(jmenu);

        jmenu = new JMenu("Help");
        jmenuitem = new JMenuItem("About");
        jmenuitem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogInfo("Author: Falcon \nE-mail: stelmachenokm@gmail.com");
            }
        });
        jmenu.add(jmenuitem);
        jmb.add(jmenu);

        jfrm.setJMenuBar(jmb);
    }

    public void createToolBar(){
        JToolBar jtb = new JToolBar("ToolBar", JToolBar.VERTICAL);
        JButton jbtn;
        ImageIcon icon;

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/document.png"));
        jbtn = new JButton(icon);
        jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(jfrm);
                File workFile = fileChooser.getSelectedFile();
                if (workFile != null) {
                    controller.setWorkFile(workFile);
                    int numberOfTrains = controller.getNumberOfTrains();
                    for (int i = numberOfTrains - 1; i >= 0 ; i--){
                        controller.removeGlobalTrain(i);
                    }
                    controller.updateList(selectionMenu);
                    selectionMenu.firstPage(selectionMenu);
                    selectionMenu.changeNumberOfPages(selectionMenu);
                    selectionMenu.changePageInfoLabel(selectionMenu);
                    selectionMenu.refreshTable();
                }
            }
        });
        jtb.add(jbtn);

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/folder.png"));
        jbtn = new JButton(icon);
        jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(jfrm);
                File workFile = fileChooser.getSelectedFile();
                if (workFile != null) {
                    controller.setWorkFile(workFile);
                    controller.openFromFile();
                    controller.updateList(selectionMenu);
                    selectionMenu.firstPage(selectionMenu);
                    selectionMenu.changeNumberOfPages(selectionMenu);
                    selectionMenu.changePageInfoLabel(selectionMenu);
                    selectionMenu.refreshTable();
                }
            }
        });
        jtb.add(jbtn);

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/save.png"));
        jbtn = new JButton(icon);
        jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.getWorkFile() != null){
                    controller.saveToFile();
                }
                else{
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.showSaveDialog(jfrm);
                    File workFile = fileChooser.getSelectedFile();
                    if (workFile != null)
                        controller.setWorkFile(workFile);
                }
            }
        });
        jtb.add(jbtn);

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/add.png"));
        jbtn = new JButton(icon);
        jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDialog dialog = new AddDialog(GUI.this);
            }
        });
        jtb.add(jbtn);

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/search.png"));
        jbtn = new JButton(icon);
        jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindDialog dialog = new FindDialog(GUI.this);
            }
        });
        jtb.add(jbtn);

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/delete.png"));
        jbtn = new JButton(icon);
        jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveDialog dialog = new RemoveDialog(GUI.this);
            }
        });
        jtb.add(jbtn);

        jpn.add(jtb, BorderLayout.WEST);
    }

    public void createTable(){
        /*DefaultTableModel model;
        String[] headings = {"Номер поезда", "Станция отправления", "Станция прибытия",
                "Дата и время отправления", "Дата и время прибытия", "Время в пути"};
        //Object[][] data = {};
        JPanel jpnCenter;

        model = new DefaultTableModel();
        model.setColumnIdentifiers(headings);*/
        DefaultTableModel model = createTableModel();
        JPanel jpnCenter;
        jtab = new JTable();
        jtab.setModel(model);

        jpnCenter = new JPanel(new BorderLayout());
        jpnCenter.add(jtab.getTableHeader(), BorderLayout.NORTH);
        jpnCenter.add(jtab, BorderLayout.CENTER);
        jpn.add(jpnCenter, BorderLayout.CENTER);
    }

    public void createSelectionMenu(){
        selectionMenu = new SelectionMenu();
        jpn.add(selectionMenu.getPanel(), BorderLayout.CENTER);

    }

    public void createController(){
        controller = new Controller();
        controller.setGUI(this);
    }



    public DefaultTableModel createTableModel(){
        DefaultTableModel tableModel;
        String[] headings = {"Номер поезда", "Станция отправления", "Станция прибытия",
                "Дата и время отправления", "Дата и время прибытия", "Время в пути"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(headings);
        return tableModel;
    }



    public Controller getController(){
        return controller;
    }


    public SelectionMenu getSelectionMenu(){
        return selectionMenu;
    }

    public void dialogInfo(String str){
        JOptionPane.showMessageDialog(jfrm, str);
    }

    GUI(){
        jfrm = new JFrame("Table Editor");
        jpn = new JPanel(new BorderLayout());
        jfrm.add(jpn);
        Dimension windowDimension = new Dimension(1920, 1030);
        jfrm.setSize(windowDimension);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setVisible(true);
        createMenu();
        createToolBar();
        createSelectionMenu();
        createController();
    }

    public static void main(String args[]){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });

    }
}

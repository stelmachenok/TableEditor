package View;

import Controller.Controller;
import Model.GlobalModel;
import Model.Train;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by y50-70 on 27.04.2017.
 */
public class DialogView {
    GUI gui;
    Controller controller;
    JFrame jfrm;
    JPanel jpn;
    JTable table;
    ButtonGroup optionsOfSearch;
    JRadioButton numberAndDateFrom;
    JRadioButton dateFromAndDateTo;
    JRadioButton stationFromAndStationTo;
    JRadioButton time;
    JLabel trainNumberLabel;
    JLabel trainDateFromLabel;
    JLabel trainDateFromFromLabel;
    JLabel trainDateFromToLabel;
    JLabel trainDateToFromLabel;
    JLabel trainDateToToLabel;
    JLabel trainStationFromLabel;
    JLabel trainStationToLabel;
    JLabel trainTimeLabel;
    JTextField trainNumberTextField;
    JTextField trainDateFromTextField;
    JTextField trainDateFromFromTextField;
    JTextField trainDateFromToTextField;
    JTextField trainDateToFromTextField;
    JTextField trainDateToToTextField;
    JTextField trainStationFromTextField;
    JTextField trainStationToTextField;
    JTextField trainTimeTextField;
    JButton actionButton;
    SelectionMenu selectionMenu;
    GlobalModel model;

    public ButtonGroup getButtonGroup(){
        return optionsOfSearch;
    }

    public JRadioButton getNumberAndDateFrom(){
        return numberAndDateFrom;
    }

    public JRadioButton getDateFromAndDateTo() {
        return dateFromAndDateTo;
    }

    public JRadioButton getStationFromAndStationTo() {
        return stationFromAndStationTo;
    }

    public JRadioButton getTime(){
        return time;
    }

    public String getTrainNumberTextField() {
        return trainNumberTextField.getText();
    }

    public String getTrainDateFromTextField() {
        return trainDateFromTextField.getText();
    }

    public String getTrainDateFromFromTextField() {
        return trainDateFromFromTextField.getText();
    }

    public String getTrainDateFromToTextField() {
        return trainDateFromToTextField.getText();
    }

    public String getTrainDateToFromTextField() {
        return trainDateToFromTextField.getText();
    }

    public String getTrainDateToToTextField() {
        return trainDateToToTextField.getText();
    }

    public String getTrainStationFromTextField() {
        return trainStationFromTextField.getText();
    }

    public String getTrainStationToTextField() {
        return trainStationToTextField.getText();
    }

    public String getTrainTimeTextField() {
        return trainTimeTextField.getText();
    }

    public Date parseDateFromString(String str){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parsingDate;
        try {
            parsingDate = ft.parse(str);
            return parsingDate;
        }catch (ParseException e) {
            return null;
        }
    }

    public JTable getTable(){
        return table;
    }

    public GlobalModel getModel(){
        return model;
    }

    public SelectionMenu getSelectionMenu(){
        return selectionMenu;
    }

    public JButton getActionButton(){
        return actionButton;
    }

    public GUI getGUI(){
        return gui;
    }

    public JFrame getFrame(){
        return jfrm;
    }


    public void createSelectionMenu(){
        selectionMenu = new SelectionMenu();


        GridBagConstraints gbc = new GridBagConstraints(3,0,2,14,0,0,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(10,5,5,5), 0,0);

        gbc.fill = GridBagConstraints.VERTICAL;
        jpn.add(selectionMenu.getPanel(), gbc);

        jfrm.pack();
    }

    public DefaultTableModel createTableModel(){
        DefaultTableModel tableModel;
        String[] headings = {"Номер поезда", "Станция отправления", "Станция прибытия",
                "Дата и время отправления", "Дата и время прибытия", "Время в пути"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(headings);
        return tableModel;
    }

    public DialogView(GUI ui){
        gui = ui;
        controller = gui.getController();
        jpn = new JPanel(new GridBagLayout());
        jfrm = new JFrame("Find Train");
        createSelectionMenu();
        selectionMenu.setController(controller);
        //createModel();

        //jpn = new JPanel(new GridLayout(13,1,5,0));

        jfrm.add(jpn);
        Dimension windowDimension = new Dimension(1280, 720);
        jfrm.setSize(windowDimension);
        jfrm.setVisible(true);

        //GridBagConstraints c = new GridBagConstraints();

        numberAndDateFrom = new JRadioButton("По номеру поезда и дате отправления");
        dateFromAndDateTo = new JRadioButton("По времени отправления и времени прибытия");
        stationFromAndStationTo = new JRadioButton("По станции отправления и станции прибытия");
        time = new JRadioButton("По времени в пути");

        optionsOfSearch = new ButtonGroup();
        optionsOfSearch.add(numberAndDateFrom);
        optionsOfSearch.add(dateFromAndDateTo);
        optionsOfSearch.add(stationFromAndStationTo);
        optionsOfSearch.add(time);

        trainNumberLabel = new JLabel("Номер поезда");
        trainDateFromLabel = new JLabel("Дата отправления");
        trainDateFromFromLabel = new JLabel("Время отправления (с)");
        trainDateFromToLabel = new JLabel("Время отправления (по)");
        trainDateToFromLabel = new JLabel("Время прибытия (с)");
        trainDateToToLabel = new JLabel("Время прибытия (по)");
        trainStationFromLabel = new JLabel("Станция отправления");
        trainStationToLabel = new JLabel("Станция прибытия");
        trainTimeLabel = new JLabel("Время в пути");

        trainNumberTextField = new JTextField(20);
        trainDateFromTextField = new JTextField(20);
        trainDateFromFromTextField = new JTextField(20);
        trainDateFromToTextField = new JTextField(20);
        trainDateToFromTextField = new JTextField(20);
        trainDateToToTextField = new JTextField(20);
        trainStationFromTextField = new JTextField(20);
        trainStationToTextField = new JTextField(20);
        trainTimeTextField = new JTextField(20);

        optionsOfSearch.setSelected(numberAndDateFrom.getModel(), true);

        actionButton = new JButton();


        DefaultTableModel tableModel = gui.createTableModel();
        table = new JTable();
        table.setModel(tableModel);

        GridBagConstraints gbc = new GridBagConstraints(0,0,2,1,0,0,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(10,5,5,5), 0,0);

        jpn.add(numberAndDateFrom, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        jpn.add(trainNumberLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainNumberTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        jpn.add(trainDateFromLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainDateFromTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        jpn.add(dateFromAndDateTo, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        jpn.add(trainDateFromFromLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainDateFromFromTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        jpn.add(trainDateFromToLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainDateFromToTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        jpn.add(trainDateToFromLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainDateToFromTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        jpn.add(trainDateToToLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainDateToToTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        jpn.add(stationFromAndStationTo, gbc);

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 1;
        jpn.add(trainStationFromLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainStationFromTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        jpn.add(trainStationToLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainStationToTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 2;
        jpn.add(time, gbc);

        gbc.gridx = 0; gbc.gridy = 12; gbc.gridwidth = 1;
        jpn.add(trainTimeLabel, gbc);

        gbc.gridx = 1;
        jpn.add(trainTimeTextField, gbc);

        gbc.gridx = 0; gbc.gridy = 13; gbc.gridwidth = 2;
        jpn.add(actionButton, gbc);

        gbc.gridx = 3; gbc.gridy = 0; gbc.gridwidth = 2; gbc.gridheight = 14; gbc.fill = GridBagConstraints.BOTH;
        jpn.add(selectionMenu.getPanel(), gbc);

        jfrm.pack();

    }

    public void dialogInfo(String str){
        JOptionPane.showMessageDialog(jfrm, str);
    }
}

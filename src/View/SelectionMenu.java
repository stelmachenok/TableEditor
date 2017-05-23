package View;

import Controller.Controller;
import Model.GlobalModel;
import Model.Train;
import View.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by y50-70 on 25.04.2017.
 */
public class SelectionMenu extends JComponent{

    JTextField currentPageTextField;
    JTextField trainsOnPageTextField;

    JLabel numberOfPagesLabel;
    JLabel currentPageInfoLabel;
    JButton back;
    JButton forward;
    JButton first;
    JButton last;
    JTable table;


    Controller controller;
    JPanel panel;



    public void changeTrainsOnPage(){
        int trainsOnPage = getTrainsOnPage();
        if (trainsOnPage == -1){
            dialogInfo("Некорректное число поездов на странице \nЭто должно быть натуральное число");
            setTrainsOnPage(5);//add23
            return;
        }

        if (trainsOnPage < 1) {
            dialogInfo("Некорректное число поездов на странице \nЭто должно быть натуральное число");
            setTrainsOnPage(5);
            return;
        }
        else
            setTrainsOnPage(trainsOnPage);
        changeNumberOfPages();
    }

    public void changeNumberOfPages(){
        int numberOfTrains = getController().getTrainsOnCurrentWindow().size();
        int trainsOnPage = getTrainsOnPage();
        if (trainsOnPage == -1){
            dialogInfo("Некорректное число поездов на странице \nЭто должно быть натуральное число");
            setNumberOfPages(1);
            return;
        }
        int numberOfPages = (numberOfTrains % trainsOnPage == 0) ? numberOfTrains/trainsOnPage : numberOfTrains/trainsOnPage + 1;
        setNumberOfPages(numberOfPages);
        if (numberOfPages == 0)
            setNumberOfPages(1);
        changeCurrentPage();

    }

    public void changeCurrentPage(){
        int currentPage = getCurrentPage();

        if (currentPage == -1 ){
            dialogInfo("Некорректный номер страницы\nЭто должно быть натуральное число");
            setCurrentPage(1);//add23
            return;
        }

        int numberOfPages = getNumberOfPages();
        if(currentPage > numberOfPages){
            currentPage = numberOfPages;
            setCurrentPage(currentPage);
        }
        else
        if(currentPage < 1){
            dialogInfo("Некорректный номер страницы\nЭто должно быть натуральное число");
            setCurrentPage(1);
            return;
        }
        else
            setCurrentPage(currentPage);
        if (currentPage == 0)
            setCurrentPage(1);
    }

    public void previousPage(){
        int currentPage = getCurrentPage();
        if (currentPage < 1){
            dialogInfo("Некорректный номер страницы\nЭто должно быть натуральное число");
            setCurrentPage(1);//add23
            return;
        }
        if (currentPage > 1){
            currentPage--;
            setCurrentPage(currentPage);
        }
    }

    public void nextPage(){
        int currentPage = getCurrentPage();
        if (currentPage < 1){
            dialogInfo("Некорректный номер страницы\nЭто должно быть натуральное число");
            setCurrentPage(1);//add23
            return;
        }
        int numberOfPages = getNumberOfPages();
        if (currentPage < numberOfPages){
            currentPage++;
            setCurrentPage(currentPage);
        }
    }

    public void firstPage(){
        int neededPage = 1;
        setCurrentPage(neededPage);
    }

    public void lastPage(){
        int neededPage = getNumberOfPages();
        setCurrentPage(neededPage);
    }

    public void setCurrentPage(int currentPage){
        setCurrentPage(String.valueOf(currentPage));
        changePageInfoLabel();
    }

    public void changePageInfoLabel(){
        java.util.List<Train> trains = getController().getTrainsOnCurrentWindow();
        if (trains.size() == 0){
            setCurrentPageInfoLabel("[0-0/0]");
        }
        else {
            int currentPage = getCurrentPage();
            int trainsOnPage = getTrainsOnPage();
            int numberOfTrains = trains.size();
            if (currentPage == -1){
                dialogInfo("Текущая страница должна быть натуральным числом!");
                setCurrentPage(1);//add23
                return;
            }
            else
            if (trainsOnPage == -1){
                dialogInfo("Количество поездов на странице должно быть натуральным числом!");
                return;
            }

            int lowerBound = (currentPage - 1) * trainsOnPage + 1;
            int upperBound = (currentPage * trainsOnPage < numberOfTrains) ? currentPage * trainsOnPage : numberOfTrains;
            setCurrentPageInfoLabel("[" + lowerBound + "-" + upperBound + "/" + numberOfTrains + "]");
        }
    }

    public void setNumberOfPages( int numberOfPages){
        setNumberOfPages(String.valueOf(numberOfPages));
        changePageInfoLabel();
    }

    public void setTrainsOnPage(int trainsOnPage){
        setTrainsOnPage(String.valueOf(trainsOnPage));
        changePageInfoLabel();
    }

    public Controller getController(){
        return controller;
    }

    public int getNumberOfPages() {
        int numberOfPages;
        try{
            numberOfPages = Integer.valueOf(numberOfPagesLabel.getText());
        }
        catch (NumberFormatException ex){
            numberOfPages = -1;
        }
        return numberOfPages;
    }

    public int getCurrentPage() {
        int currentPage;
        try{
            currentPage = Integer.valueOf(currentPageTextField.getText());
        }
        catch (NumberFormatException ex){
            currentPage = -1;
        }
        return currentPage;
    }

    public int getTrainsOnPage() {
        int trainsOnPage;
        try{
            trainsOnPage = Integer.valueOf(trainsOnPageTextField.getText());
        }
        catch (NumberFormatException ex){
            trainsOnPage = -1;
        }
        return trainsOnPage;
    }

    public void setCurrentPage(String currentPageTextField) {
        this.currentPageTextField.setText(currentPageTextField);
    }

    public void setTrainsOnPage(String trainsOnPageTextField) {
        this.trainsOnPageTextField.setText(trainsOnPageTextField);
    }

    public void setNumberOfPages(String numberOfPagesLabel) {
        this.numberOfPagesLabel.setText(numberOfPagesLabel);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void updateList(){

        List<Train> trains = controller.getTrainsOnCurrentWindow();
        while (!trains.isEmpty()){
            trains.remove(0);
        }
        for (int i = 0; i < controller.getNumberOfTrains(); i++){
            trains.add(controller.getTrain(i));
        }
    }

    public void createTable(){
        table = new JTable();
        DefaultTableModel model = createTableModel();
        table.setModel(model);
    }

    public DefaultTableModel createTableModel(){
        DefaultTableModel tableModel;
        String[] headings = {"Номер поезда", "Станция отправления", "Станция прибытия",
                "Дата и время отправления", "Дата и время прибытия", "Время в пути"};
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(headings);
        return tableModel;
    }

    public void refreshTable(){
        DefaultTableModel tableModel = createTableModel();
        table.setModel(tableModel);
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy ' ' HH:mm");
        int currentPage = getCurrentPage();
        if (currentPage == -1){
            dialogInfo("Некорректный номер страницы\nЭто должно быть натуральное число");
            return;
        }
        int trainsOnPage = getTrainsOnPage();
        if (trainsOnPage == -1){
            dialogInfo("Количество поездов на странице должно быть натуральным числом!");
            return;
        }
        int numberOfTrains = controller.getNumberOfTrainsOnThisWindow();
        int upperBound = (numberOfTrains < trainsOnPage * currentPage) ? numberOfTrains : trainsOnPage * currentPage;
        for (int i = trainsOnPage * (currentPage - 1); i < upperBound; i++){
            Train train = controller.getTrainsOnCurrentWindow().get(i);
            Object objects[] = {train.getTrainNumber(),
                    train.getTrainStationFrom(),
                    train.getTrainStationTo(),
                    formatForDateNow.format(train.getTrainDateFrom()),
                    formatForDateNow.format(train.getTrainDateTo()),
                    train.getTrainTime()
            };
            tableModel.addRow(objects);
        }
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setCurrentPageInfoLabel(String info) {
        this.currentPageInfoLabel.setText(info);
    }

    public JTable getTable() {
        return table;
    }

    public void dialogInfo(String str){
        JOptionPane.showMessageDialog(this, str);
    }

    public SelectionMenu(){
        JPanel jpnSouth;
        JPanel jpnControl;
        JPanel jpnTrainsOnPage;
        JButton jbtn;
        ImageIcon icon;
        //JTextField jtf;
        JLabel jlab;
        createTable();
        Dimension elementDimension = new Dimension(48,48);
        Font textFont = new Font("Arial", Font.PLAIN, 32);
        controller = new Controller();

        jpnSouth = new JPanel(new BorderLayout());
        jpnTrainsOnPage = new JPanel(new FlowLayout());
        jpnControl = new JPanel(new FlowLayout());



        icon = new ImageIcon(getClass().getClassLoader().getResource("res/back.png"));
        first = new JButton(icon);
        first.setPreferredSize(elementDimension);
        jpnControl.add(first);
        first.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstPage();
                refreshTable();
            }
        });

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/back.png"));
        back = new JButton(icon);
        back.setPreferredSize(elementDimension);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousPage();
                refreshTable();
            }
        });
        jpnControl.add(back);

        currentPageTextField = new JTextField();
        currentPageTextField.setPreferredSize(elementDimension);
        currentPageTextField.setFont(textFont);
        currentPageTextField.setText("1");
        currentPageTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCurrentPage();
                refreshTable();
            }
        });
        jpnControl.add(currentPageTextField);

        jlab = new JLabel("/");
        jlab.setFont(textFont);
        jpnControl.add(jlab);

        numberOfPagesLabel = new JLabel(String.valueOf(1));
        numberOfPagesLabel.setFont(textFont);
        jpnControl.add(numberOfPagesLabel);

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/forward.png"));
        forward = new JButton(icon);
        forward.setPreferredSize(elementDimension);
        forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPage();
                refreshTable();
            }
        });
        jpnControl.add(forward);

        icon = new ImageIcon(getClass().getClassLoader().getResource("res/forward.png"));
        last = new JButton(icon);
        last.setPreferredSize(elementDimension);
        jpnControl.add(last);
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastPage();
                refreshTable();
            }
        });


        int lowerBound = 0;
        int upperBound = 0;
        int numberOfTrains = 0;
        currentPageInfoLabel = new JLabel("[" + lowerBound + "-" + upperBound + "/" + numberOfTrains + "]");
        currentPageInfoLabel.setFont(textFont);
        jpnTrainsOnPage.add(currentPageInfoLabel);

        jlab = new JLabel("Trains on page:");
        jlab.setFont(textFont);
        jpnTrainsOnPage.add(jlab);

        trainsOnPageTextField = new JTextField();
        trainsOnPageTextField.setPreferredSize(elementDimension);
        trainsOnPageTextField.setFont(textFont);
        trainsOnPageTextField.setText("5");
        trainsOnPageTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTrainsOnPage();
                refreshTable();

            }
        });
        jpnTrainsOnPage.add(trainsOnPageTextField);

        jpnSouth.add(jpnControl, BorderLayout.CENTER);
        jpnSouth.add(jpnTrainsOnPage, BorderLayout.EAST);
        panel = new JPanel(new BorderLayout());
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(jpnSouth, BorderLayout.SOUTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
    }
}

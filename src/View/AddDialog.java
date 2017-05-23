package View;

import Controller.Controller;
import Model.Train;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by y50-70 on 25.04.2017.
 */
public class AddDialog {
    GUI gui;
    JFrame jfrm;
    JPanel jpn;
    JTextField trainNumber;
    JTextField trainStationFrom;
    JTextField trainStationTo;
    JTextField trainDateFrom;
    JTextField trainDateTo;
    JTextField trainTime;
    Controller controller;

    public String getTrainNumber(){
        return trainNumber.getText();
    }

    public String getStationFrom(){
        return trainStationFrom.getText();
    }

    public String getStationTo(){
        return trainStationTo.getText();
    }

    public String getDateFrom(){
        return trainDateFrom.getText();
    }

    public String getDateTo(){
        return trainDateTo.getText();
    }

    public String getTime(){
        return trainTime.getText();
    }

    public void dialogInfo(String str){
        JOptionPane.showMessageDialog(jfrm, str);
    }

    public void addTrain(){
        Train train = new Train();
        try {
            train.setTrainNumber(Integer.valueOf(getTrainNumber()));
        }catch (NumberFormatException e) {
            dialogInfo("Некорректно заполнен номер поезда.  \nЭто должно быть целое число");
            return;
        }
        train.setTrainStationFrom(getStationFrom());
        train.setTrainStationTo(getStationTo());
        train.setTrainDateFrom(controller.parseDateFromString(getDateFrom()));
        train.setTrainDateTo(controller.parseDateFromString(getDateTo()));
        try {
            Date dateFrom = controller.parseDateFromString(getDateFrom());
            Date dateTo = controller.parseDateFromString(getDateTo());
            if (dateFrom == null || dateTo == null){
                dialogInfo("Некорректно заполнена дата.  \nИспользуйте формат \"yyyy-MM-dd HH:mm\" ");
                return;
            }
            int minutes = (int)((dateTo.getTime() - dateFrom.getTime())/1000/60);                                                                                                           //перевод из милисекунд в минуты
            train.setTrainTime(minutes);
        }catch (NumberFormatException e) {
            dialogInfo("Некорректно заполнено время в пути.  \nЭто должно быть целое положительное число (минут)");
            return;
        }
        controller.addGlobalTrain(train);
        gui.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(train);

    }

    public AddDialog(final GUI gui){
        this.gui = gui;
        controller = gui.getController();
        jfrm = new JFrame("Add Train");
        jpn = new JPanel(new GridLayout(11,1,5,0));
        jfrm.add(jpn);
        Dimension windowDimension = new Dimension(640, 480);
        jfrm.setSize(windowDimension);
        jfrm.setVisible(true);

        JLabel jlb;
        JButton jbtn;

        jlb = new JLabel("Номер поезда");
        jpn.add(jlb);
        trainNumber = new JTextField();
        jpn.add(trainNumber );

        jlb = new JLabel("Станция отправления");
        jpn.add(jlb);
        trainStationFrom = new JTextField();
        jpn.add(trainStationFrom);

        jlb = new JLabel("Станция прибытия");
        jpn.add(jlb);
        trainStationTo = new JTextField();
        jpn.add(trainStationTo);

        jlb = new JLabel("Дата и время отправления");
        jpn.add(jlb);
        trainDateFrom = new JTextField();
        jpn.add(trainDateFrom);

        jlb = new JLabel("Дата и время прибытия");
        jpn.add(jlb);
        trainDateTo = new JTextField();
        jpn.add(trainDateTo);

        jbtn = new JButton("Добавить");
        jbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTrain();
                gui.getSelectionMenu().changeTrainsOnPage(gui.getSelectionMenu());
                gui.getSelectionMenu().refreshTable();
            }
        });
        jpn.add(jbtn);


    }
}

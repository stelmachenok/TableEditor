package View;

import Controller.Controller;
import Model.Train;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

/**
 * Created by y50-70 on 09.05.2017.
 */
public class RemoveDialog {
    DialogView dialogView;
    //GUI gui;
    Controller controller;

    public DialogView getDialogView() {
        return dialogView;
    }

    public SelectionMenu getSelectionMenu(){
        return dialogView.getSelectionMenu();
    }

    public void deleteOptions(){


        int numberOfTrains = getSelectionMenu().getController().getTrainsOnCurrentWindow().size();
        List<Train> trains = getSelectionMenu().getController().getTrainsOnCurrentWindow();
        for (int i = numberOfTrains - 1; i>=0; i--){
            trains.remove(i);
        }

        ButtonGroup deleteOption = getDialogView().getButtonGroup();
        JRadioButton timeRadioButton = getDialogView().getTime();
        JRadioButton numberAndDateFromRadioButton = getDialogView().getNumberAndDateFrom();
        JRadioButton dateFromAndDateToRadioButton = getDialogView().getDateFromAndDateTo();
        JRadioButton stationFromAndStationToRadioButton = getDialogView().getStationFromAndStationTo();
        if (deleteOption.isSelected(timeRadioButton.getModel())){
            int time;
            try{
                time = Integer.valueOf(getDialogView().getTrainTimeTextField());
            }
            catch (NumberFormatException ex){
                getDialogView().dialogInfo("Время в пути должно быть натуральным числом");
                return;
            }
            if (time < 1) {
                getDialogView().dialogInfo("Время в пути должно быть натуральным числом");
                return;
            }
            if (controller.deleteByTime(time) == -1){
                return;
            }
        }
        else
        if (deleteOption.isSelected(numberAndDateFromRadioButton.getModel())) {
            int number;
            try{
                number = Integer.valueOf(getDialogView().getTrainNumberTextField());
            }catch (NumberFormatException ex){
                getDialogView().dialogInfo("Некорректный формат номера поезда \nЭто должно быть натуральное число");
                return;
            }

            if (number < 1) {
                getDialogView().dialogInfo("Некорректный формат номера поезда \nЭто должно быть натуральное число");
                return;
            }

            Date dateFrom = getDialogView().parseDateFromString(getDialogView().getTrainDateFromTextField());

            if (dateFrom == null) {
                getDialogView().dialogInfo("Некорректно заполнена дата.  \nИспользуйте формат \"yyyy-MM-dd HH:mm\" ");
                return;
            }
            if (controller.deleteByNumberAndDateFrom(number, dateFrom) == -1){
                return;
            }
        }
        else
        if (deleteOption.isSelected(dateFromAndDateToRadioButton.getModel())) {
            Date dateFromFrom = getDialogView().parseDateFromString(getDialogView().getTrainDateFromFromTextField());
            Date dateFromTo = getDialogView().parseDateFromString(getDialogView().getTrainDateFromToTextField());
            Date dateToFrom = getDialogView().parseDateFromString(getDialogView().getTrainDateToFromTextField());
            Date dateToTo = getDialogView().parseDateFromString(getDialogView().getTrainDateToToTextField());

            if (dateFromFrom  == null ||  dateFromTo == null || dateToFrom == null || dateToTo == null) {
                getDialogView().dialogInfo("Некорректно заполнена дата.  \nИспользуйте формат \"yyyy-MM-dd HH:mm\" ");
                return;
            }
            if (controller.deleteByDateFromAndDateTo(dateFromFrom, dateFromTo, dateToFrom, dateToTo) == -1){
                return;
            }
        }
        else
        if (deleteOption.isSelected(stationFromAndStationToRadioButton.getModel())) {
            String stationFrom = getDialogView().getTrainStationFromTextField();
            String stationTo = getDialogView().getTrainStationToTextField();
            if (controller.deleteByStationFromAndStationTo(stationFrom, stationTo) == -1) {
                return;
            }
        }
        getSelectionMenu().updateList();

        int numberOfRemoteTrains = getSelectionMenu().getController().getTrainsOnCurrentWindow().size();
        if (numberOfRemoteTrains > 0)
            getDialogView().dialogInfo("Было удалено поездов: " + numberOfRemoteTrains);
        else
            getDialogView().dialogInfo("В базе нет поезда, удовлетворяющего заданным критериям");
    }

    public RemoveDialog(GUI ui){
        dialogView = new DialogView(ui);
        controller = getSelectionMenu().getController();
        controller.defaultStateRemoveDialog(this);
        dialogView.getFrame().setTitle("Remove Train");

        JButton actionButton = dialogView.getActionButton();
        actionButton.setText("Удалить");
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOptions();
                getSelectionMenu().changeTrainsOnPage();
                getSelectionMenu().refreshTable();
                getSelectionMenu().updateList();

                getDialogView().getGUI().getSelectionMenu().changeTrainsOnPage();
                getDialogView().getGUI().getSelectionMenu().refreshTable();
            }
        });
    }
}

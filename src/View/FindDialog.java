package View;

import Controller.Controller;
import Model.Train;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by y50-70 on 09.05.2017.
 */
public class FindDialog {
    DialogView dialogView;
    Controller controller;

    public SelectionMenu getSelectionMenu(){
        return dialogView.getSelectionMenu();
    }

    public DialogView getDialogView() {
        return dialogView;
    }

    public void searchOptions(){

        List<Train> trains = getSelectionMenu().getController().getTrainsOnCurrentWindow();
        int numberOfTrains = trains.size();

        for (int i = numberOfTrains - 1; i>=0; i--){
            trains.remove(i);
        }

        ButtonGroup searchOption = getDialogView().getButtonGroup();
        JRadioButton timeRadioButton = getDialogView().getTime();
        JRadioButton numberAndDateFromRadioButton = getDialogView().getNumberAndDateFrom();
        JRadioButton dateFromAndDateToRadioButton = getDialogView().getDateFromAndDateTo();
        JRadioButton stationFromAndStationToRadioButton = getDialogView().getStationFromAndStationTo();
        if (searchOption.isSelected(timeRadioButton.getModel())) {
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
            if (controller.searchByTime(time) == -1){
                return;
            }
        }
        else
        if (searchOption.isSelected(numberAndDateFromRadioButton.getModel())) {

            if (controller.searchByNumberAndDateFrom() == -1){
                return;
            }
        }
        else
        if (searchOption.isSelected(dateFromAndDateToRadioButton.getModel())) {
            if (searchByDateFromAndDateTo() == -1){
                return;
            }
        }
        else
        if (searchOption.isSelected(stationFromAndStationToRadioButton.getModel())) {
            if(searchByStationFromAndStationTo() == -1){
                return;
            }
        }
        /*changeTrainsOnPage(findDialog.getSelectionMenu());
        findDialog.getSelectionMenu().refreshTable();*/

        int numberOfFoundTrains = trainsOnCurrentWindow.size();
        if (numberOfFoundTrains > 0)
            findDialog.getDialogView().dialogInfo("Было найдено поездов: " + numberOfFoundTrains);
        else
            findDialog.getDialogView().dialogInfo("В базе нет поезда, удовлетворяющего заданным критериям");
    }

    public FindDialog(GUI ui){
        dialogView = new DialogView(ui);
        controller = getSelectionMenu().getController();
        controller.defaultStateFindDialog(this);
        dialogView.getFrame().setTitle("Find Train");

        JButton actionButton = dialogView.getActionButton();
        actionButton.setText("Найти");
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchOptions();
                getSelectionMenu().changeTrainsOnPage();
                getSelectionMenu().refreshTable();
                controller.updateList(getDialogView().getGUI().getSelectionMenu());

                /*getDialogView().getGUI().getSelectionMenu().changeTrainsOnPage(getDialogView().getGUI().getSelectionMenu());
                getDialogView().getGUI().getSelectionMenu().refreshTable();*/
            }
        });
    }
}

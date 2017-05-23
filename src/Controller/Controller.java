package Controller;

import Model.GlobalModel;
import Model.Train;
import View.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by y50-70 on 25.04.2017.
 */
public class Controller {
    GUI gui;
    AddDialog addDialog;
    FindDialog findDialog;
    RemoveDialog removeDialog;
    TableParser tableParser;
    static GlobalModel globalModel = new GlobalModel();
    List<Train> trainsOnCurrentWindow = new ArrayList<Train>();


    public void removeGlobalTrain(int i){
        globalModel.removeTrain(i);
    }

    public void removeGlobalTrain(Train train){
        globalModel.removeTrain(train);
    }

    public void addGlobalTrain(Train train){
        globalModel.addTrain(train);
    }

    public List<Train> getTrainsOnCurrentWindow(){
        return trainsOnCurrentWindow;
    }

    public File getWorkFile(){
        return globalModel.getWorkFile();
    }

    public int getNumberOfTrains(){
        return globalModel.getNumberOfTrains();
    }

    public int getNumberOfTrainsOnThisWindow(){
        return trainsOnCurrentWindow.size();
    }

    public Train getTrain(int i){
        return globalModel.getTrain(i);
    }

    public void defaultStateFindDialog(FindDialog dialog){
        findDialog = dialog;
    }

    public void defaultStateRemoveDialog(RemoveDialog dialog){
        removeDialog = dialog;
    }

    public int searchByDateFromAndDateTo(){
        Date dateFromFrom = parseDateFromString(findDialog.getDialogView().getTrainDateFromFromTextField());
        Date dateFromTo = parseDateFromString(findDialog.getDialogView().getTrainDateFromToTextField());
        Date dateToFrom = parseDateFromString(findDialog.getDialogView().getTrainDateToFromTextField());
        Date dateToTo = parseDateFromString(findDialog.getDialogView().getTrainDateToToTextField());

        if (dateFromFrom  == null ||  dateFromTo == null || dateToFrom == null || dateToTo == null) {
            findDialog.getDialogView().dialogInfo("Некорректно заполнена дата.  \nИспользуйте формат \"yyyy-MM-dd HH:mm\" ");
            return -1;
        }

        //GlobalModel modelGUI = gui.getModel();
        //GlobalModel modelDialog = findDialog.getDialogView().getModel();
        int numberOfFindTrains = 0;
        int numberOfTrains = getNumberOfTrains();
        for(int i = 0; i < numberOfTrains; i++){
            Train train = getTrain(i);
            if ((dateFromFrom.before(train.getTrainDateFrom()) || dateFromFrom.equals(train.getTrainDateFrom())) &&
                    (dateFromTo.after(train.getTrainDateFrom()) || dateFromTo.equals(train.getTrainDateFrom())) &&
                    (dateToFrom.before(train.getTrainDateTo()) || dateToFrom.equals(train.getTrainDateTo())) &&
                    (dateToTo.after(train.getTrainDateTo()) || dateToTo.equals(train.getTrainDateTo()))) {
                findDialog.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(new Train(train));
                numberOfFindTrains++;
                /*DefaultTableModel tableModel = (DefaultTableModel)findDialog.getTable().getModel();
                Object objects[] = {train.getTrainNumber(), train.getTrainStationFrom(),
                        train.getTrainStationTo(), train.getTrainDateFrom(),
                        train.getTrainDateTo(), train.getTrainTime()};
                tableModel.addRow(objects);*/
            }
        }
        return numberOfFindTrains;
    }

    /*public void updateList(SelectionMenu selectionMenu){
        Controller selectionMenuController;
        selectionMenuController = selectionMenu.getController();
        List<Train> trains = selectionMenuController.getTrainsOnCurrentWindow();
        while (!trains.isEmpty()){
            trains.remove(0);
        }
        for (int i = 0; i < globalModel.getNumberOfTrains(); i++){
            trains.add(globalModel.getTrain(i));
        }
    }*/

    public int searchByStationFromAndStationTo(){
        String stationFrom = findDialog.getDialogView().getTrainStationFromTextField();
        String stationTo = findDialog.getDialogView().getTrainStationToTextField();
        //GlobalModel modelGUI = gui.getModel();
        //GlobalModel modelDialog  = findDialog.getDialogView().getModel();

        int numberOfFindTrains = 0;
        int numberOfTrains = getNumberOfTrains();
        for(int i = 0; i < numberOfTrains; i++){
            Train train = getTrain(i);
            if (train.getTrainStationFrom().equals(stationFrom) && train.getTrainStationTo().equals(stationTo)){
                findDialog.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(new Train(train));
                numberOfFindTrains++;
                /*DefaultTableModel tableModel = (DefaultTableModel)findDialog.getTable().getModel();
                Object objects[] = {train.getTrainNumber(), train.getTrainStationFrom(),
                        train.getTrainStationTo(), train.getTrainDateFrom(),
                        train.getTrainDateTo(), train.getTrainTime()};
                tableModel.addRow(objects);*/
            }
        }
        return numberOfFindTrains;
    }

    public int searchByNumberAndDateFrom(){
        int number;
        try{
            number = Integer.valueOf(findDialog.getDialogView().getTrainNumberTextField());
        }catch (NumberFormatException ex){
            findDialog.getDialogView().dialogInfo("Некорректный формат номера поезда \nЭто должно быть натуральное число");
            return -1;
        }

        if (number < 1) {
            findDialog.getDialogView().dialogInfo("Некорректный формат номера поезда \nЭто должно быть натуральное число");
            return -1;
        }

        Date dateFrom = parseDateFromString(findDialog.getDialogView().getTrainDateFromTextField());

        if (dateFrom == null) {
            findDialog.getDialogView().dialogInfo("Некорректно заполнена дата.  \nИспользуйте формат \"yyyy-MM-dd HH:mm\" ");
            return -1;
        }

        //GlobalModel modelGUI = gui.getModel();
        //GlobalModel modelDialog  = findDialog.getDialogView().getModel();
        int numberOfFindTrains = 0;
        for (int i = 0; i < getNumberOfTrains(); i++){
            Train train = getTrain(i);
            if (number == train.getTrainNumber() && dateFrom.equals(train.getTrainDateFrom())){
                findDialog.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(new Train(train));
                numberOfFindTrains++;

            }
        }
        return numberOfFindTrains;

    }

    public int searchByTime(int time){

        int numberOfFindTrains = 0;
        int numberOfTrains = getNumberOfTrains();
        for (int i = 0; i < numberOfTrains; i++){
            Train train = getTrain(i);
            if (getTrain(i).getTrainTime() == time){
                findDialog.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(new Train(train));
                numberOfFindTrains++;
            }
        }
        return numberOfFindTrains;
    }

    public int deleteByDateFromAndDateTo(Date dateFromFrom, Date dateFromTo, Date dateToFrom, Date dateToTo){

        int numberOfRemovedTrains = 0;
        int numberOfTrains = getNumberOfTrains();
        for (int i = numberOfTrains - 1; i >= 0; i--){
            Train train = getTrain(i);
            if ((dateFromFrom.before(train.getTrainDateFrom()) || dateFromFrom.equals(train.getTrainDateFrom())) &&
                    (dateFromTo.after(train.getTrainDateFrom()) || dateFromTo.equals(train.getTrainDateFrom())) &&
                    (dateToFrom.before(train.getTrainDateTo()) || dateToFrom.equals(train.getTrainDateTo())) &&
                    (dateToTo.after(train.getTrainDateTo()) || dateToTo.equals(train.getTrainDateTo()))) {
                removeDialog.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(new Train(train));
                removeGlobalTrain(train);
                numberOfRemovedTrains++;
            }
        }
        return numberOfRemovedTrains;
    }

    public int deleteByStationFromAndStationTo(String stationFrom, String stationTo){
        int numberOfRemovedTrains = 0;
        int numberOfTrains = getNumberOfTrains();
        for (int i = numberOfTrains - 1; i >= 0; i--){
            Train train = getTrain(i);
            if (train.getTrainStationFrom().equals(stationFrom) && train.getTrainStationTo().equals(stationTo)){
                removeDialog.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(new Train(train));
                removeGlobalTrain(train);
                numberOfRemovedTrains++;
            }
        }
        return numberOfRemovedTrains;
    }

    public int deleteByNumberAndDateFrom(int number, Date dateFrom){
        int numberOfTrains = getNumberOfTrains();

        int numberOfRemovedTrains = 0;
        for (int i = numberOfTrains - 1; i >= 0; i--){
            Train train = getTrain(i);
            if (number == train.getTrainNumber() && dateFrom.equals(train.getTrainDateFrom())){
                removeDialog.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(new Train(train));
                removeGlobalTrain(train);
                numberOfRemovedTrains++;
            }
        }
        return numberOfRemovedTrains;

    }

    public int deleteByTime(int time){
        int numberOfRemovedTrains = 0;
        int numberOfTrains = getNumberOfTrains();
        for (int i = numberOfTrains - 1; i >= 0; i--){
            Train train = getTrain(i);
            if (getTrain(i).getTrainTime() == time){
                removeDialog.getSelectionMenu().getController().getTrainsOnCurrentWindow().add(new Train(train));
                removeGlobalTrain(train);
                numberOfRemovedTrains++;
            }
        }
        return numberOfRemovedTrains;
    }

    public void setWorkFile(File workFile){
        globalModel.setWorkFile(workFile);
    }

    /*public Date parseDateFromString(String str){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date parsingDate;
        try {
            parsingDate = ft.parse(str);
            return parsingDate;
        }catch (ParseException e) {
            return null;
        }
    }*/

    public void saveToFile(){
        try {
            tableParser.parceToXml();//List<Train> trains =
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void openFromFile(){
        try {
            tableParser.parceFromXml();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException | IOException  e) {
            e.printStackTrace();
        }
    }

    public void setGUI(GUI gui){
        this.gui = gui;
    }

    public Controller(){
        tableParser = new TableParser();
        tableParser.setController(this);
    }
}

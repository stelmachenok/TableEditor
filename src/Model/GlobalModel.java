package Model;

import View.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by y50-70 on 25.04.2017.
 */
public class GlobalModel {
    List<Train> trains;

    File workFile;

    public int getNumberOfTrains(){
        return trains.size();
    }


    public Train getTrain(int i){
        return trains.get(i);
    }


    public void addTrain(Train train){

        trains.add(train);
    }

    public void removeTrain(Train train){
        trains.remove(train);
    }

    public void removeTrain(int Index){
        trains.remove(Index);
    }

    public File getWorkFile() {
        return workFile;
    }

    public void setWorkFile(File workFile){
        this.workFile = workFile;
    }


    public GlobalModel(){
        trains = new ArrayList<Train>();
    }
}

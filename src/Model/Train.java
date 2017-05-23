package Model;

import java.util.Date;

/**
 * Created by y50-70 on 25.04.2017.
 */
public class Train {
    int trainNumber;
    String trainStationFrom;
    String trainStationTo;
    Date trainDateFrom;
    Date trainDateTo;
    int trainTime;

    public int getTrainNumber(){
        return trainNumber;
    }

    public String getTrainStationFrom(){
        return trainStationFrom;
    }

    public String getTrainStationTo(){
        return trainStationTo;
    }

    public Date getTrainDateFrom(){
        return trainDateFrom;
    }

    public Date getTrainDateTo(){
        return trainDateTo;
    }

    public int getTrainTime(){
        return trainTime;
    }

    public void setTrainNumber(int trainNumber){
        this.trainNumber = trainNumber;
    }

    public void setTrainStationFrom(String trainStationFrom){
        this.trainStationFrom = trainStationFrom;
    }

    public void setTrainStationTo(String trainStationTo){
        this.trainStationTo = trainStationTo;
    }

    public void setTrainDateFrom(Date trainDateFrom){
        this.trainDateFrom = trainDateFrom;
    }

    public void setTrainDateTo(Date trainDateTo){
        this.trainDateTo = trainDateTo;
    }

    public void setTrainTime(int trainTime){
        this.trainTime = trainTime;
    }

    public Train(Train train){
        trainNumber = train.trainNumber;
        trainStationFrom = train.trainStationFrom;
        trainStationTo = train.trainStationTo;
        trainDateFrom = train.trainDateFrom;
        trainDateTo = train.trainDateTo;
        trainTime = train.trainTime;
    }

    public Train(){

    }
}

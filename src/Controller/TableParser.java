package Controller;

import Model.Train;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.Date;

/**
 * Created by y50-70 on 09.05.2017.
 */
public class TableParser {
    Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void parceToXml() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element tableElem = document.createElement("Table");
        document.appendChild(tableElem);

        for (int i = 0; i < controller.getNumberOfTrains(); i++){
            Train train = controller.getTrain(i);

            Element trainElem = document.createElement("Train");
            tableElem.appendChild(trainElem);

            Element numberElem = document.createElement("Number");
            numberElem.setTextContent(String.valueOf(train.getTrainNumber()));
            trainElem.appendChild(numberElem);

            Element stationFromElem = document.createElement("StationFrom");
            stationFromElem.setTextContent(train.getTrainStationFrom());
            trainElem.appendChild(stationFromElem);

            Element stationToElem = document.createElement("StationTo");
            stationToElem.setTextContent(train.getTrainStationTo());
            trainElem.appendChild(stationToElem);

            Element dateFromElem = document.createElement("DateFrom");
            dateFromElem.setTextContent(String.valueOf(train.getTrainDateFrom().getTime()));
            trainElem.appendChild(dateFromElem);

            Element dateToElem = document.createElement("DateTo");
            dateToElem.setTextContent(String.valueOf(train.getTrainDateTo().getTime()));
            trainElem.appendChild(dateToElem);

            Element timeElem = document.createElement("Time");
            timeElem.setTextContent(String.valueOf(train.getTrainTime()));
            trainElem.appendChild(timeElem);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamFile = new StreamResult(controller.getWorkFile());
        transformer.transform(domSource, streamFile);
    }

    public void parceFromXml() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        DefaultHandler handler = (new DefaultHandler(){
            String thisElement;
            Train currentTrain;
            @Override
            public void startDocument() throws SAXException {
                for(int i = controller.getNumberOfTrains() - 1; i >= 0; i--){
                    Train train = controller.getTrain(i);
                    controller.removeGlobalTrain(train);
                }
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                thisElement = qName;
                if (thisElement.equals("Train"))
                    currentTrain = new Train();
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                if (qName.equals("Train")){
                    controller.addGlobalTrain(currentTrain);
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                if (thisElement.equals("Number")){
                    currentTrain.setTrainNumber(Integer.valueOf(new String(ch, start, length)));
                }
                if (thisElement.equals("StationFrom")){
                    currentTrain.setTrainStationFrom(new String(ch, start, length));
                }
                if (thisElement.equals("StationTo")){
                    currentTrain.setTrainStationTo(new String(ch, start, length));
                }
                if (thisElement.equals("DateFrom")){
                    Date date = new Date();
                    date.setTime(Long.valueOf(new String(ch, start, length)));
                    currentTrain.setTrainDateFrom(date);
                }
                if (thisElement.equals("DateTo")){
                    Date date = new Date();
                    date.setTime(Long.valueOf(new String(ch, start, length)));
                    currentTrain.setTrainDateTo(date);
                }
                if (thisElement.equals("Time")){
                    currentTrain.setTrainTime(Integer.valueOf(new String(ch, start, length)));
                }
            }
        });
        saxParser.parse(controller.getWorkFile(), handler);
    }
}

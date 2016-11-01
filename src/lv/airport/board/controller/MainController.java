package lv.airport.board.controller;

import lv.airport.board.domain.Data;

import javax.xml.bind.JAXBException;
import java.util.List;

public interface MainController {
    void setFlights(List<Data.Flight> flights);

    void clearFlights();

    void refreshFlights(List<Data.Flight> flights);

    void addFlights(Data.Flight flight);

    void removeSelectedFlights();

    void updateFlights(Data.Flight flight, int selectedIndex);

    void importNewFlights() throws JAXBException;

    void exportFlights() throws JAXBException;
}

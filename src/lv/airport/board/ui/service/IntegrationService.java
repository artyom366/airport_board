package lv.airport.board.ui.service;

import lv.airport.board.domain.Data;

import javax.xml.bind.JAXBException;

public interface IntegrationService {
    Data getImportedFlights() throws JAXBException;

    void exportFlights(Data flights) throws JAXBException;
}

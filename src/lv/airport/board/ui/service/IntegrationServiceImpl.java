package lv.airport.board.ui.service;

import lv.airport.board.domain.Data;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class IntegrationServiceImpl implements IntegrationService {

    private final static String IN = "integration\\in\\flight.xml";
    private final static String OUT = "integration\\out\\flight.xml";

    @Override
    public Data getImportedFlights() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Data) jaxbUnmarshaller.unmarshal(new File(IN));
    }

    @Override
    public void exportFlights(final Data flights) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.marshal(flights, new File(OUT));
    }
}

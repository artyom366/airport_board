package lv.airport.board.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import lv.airport.board.domain.Data;
import lv.airport.board.ui.service.IntegrationService;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainControllerImpl implements MainController {

    private final TableView<Data.Flight> table;
    private final IntegrationService integrationService;

    public MainControllerImpl(final TableView<Data.Flight> table, final IntegrationService integrationService) {
        this.table = table;
        this.integrationService = integrationService;
    }

    @Override
    public void setFlights(final List<Data.Flight> flights) {
        final ObservableList<Data.Flight> items = FXCollections.observableList(flights);
        table.setItems(items);
    }

    @Override
    public void clearFlights() {
        table.getItems().clear();
    }

    @Override
    public void refreshFlights(final List<Data.Flight> flights) {
        clearFlights();
        setFlights(flights);
    }

    @Override
    public void addFlights(final Data.Flight flight) {
        final List<Data.Flight> oldItems = table.getItems();
        final List<Data.Flight> tempItems = new ArrayList<>(Collections.singletonList(flight));
        tempItems.addAll(oldItems);

        final ObservableList<Data.Flight> newItems = new ObservableListWrapper<Data.Flight>(tempItems);
        refreshFlights(newItems);
    }

    @Override
    public void removeSelectedFlights() {
        final List<Data.Flight> selectedItems = table.getSelectionModel().getSelectedItems();
        final List<Data.Flight> oldItems = table.getItems();
        oldItems.removeAll(selectedItems);

        final List<Data.Flight> newItems = new ArrayList<>(Collections.unmodifiableList(oldItems));
        refreshFlights(newItems);
    }

    @Override
    public void updateFlights(final Data.Flight updatedFlight, final int selectedIndex) {
        final List<Data.Flight> oldItems = table.getItems();
        final Data.Flight flight = oldItems.remove(selectedIndex);

        if (flight.getId().equals(updatedFlight.getId())) {
            final List<Data.Flight> newItems = new ArrayList<>(Collections.singleton(updatedFlight));
            newItems.addAll(oldItems);
            refreshFlights(newItems);
        }
    }

    @Override
    public void importNewFlights() throws JAXBException {
        final Data importedFlights = integrationService.getImportedFlights();
        refreshFlights(importedFlights.getFlights());
    }

    @Override
    public void exportFlights() throws JAXBException {
        final Data data = new Data();
        data.setFlights(table.getItems());
        integrationService.exportFlights(data);
    }
}

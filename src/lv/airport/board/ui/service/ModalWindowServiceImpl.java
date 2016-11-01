package lv.airport.board.ui.service;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lv.airport.board.controller.MainController;
import lv.airport.board.domain.Data;
import lv.airport.board.ui.components.ButtonFactory;
import lv.airport.board.ui.components.TextFieldFactory;

import javax.xml.bind.JAXBException;
import java.util.List;

public class ModalWindowServiceImpl implements ModalWindowService {

    private final MainController mainController;
    private final TableView<Data.Flight> table;

    public ModalWindowServiceImpl(final MainController mainController, TableView<Data.Flight> table) {
        this.mainController = mainController;
        this.table = table;
    }

    @Override
    public void getAddModal() {
        final Stage addModal = new Stage();
        final Pane root = new Pane();

        final Button addButton = ButtonFactory.getButton(20, 220, 25, 50, "Add");
        addButton.setOnAction(event -> {
            final Data.Flight flight = getFlightFromModal(root);
            mainController.addFlights(flight);
            addModal.close();
        });

        final Button cancelButton = ButtonFactory.getButton(80, 220, 25, 50, "Cancel");
        cancelButton.setOnAction(event -> addModal.close());

        root.getChildren().addAll(addButton, cancelButton);
        init(root);
        Scene myDialogScene = new Scene(root, 340, 250);

        addModal.initModality(Modality.APPLICATION_MODAL);
        addModal.setScene(myDialogScene);
        addModal.show();
    }

    @Override
    public void getUpdateModal() {
        final Stage updateModal = new Stage();
        final Pane root = new Pane();

        final Data.Flight selectedItem = table.getSelectionModel().getSelectedItem();
        final int selectedIndex = table.getSelectionModel().getFocusedIndex();

        final Button updateButton = ButtonFactory.getButton(20, 220, 25, 50, "Update");
        updateButton.setOnAction(event -> {
            final Data.Flight flight = getFlightFromModal(root);
            mainController.updateFlights(flight, selectedIndex);
            updateModal.close();
        });

        final Button cancelButton = ButtonFactory.getButton(80, 220, 25, 50, "Cancel");
        cancelButton.setOnAction(event -> updateModal.close());

        root.getChildren().addAll(updateButton, cancelButton);
        init(root);
        setFlightToModal(selectedItem, root);

        Scene myDialogScene = new Scene(root, 340, 250);

        updateModal.initModality(Modality.APPLICATION_MODAL);
        updateModal.setScene(myDialogScene);
        updateModal.show();
    }

    @Override
    public void getRemoveModal() {
        final Stage removeModal = new Stage();
        final Pane root = new Pane();

        final Button okButton = ButtonFactory.getButton(90, 50, 25, 50, "Ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                mainController.removeSelectedFlights();
                removeModal.close();
            }
        });

        final Button cancelButton = ButtonFactory.getButton(150, 50, 25, 50, "Cancel");
        cancelButton.setOnAction(event -> removeModal.close());

        final int quantity = table.getSelectionModel().getSelectedIndices().size();
        final Label promptLabel = new Label(String.format("Are you sure to remove %d flight(s)?", quantity));
        promptLabel.setLayoutX(55);
        promptLabel.setLayoutY(20);

        root.getChildren().addAll(promptLabel, okButton, cancelButton);

        Scene myDialogScene = new Scene(root, 290, 100);

        removeModal.initModality(Modality.APPLICATION_MODAL);
        removeModal.setScene(myDialogScene);
        removeModal.show();

    }

    @Override
    public void getExportModal() {
        final Stage exportModal = new Stage();
        final Pane root = new Pane();

        Scene myDialogScene = new Scene(root, 290, 100);

        exportModal.initModality(Modality.APPLICATION_MODAL);
        exportModal.setScene(myDialogScene);
        exportModal.show();

        final Button okButton = ButtonFactory.getButton(90, 50, 25, 50, "Ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    mainController.exportFlights();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                exportModal.close();
            }
        });

        final Button cancelButton = ButtonFactory.getButton(150, 50, 25, 50, "Cancel");
        cancelButton.setOnAction(event -> exportModal.close());

        final Label promptLabel = new Label("Are you sure to export flights?");
        promptLabel.setLayoutX(55);
        promptLabel.setLayoutY(20);

        root.getChildren().addAll(promptLabel, okButton, cancelButton);
    }

    @Override
    public void getImportModal() {

        final Stage importModal = new Stage();
        final Pane root = new Pane();

        Scene myDialogScene = new Scene(root, 290, 100);

        importModal.initModality(Modality.APPLICATION_MODAL);
        importModal.setScene(myDialogScene);
        importModal.show();

        final Button okButton = ButtonFactory.getButton(90, 50, 25, 50, "Ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                try {
                    mainController.importNewFlights();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                importModal.close();
            }
        });

        final Button cancelButton = ButtonFactory.getButton(150, 50, 25, 50, "Cancel");
        cancelButton.setOnAction(event -> importModal.close());

        final Label promptLabel = new Label("Are you sure to import new flights?");
        promptLabel.setLayoutX(55);
        promptLabel.setLayoutY(20);

        root.getChildren().addAll(promptLabel, okButton, cancelButton);
    }

    private void init(final Pane root) {
        final TextField id = TextFieldFactory.getTextField(10, 20, 300, 25, "ID");
        id.setVisible(false);

        final TextField flightNumber = TextFieldFactory.getTextField(10, 20, 300, 25, "FLIGHT NUMBER");
        final TextField departure = TextFieldFactory.getTextField(40, 20, 300, 25, "DEPARTURE");
        final TextField departureTime = TextFieldFactory.getTextField(70, 20, 300, 25, "DEPARTURE TIME");
        final TextField destination = TextFieldFactory.getTextField(100, 20, 300, 25, "DESTINATION");
        final TextField destinationTime = TextFieldFactory.getTextField(130, 20, 300, 25, "DESTINATION TIME");
        final TextField status = TextFieldFactory.getTextField(160, 20, 300, 25, "STATUS");
        final TextField delay = TextFieldFactory.getTextField(190, 20, 300, 25, "DELAY");
        root.getChildren().addAll(id, flightNumber, departure, departureTime, destination, destinationTime, status, delay);
    }

    private Data.Flight getFlightFromModal(final Pane root) {
        final List<Node> children = root.getChildren();
        final Data.Flight flight = new Data.Flight();

        for (final Node node : children) {
            if (node instanceof TextField && node.getId() != null) {
                switch (node.getId()) {
                    case "ID": {
                        flight.setId(((TextField) node).getText());
                    }
                    break;

                    case "FLIGHT NUMBER": {
                        flight.setNumber(((TextField) node).getText());
                    }
                    break;

                    case "DEPARTURE": {
                        flight.setFrom(((TextField) node).getText());
                    }
                    break;

                    case "DEPARTURE TIME": {
                        flight.setTimeFrom(((TextField) node).getText());
                    }
                    break;

                    case "DESTINATION": {
                        flight.setTo(((TextField) node).getText());
                    }
                    break;

                    case "DESTINATION TIME": {
                        flight.setTimeTo(((TextField) node).getText());
                    }
                    break;

                    case "STATUS": {
                        flight.setStatus(((TextField) node).getText());
                    }
                    break;

                    case "DELAY": {
                        flight.setStatus(((TextField) node).getText());
                    }
                    break;
                }
            }
        }
        return flight;
    }

    private void setFlightToModal(final Data.Flight flight, final Pane root) {
        final List<Node> children = root.getChildren();

        for (final Node node : children) {
            if (node instanceof TextField && node.getId() != null) {
                switch (node.getId()) {
                    case "ID": {
                        ((TextField) node).setText(flight.getId());
                    }
                    break;

                    case "FLIGHT NUMBER": {
                        ((TextField) node).setText(flight.getNumber());
                    }
                    break;

                    case "DEPARTURE": {
                        ((TextField) node).setText(flight.getFrom());
                    }
                    break;

                    case "DEPARTURE TIME": {
                        ((TextField) node).setText(flight.getTimeFrom());
                    }
                    break;

                    case "DESTINATION": {
                        ((TextField) node).setText(flight.getTo());
                    }
                    break;

                    case "DESTINATION TIME": {
                        ((TextField) node).setText(flight.getTimeTo());
                    }
                    break;

                    case "STATUS": {
                        ((TextField) node).setText(flight.getStatus());
                    }
                    break;

                    case "DELAY": {
                        ((TextField) node).setText(flight.getStatus());
                    }
                    break;
                }
            }
        }
    }
}

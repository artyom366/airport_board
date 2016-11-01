package lv.airport.board.ui.service;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lv.airport.board.controller.MainController;
import lv.airport.board.controller.MainControllerImpl;
import lv.airport.board.domain.Data;
import lv.airport.board.ui.components.ButtonFactory;

import java.util.ArrayList;
import java.util.List;

public class AppServiceImpl implements AppService {

    private final static float MAIN_HEIGHT = 500f;
    private final static float MAIN_WIDTH = 1013f;
    private final static float TABLE_HEIGHT = 450f;
    private final static float TABLE_WIDTH = 1013f;

    private final KeyCombination upKey = KeyCodeCombination.valueOf("UP");
    private final KeyCombination downKey = KeyCodeCombination.valueOf("DOWN");

    private TableService tableService;
    private ModalWindowService windowService;

    @Override
    public Scene buildApplication() {
        tableService = new TableServiceImpl();
        final IntegrationService integrationService = new IntegrationServiceImpl();

        final Pane root = new Pane();

        final TableView<Data.Flight> table = getTable();
        root.getChildren().add(table);

        final MainController mainController = new MainControllerImpl(table, integrationService);
        mainController.setFlights(createTestFlights());
        windowService = new ModalWindowServiceImpl(mainController, table);

        final Button addButton = getButton(10, 460, 25, 50, "Add");
        final Button updateButton = getButton(70, 460, 25, 50, "Update");
        updateButton.setDisable(true);
        final Button deleteButton = getButton(135, 460, 25, 50, "Delete");
        deleteButton.setDisable(true);
        final Button importButton = getButton(887, 460, 25, 50, "Import");
        final Button exportButton = getButton(950, 460, 25, 50, "Export");

        addTableEventListeners(updateButton, deleteButton, table);
        addButtonEventListeners(updateButton, deleteButton, addButton, importButton, exportButton, table);

        root.getChildren().addAll(addButton, updateButton, deleteButton, importButton, exportButton);
        return new Scene(root, MAIN_WIDTH, MAIN_HEIGHT);
    }

    private Button getButton(final float x, final float y, final float height, final float width, final String caption) {
        return ButtonFactory.getButton(x, y, height, width, caption);
    }

    private TableView<Data.Flight> getTable() {
        return tableService.getTable(TABLE_HEIGHT, TABLE_WIDTH);
    }

    private List<Data.Flight> createTestFlights() {
        final List<Data.Flight> testData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final Data.Flight flight = new Data.Flight();
            flight.setTimeTo("time" + i);
            flight.setTo("dest" + i);
            flight.setStatus("delay" + i);
            flight.setNumber("flight" + i);
            flight.setFrom("departure" + i);
            flight.setTimeFrom("time" + i);
            flight.setStatus("Status" + i);
            flight.setDelay("Delay" + i);
            testData.add(flight);
        }
        return testData;
    }

    private void addTableEventListeners(final Button updateButton, final Button deleteButton, final TableView<Data.Flight> table) {
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                handleUserInteraction(updateButton, deleteButton, table);
            }
        });

        table.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                if (upKey.getName().equalsIgnoreCase(event.getCode().toString()) ||
                        downKey.getName().equalsIgnoreCase(event.getCode().toString())) {
                    handleUserInteraction(updateButton, deleteButton, table);
                }
            }
        });
    }

    private void handleUserInteraction(final Button updateButton, final Button deleteButton, final TableView<Data.Flight> table) {
        final ObservableList<Data.Flight> selectedFlights = table.getSelectionModel().getSelectedItems();
        if (selectedFlights.size() == 1) {
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
        } else if (selectedFlights.size() > 1) {
            updateButton.setDisable(true);
        } else {
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

    private void addButtonEventListeners(final Button updateButton,
                                         final Button deleteButton,
                                         final Button addButton,
                                         final Button importButton,
                                         final Button refreshButton,
                                         final TableView<Data.Flight> table) {

        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                windowService.getUpdateModal();
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                windowService.getRemoveModal();
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                windowService.getAddModal();
            }
        });

        importButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                windowService.getImportModal();
            }
        });

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                windowService.getExportModal();
            }
        });
    }
}

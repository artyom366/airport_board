package lv.airport.board.ui.service;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lv.airport.board.domain.Data;
import lv.airport.board.ui.components.TableColumnFactory;

public class TableServiceImpl implements TableService {

    private final static String FLIGHT = "FLIGHT";
    private final static String DEPARTURE = "FROM";
    private final static String DEPARTURE_TIME = "START";
    private final static String DESTINATION = "DESTINATION";
    private final static String DESTINATION_TIME = "ARRIVAL";
    private final static String STATUS = "STATUS";
    private final static String DELAY = "DELAY";

    private final static float COLUMN_WIDTH_SMALL = 50f;
    private final static float COLUMN_WIDTH_NORMAL = 70f;
    private final static float COLUMN_WIDTH_LARGE = 334f;
    private TableView<Data.Flight> table;

    @Override
    public TableView<Data.Flight> getTable(final float height, final float width) {
        table = new TableView<>();
        table.setPrefSize(width, height);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        init();
        return table;
    }

    private void init() {
        TableColumn flightNumber = TableColumnFactory.<Data.Flight, String>getColumn(FLIGHT, "number", COLUMN_WIDTH_NORMAL);
        TableColumn departure = TableColumnFactory.<Data.Flight, String>getColumn(DEPARTURE, "from", COLUMN_WIDTH_LARGE);
        TableColumn departureTime = TableColumnFactory.<Data.Flight, String>getColumn(DEPARTURE_TIME, "timeFrom", COLUMN_WIDTH_NORMAL);
        TableColumn destination = TableColumnFactory.<Data.Flight, String>getColumn(DESTINATION, "to", COLUMN_WIDTH_LARGE);
        TableColumn destinationTime = TableColumnFactory.<Data.Flight, String>getColumn(DESTINATION_TIME, "timeTo", COLUMN_WIDTH_NORMAL);
        TableColumn status = TableColumnFactory.<Data.Flight, String>getColumn(STATUS, "status", COLUMN_WIDTH_NORMAL);
        TableColumn delay = TableColumnFactory.<Data.Flight, String>getColumn(DELAY, "delay", COLUMN_WIDTH_SMALL);
        table.getColumns().addAll(flightNumber, departure, departureTime, destination, destinationTime, status, delay);
    }
}

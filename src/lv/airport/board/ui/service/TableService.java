package lv.airport.board.ui.service;

import javafx.scene.control.TableView;
import lv.airport.board.domain.Data;

public interface TableService {
    TableView<Data.Flight> getTable(float height, float width);
}

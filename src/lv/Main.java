package lv;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import lv.airport.board.ui.service.AppService;
import lv.airport.board.ui.service.AppServiceImpl;

public class Main extends Application {

    private static final String TITLE = "Schedule";

    @Override
    public void start(Stage primaryStage) throws Exception {
        final AppService appService = new AppServiceImpl();

        Scene scene = appService.buildApplication();
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package lv.airport.board.ui.components;

import javafx.scene.control.TextField;

public class TextFieldFactory  {

    public static TextField getTextField(final float y, final float x, final float width, final float height, final String prompt) {
        final TextField textField = new TextField();
        textField.setLayoutY(y);
        textField.setLayoutX(x);
        textField.setPrefSize(width, height);
        textField.setPromptText(prompt);
        textField.setId(prompt);
        return textField;
    }
}

package com.teatroingressos.pi20251.util;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TextFieldHighlight {

    private TextFieldHighlight() {}

    public static void aplicarHighlightComLimite(TextField tf, int maxLength) {
        tf.setOnKeyTyped((KeyEvent event) -> {
            if (tf.getText().length() > 0 && tf.getText().length() < maxLength) {
                tf.setStyle("-fx-focus-color: red; -fx-text-box-border: red;");
            } else {
                tf.setStyle(null);
            }
        });
    }

    public static void aplicarHighlightCustomizado(TextField tf, String corHex) {
        tf.setStyle(String.format("-fx-focus-color: %s; -fx-text-box-border: %s;", corHex, corHex));
    }

    public static void limparHighlight(TextField tf) {
        tf.setStyle(null);
    }
}

package com.teatroingressos.pi20251.util;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DefaultStringConverter;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter.Change;

public class TextFieldLimiter {
    private static final String ONLY_NUMBER_PATTERN = "\\d*";
    private static final String NAME_PATTERN = "^[\\p{L} .'-]+$";
    private static final String CITY_PATTERN = "^[\\p{L} ]+$";

    private TextFieldLimiter() {}

    public static void limitarPorRegex(TextField tf, String regex) {
        UnaryOperator<Change> filter = change -> {
            String novoTexto = change.getControlNewText();
            return novoTexto.isEmpty() || novoTexto.matches(regex) ? change : null;
        };
        tf.setTextFormatter(new TextFormatter<>(filter));
    }

    public static void limitarNumeros(TextField tf, int maxLength) {
        UnaryOperator<Change> filter = change -> {
            String novoTexto = change.getControlNewText();
            return novoTexto.matches(ONLY_NUMBER_PATTERN) && novoTexto.length() <= maxLength ? change : null;
        };
        tf.setTextFormatter(new TextFormatter<>(filter));
    }

    public static void limitarNumerosFormatado(TextField tf, String tipo, int maxLength) {
        aplicarFormatador(tf, null, tipo, maxLength);
    }

    public static void limitarNumerosFormatadoComErro(TextField tf, Label lbl, String tipo, int maxLength) {
        aplicarFormatador(tf, lbl, tipo, maxLength);
    }

    private static void aplicarFormatador(TextField tf, Label lbl, String tipo, int maxLength) {
        UnaryOperator<Change> filter = change -> {
            if (change.isContentChange()) {
                TextFieldFormatter.ajustarBackspace(change);
                if (change.getText().matches(ONLY_NUMBER_PATTERN)) {
                    if (lbl != null) lbl.setText("");
                    int lenAntigo = change.getControlNewText().length();
                    change.setText(TextFieldFormatter.formatar(tipo, change.getControlNewText(), maxLength));
                    change.setRange(0, change.getControlText().length());
                    int offset = change.getControlNewText().length() - lenAntigo;
                    change.setCaretPosition(change.getCaretPosition() + offset);
                    change.setAnchor(change.getAnchor() + offset);
                    return change;
                }
                return null;
            }
            return change;
        };
        tf.setTextFormatter(new TextFormatter<>(new DefaultStringConverter(), "", filter));
    }

    public static String getNamePattern() { return NAME_PATTERN; }
    public static String getCityPattern() { return CITY_PATTERN; }
}

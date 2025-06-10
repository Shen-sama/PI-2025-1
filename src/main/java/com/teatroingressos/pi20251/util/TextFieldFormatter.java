package com.teatroingressos.pi20251.util;

import javafx.scene.control.TextFormatter;

public class TextFieldFormatter {

    private TextFieldFormatter() {}

    public static void ajustarBackspace(TextFormatter.Change change) {
        if (change.isDeleted() && change.getSelection().getLength() == 0) {
            if (!Character.isDigit(change.getControlText().charAt(change.getRangeStart()))) {
                if (change.getRangeStart() > 0) {
                    change.setRange(change.getRangeStart() - 1, change.getRangeEnd() - 1);
                }
            }
        }
    }

    public static String formatar(String tipo, String texto, int maxLength) {
        texto = texto.replaceAll("[^\\d]", "");
        texto = texto.substring(0, Math.min(maxLength, texto.length()));

        switch (tipo) {
            case "CPF":
                return formatarCPF(texto);
            case "CEP":
                return formatarCEP(texto);
            case "Telefone":
                return formatarTelefone(texto);
            default:
                return texto;
        }
    }

    private static String formatarCPF(String texto) {
        if (texto.length() <= 3)
            return texto;
        if (texto.length() <= 6)
            return texto.replaceFirst("(\\d{3})(\\d+)", "$1.$2");
        if (texto.length() <= 9)
            return texto.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1.$2.$3");
        return texto.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d+)", "$1.$2.$3-$4");
    }

    private static String formatarCEP(String texto) {
        if (texto.length() <= 5)
            return texto;
        return texto.replaceFirst("(\\d{5})(\\d+)", "$1-$2");
    }

    private static String formatarTelefone(String texto) {
        if (texto.length() <= 2)
            return "(" + texto;
        if (texto.length() <= 6)
            return texto.replaceFirst("(\\d{2})(\\d+)", "($1) $2");
        return texto.replaceFirst("(\\d{2})(\\d{5})(\\d+)", "($1) $2-$3");
    }
}

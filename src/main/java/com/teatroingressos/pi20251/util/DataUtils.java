package com.teatroingressos.pi20251.util;

import java.time.LocalDate;
import java.time.Period;

public class DataUtils {

    private DataUtils() {}

    public static boolean validarDataNascimento(LocalDate data, int idadeMinima, int idadeMaxima) {
        if (data == null) return false;

        LocalDate hoje = LocalDate.now();
        if (data.isAfter(hoje)) return false;

        int idade = Period.between(data, hoje).getYears();

        return idade >= idadeMinima && idade <= idadeMaxima;
    }
}

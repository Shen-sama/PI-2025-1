package com.teatroingressos.pi20251.util;

import com.teatroingressos.pi20251.model.domain.Poltrona;

import java.util.HashMap;
import java.util.Map;

public class PoltronaUtils {

    public static Map<String, Poltrona> gerarPoltronas(int fileiras, int colunas) {

        Map<String, Poltrona> poltronas = new HashMap<>();

        for (int i = 0; i < fileiras; i++) {
            char letra = (char) ('A' + i);
            for (int j = 1; j <= colunas; j++) {
                String codigo = letra + String.valueOf(j);
                poltronas.put(codigo, new Poltrona(codigo));
            }
        }

        return poltronas;
    }
}

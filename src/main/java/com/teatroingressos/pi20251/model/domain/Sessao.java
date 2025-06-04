package com.teatroingressos.pi20251.model.domain;

import java.time.LocalDateTime;
import java.util.Map;

public class Sessao {
    private LocalDateTime horario;
    private Sala sala;
    private Map<String, Ingresso> ingressos;
}

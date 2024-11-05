package com.lpm.XHRRentals.DTO;

import java.time.LocalDate;

public record AluguelDTO(int idAluguel,
                         LocalDate inicioAluguel,
                         int duracaoAluguel,
                         EquipamentoDTO equipamento,
                         double valorDiaria) {
}

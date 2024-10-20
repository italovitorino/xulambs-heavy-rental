package com.lpm.XHRRentals.DTO;

import com.lpm.XHRRentals.Models.Equipamento;

import java.time.LocalDate;

public record AluguelDTO(int idAluguel,
                         LocalDate inicioAluguel,
                         int duracaoAluguel,
                         EquipamentoDTO equipamento,
                         double valorDiaria) {
}

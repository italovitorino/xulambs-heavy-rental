package com.lpm.XHRRentals.DTO;

import com.lpm.XHRRentals.Models.Filial;

public record EquipamentoDTO(long idEquipamento,
                             String descricao,
                             int duracaoMaxima,
                             double valorDiaria,
                             double descontoSemanal,
                             double totalArrecadado,
                             FilialDTO filial) {
}

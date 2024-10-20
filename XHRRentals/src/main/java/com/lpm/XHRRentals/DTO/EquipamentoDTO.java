package com.lpm.XHRRentals.DTO;

public record EquipamentoDTO(long idEquipamento,
                             String descricao,
                             int duracaoMaxima,
                             double valorDiaria,
                             double descontoSemanal,
                             double totalArrecadado) {
}

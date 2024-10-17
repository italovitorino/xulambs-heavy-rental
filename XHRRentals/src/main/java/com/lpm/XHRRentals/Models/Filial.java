package com.lpm.XHRRentals.Models;

import java.util.List;

import com.lpm.XHRRentals.DTO.FilialDTO;

import java.util.LinkedList;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "filiais")
public class Filial {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String nomeFilial;

  @OneToMany
  private List<Equipamento> equipamentos;

  public Filial(){}

  public Filial(String nome) {
    if (nome.equals("")) {
      nomeFilial = "Filial" + id;
    }

    nomeFilial = nome;
    equipamentos = new LinkedList<>();
  }

  public int cadastrarEquipamento(Equipamento novo) {
    equipamentos.add(novo);
    return equipamentos.size();
  }

  public Equipamento buscarEquipamento(String descricao) {
    Equipamento equipamento = null;

    for (Equipamento e : equipamentos) {
      if (e.getDescricao().equals(descricao)) {
        equipamento = e;
      }
    }

    return equipamento;
  }

  public Equipamento maiorArrecadacao() {
    Equipamento equipamento = null;

    for (Equipamento e : equipamentos) {
      if (e.totalArrecadado() > equipamento.totalArrecadado()) {
        equipamento = e;
      }
    }

    return equipamento;
  }

  public Equipamento maiorArrecadacao(String equipamento1, String equipamento2) {
    Equipamento e1 = buscarEquipamento(equipamento1);
    Equipamento e2 = buscarEquipamento(equipamento2);
    Equipamento maior = e1;

    if (e2 != null && e2.totalArrecadado() > e1.totalArrecadado()) {
      maior = e2;
    }

    return maior;
  }

  public String relatorioFilial() {
    StringBuilder relatFilial = new StringBuilder();
    relatFilial.append(String.format("ID: %ld", id));
    relatFilial.append(String.format("Filial: %s", nomeFilial));

    return relatFilial.toString();
  }

  public FilialDTO gerarDTO() {
    return new FilialDTO(id, nomeFilial);
  }
}

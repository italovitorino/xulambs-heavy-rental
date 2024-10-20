package com.lpm.XHRRentals.Models;

import com.lpm.XHRRentals.DTO.FilialDTO;
import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Representa uma filial que possui um identificador único, nome e uma lista de equipamentos disponíveis.
 * A classe gerencia o cadastro de equipamentos, permite buscar e comparar arrecadações,
 * gera relatórios da filial e cria objetos {@link FilialDTO} para transferência de dados.
 */

@Entity
@Table(name = "filiais")
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFilial;

    @OneToMany(mappedBy = "filial")
    private List<Equipamento> equipamentos;

    public Filial() {
    }

    /**
     * Construtor principal da classe.
     *
     * @param nome o nome da filial a ser criada.
     */
    public Filial(String nome) {
        if (nome.equals("")) {
            nomeFilial = "Filial" + id;
        }

        nomeFilial = nome;
        equipamentos = new LinkedList<>();
    }

    /**
     * Adiciona o equipamento à lista de equipamentos da filial.
     *
     * @param novo o equipamento a ser cadastrado na filial.
     * @return o total de equipamentos cadastrados após a adição.
     */
    public int cadastrarEquipamento(Equipamento novo) {
        equipamentos.add(novo);
        return equipamentos.size();
    }

    /**
     * Busca um equipamento na lista de equipamentos da filial, com base em sua descrição (case-sensitive).
     *
     * @param descricao a descrição do equipamento procurado.
     * @return o equipamento encontrado com a descrição correspondente ou {@code null} se não for localizado.
     */
    public Equipamento buscarEquipamento(String descricao) {
        Equipamento equipamento = null;

        for (Equipamento e : equipamentos) {
            if (e.getDescricao().equals(descricao)) {
                equipamento = e;
            }
        }

        return equipamento;
    }

    /**
     * Retorna o equipamento com maior arrecadação total da lista de equipamentos da filial.
     *
     * @return o equipamento com a maior arrecação total.
     */
    public Equipamento maiorArrecadacao() {
        Equipamento equipamento = null;

        if (!equipamentos.isEmpty()) {
            equipamento = equipamentos.get(0);

            for (Equipamento e : equipamentos) {
                if (e.totalArrecadado() > equipamento.totalArrecadado()) {
                    equipamento = e;
                }
            }
        }

        return equipamento;
    }

    /**
     * Verifica, entre dois equipamentos, qual possui a maior arrecadação total.
     *
     * @param equipamento1 descrição do primeiro equipamento a ser comparado.
     * @param equipamento2 descrição do segundo equipamento a ser comparado.
     * @return o equipamento com a maior arrecadação total, ou {@code null} se ambos não forem encontrados.
     */
    public Equipamento maiorArrecadacao(String equipamento1, String equipamento2) {
        Equipamento e1 = buscarEquipamento(equipamento1);
        Equipamento e2 = buscarEquipamento(equipamento2);
        Equipamento maior = e1;

        if (e2 != null && e2.totalArrecadado() > e1.totalArrecadado()) {
            maior = e2;
        }

        return maior;
    }

    /**
     * Gera um relatório com os dados da filial.
     *
     * @return uma string formatada contendo o relatório da filial.
     */
    public String relatorioFilial() {
        StringBuilder relatFilial = new StringBuilder();
        relatFilial.append(String.format("ID: %ld", id));
        relatFilial.append(String.format("Filial: %s", nomeFilial));

        return relatFilial.toString();
    }

    /**
     * Gera um objeto {@code FilialDTO} com as informações da filial.
     *
     * @return uma instância de {@code FilialDTO} contendo o ID e o nome da filial
     */
    public FilialDTO gerarDTO() {
        return new FilialDTO(id, nomeFilial);
    }
}

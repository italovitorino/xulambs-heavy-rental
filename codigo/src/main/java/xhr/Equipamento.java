package xhr;

import java.text.NumberFormat;
import java.time.LocalDate;

/**
 * Representa um equipamento disponível para {@link Aluguel}. Cada equipamento
 * possui um
 * identificador único, descrição, duração máxima de aluguel, valor da diária,
 * desconto semanal e registra o total arrecadado com os aluguéis. A classe
 * gerencia a disponibilidade do equipamento, permite realizar aluguéis
 * respeitando as condições estabelecidas, calcula o valor da diária com base na
 * duração e disponibiliza dados detalhados do equipamento e seu histórico de
 * aluguéis.
 */
public class Equipamento {

    private static int TAM_MAX_HISTORICO = 100;
    private static int ultimoId = 0;
    private int id;
    private Aluguel[] historico;
    private int posHistorico;
    private String descricao;
    private int duracaoMaxima;
    private double valorDiaria;
    private double descontoSemanal;
    private double totalArrecadado;

    /**
     * Construtor para criar um novo equipamento. Se os parâmetros forem inválidos,
     * serão aplicados valores padrão:
     * - Descrição: "Equipamento" (se tiver menos de 5 caracteres)
     * - Duração máxima: 7 (se for menor ou igual a 0)
     * - Valor da diária: 10 (se for menor ou igual a 0)
     * - Desconto: 0 (se for um valor fora do intervalo entre 0 e 10)
     *
     * @param descricao     Descrição do equipamento, deve ter pelo menos 5
     *                      caracteres.
     * @param diaria        Valor da diária, deve ser maior que 0.
     * @param duracaoMaxima Duração máxima do aluguel, deve ser maior que 0.
     * @param desconto      Desconto, em porcentagem. Deve estar entre 0 e 10.
     */
    public Equipamento(String descricao, double diaria, int duracaoMaxima, double desconto) {
        this.id = ++ultimoId;
        this.historico = new Aluguel[TAM_MAX_HISTORICO];
        this.posHistorico = 0;
        this.descricao = (descricao.length() >= 5) ? descricao : descricao + id;
        this.duracaoMaxima = duracaoMaxima > 0 ? duracaoMaxima : 7;
        this.valorDiaria = diaria > 0 ? diaria : 10d;
        this.descontoSemanal = desconto >= 0 ? desconto <= 10 ? desconto / 100 : 0d : 0d;
        this.totalArrecadado = 0d;
    }

    /**
     * Retorna a descrição do equipamento.
     *
     * @return A descrição do equipamento
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Verifica se o equipamento está disponível para locação em uma data
     * específica.
     *
     * @param data A data a ser verificada.
     * @return {@code true} se o equipamento estiver disponível na data informada,
     * {@code false} se indisponível.
     */
    public boolean estaDisponivelEm(LocalDate data) {
        boolean disponivel = true;

        for (int i = 0; i < posHistorico; i++) {
            Aluguel aluguel = historico[i];

            if (aluguel.incluiData(data)) {
                disponivel = false;
                break;
            }
        }

        return disponivel;
    }

    /**
     * Aluga o equipamento a partir de uma data de início e por uma duração
     * específica, se as condições de disponibilidade e duração máxima forem
     * atendidas.
     *
     * @param inicio         Data de início do aluguel.
     * @param duracaoAluguel Duração do aluguel em dias. Deve ser maior que 0 e
     *                       menor ou igual à duração máxima permitida pelo
     *                       equipamento.
     * @return Um objeto {@link Aluguel} representando o aluguel realizado, ou
     * {@code null} se o aluguel não puder ser efetuado (duração inválida ou
     * falta de disponibilidade).
     */
    public Aluguel alugar(LocalDate inicio, int duracaoAluguel) {
        if (duracaoAluguel <= 0 || duracaoAluguel > this.duracaoMaxima)
            return null;

        for (int i = 0; i < duracaoAluguel; i++) {
            if (!estaDisponivelEm(inicio.plusDays(i)))
                return null;
        }

        Aluguel aluguel = new Aluguel(this, inicio, duracaoAluguel);
        historico[posHistorico] = aluguel;
        posHistorico++;
        this.totalArrecadado += valorDiario(duracaoAluguel) * duracaoAluguel;

        return aluguel;
    }

    /**
     * Retorna o valor total arrecadado com aluguéis.
     *
     * @return O valor total arrecadado com aluguéis.
     */
    public double totalArrecadado() {
        return totalArrecadado;
    }

    /**
     * Calcula o valor diário do aluguel com base na quantidade de dias.
     *
     * @param quantDias A quantidade de dias do aluguel. Deve ser um número inteiro
     *                  positivo.
     * @return O valor da diária do equipamento.
     */
    public double valorDiario(int quantDias) {
        return quantDias > 7 ? (valorDiaria - (valorDiaria * descontoSemanal)) : valorDiaria;
    }

    /**
     * Retorna uma string formatada com os dados detalhados do equipamento,
     * incluindo descrição, valor da diária, desconto semanal (em %) e o total
     * arrecadado.
     *
     * @return Uma string com os dados do equipamento.
     */
    public String dadosEquipamento() {
        NumberFormat moeda = NumberFormat.getCurrencyInstance();
        StringBuilder relatEquipamento = new StringBuilder();

        relatEquipamento.append(String.format("ID: %d\n", id));
        relatEquipamento.append(String.format("Descrição: %s\n", descricao));
        relatEquipamento.append(String.format("Valor da diária: %s\n", moeda.format(valorDiaria)));
        relatEquipamento.append(String.format("Duração máxima: %d\n", duracaoMaxima));
        relatEquipamento.append(String.format("Desconto semanal: %.2f%%\n", descontoSemanal * 100));
        relatEquipamento.append(String.format("Total arrecadado: %s\n", moeda.format(totalArrecadado)));

        return relatEquipamento.toString();
    }

    /**
     * Retorna uma string formatada com o histórico de aluguéis do equipamento,
     * incluindo data de início, data de término, valor da diária, valor total por
     * aluguel e o total geral arrecadado.
     *
     * @return Uma string com os dados do historico de aluguéis do equipamento.
     */
    public String relatorioAlugueis() {
        NumberFormat moeda = NumberFormat.getCurrencyInstance();
        StringBuilder relatAlugueis = new StringBuilder();

        for (int i = 0; i < posHistorico; i++) {
            Aluguel aluguel = historico[i];

            relatAlugueis.append(String.format("%s\n", aluguel.relatorio()));
            relatAlugueis.append("--------------------------------------------------\n");
        }

        relatAlugueis.append(String.format("Total arrecadado: %s\n", moeda.format(totalArrecadado)));

        return relatAlugueis.toString();
    }
}

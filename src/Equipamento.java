import java.text.NumberFormat;
import java.time.LocalDate;

/**
 * Representa um equipamento disponível para aluguel.
 * Cada equipamento possui um identificador único, descrição, valor da diária,
 * duração máxima de aluguel, e mantém o total arrecadado com os aluguéis. A
 * classe permite verificar se o equipamento está disponível, realizar
 * aluguéis dentro das condições permitidas, calcular o valor do aluguel com
 * base na duração e exibir dados do equipamento.
 */
public class Equipamento {

  private static int ultimoId = 0;
  private int id;
  private Aluguel[] historico;
  private String descricao;
  private int duracaoMaxima;
  private double valorDiaria;
  private double descontoSemanal;
  private double totalArrecadado;

  /**
   * Construtor para criar um novo equipamento. Se os parâmetros forem inválidos,
   * serão aplicados valores padrão:
   * - Descrição: "Equipamento" (se tiver menos de 5 caracteres)
   * - Valor da diária: 10 (se for menor ou igual a 0)
   * - Duração máxima: 7 (se for menor ou igual a 0)
   * - Desconto: 0 (se não estiver entre 0 e 10)
   *
   * @param descricao     Descrição do equipamento, deve ter pelo menos 5
   *                      caracteres.
   * @param diaria        Valor da diária, deve ser maior que 0.
   * @param duracaoMaxima Duração máxima do aluguel, deve ser maior que 0.
   * @param desconto      Desconto, em porcentagem, se determinado aluguel passar de 7 dias. Deve estar entre 0 e 10.
   */
  public Equipamento(String descricao, double diaria, int duracaoMaxima, double desconto) {
    this.id = ++ultimoId;
    this.descricao = (descricao.length() >= 5) ? descricao : "Equipamento";
    this.valorDiaria = diaria > 0 ? diaria : 10d;
    this.duracaoMaxima = duracaoMaxima > 0 ? duracaoMaxima : 7;
    this.descontoSemanal = desconto > 0 ? desconto <= 10 ? desconto / 100 : 0d : 0d;
    this.totalArrecadado = 0d;
  }

  /**
   * Verifica se o equipamento está disponível para locação em uma data
   * específica.
   * 
   * @param data A data a ser verificada.
   * @return {@code true} se o equipamento estiver disponível na data informada,
   *         {@code false} se indisponível.
   */
  public boolean estaDisponivelEm(LocalDate data) {
    boolean disponivel = true;

    for (Aluguel aluguel : historico) {
      if (aluguel.incluiData(data)) {
        disponivel = false;
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
   * @return {@code true} se o aluguel for realizado com sucesso; {@code false} se
   *         o equipamento não estiver disponível ou se a duração do aluguel é
   *         inválida.
   */
  public Aluguel alugar(LocalDate inicio, int duracaoAluguel) {
    if (duracaoAluguel > 0 && duracaoAluguel <= this.duracaoMaxima && estaDisponivelEm(inicio)) {
      
      this.totalArrecadado += valorDiaria * duracaoAluguel;
      
    }
  }

  public double totalArrecadado() {
    return totalArrecadado;
  }

  public double valorDiario(int quantDias) {
    //
  }

  /**
   * Retorna uma string formatada com os dados detalhados do equipamento,
   * incluindo descrição, valor da diária, informações da última locação e o total
   * arrecadado.
   *
   * @return Uma string com os dados do equipamento.
   */
  public String dadosEquipamento() {
    NumberFormat moeda = NumberFormat.getCurrencyInstance();
    StringBuilder relatEquipamento = new StringBuilder();

    relatEquipamento.append(String.format("Descrição: %s\n", descricao));
    relatEquipamento.append(String.format("Valor da diária: %s\n", moeda.format(valorDiaria)));
    relatEquipamento.append(String.format("Total arrecadado: %s\n", moeda.format(totalArrecadado)));

    return relatEquipamento.toString();
  }

  public String relatorioAlugueis() {
    //
  }
}

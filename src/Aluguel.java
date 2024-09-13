import java.text.NumberFormat;
import java.time.LocalDate;

/**
 * Representa um aluguel de um {@link Equipamento}, contendo informações sobre a data de
 * início, duração, equipamento alugado e o valor diário do aluguel. A classe
 * gerencia o cálculo do valor total do aluguel, verifica se uma data está
 * dentro do período do aluguel e exibe dados detalhados sobre o aluguel
 * realizado.
 */
public class Aluguel {

  private LocalDate inicioAluguel;
  private int duracaoAluguel;
  private Equipamento equipamento;
  private double valorDiario;

  public Aluguel(Equipamento equipamento, LocalDate inicio, int duracao) {
    this.inicioAluguel = inicio;
    this.duracaoAluguel = duracao;
    this.equipamento = equipamento;
    this.valorDiario = equipamento.valorDiario(duracao);
  }

  /**
   * Calcula o valor total do aluguel.
   * 
   * @return O valor total do aluguel.
   */
  public double valorAluguel() {
    return valorDiario * duracaoAluguel;
  }

  /**
   * Retorna uma string formata com os dados do aluguel, incluindo data de início,
   * data de término, valor da diária e valor total do aluguel.
   * 
   * @return Uma string com os dados do aluguel.
   */
  public String relatorio() {
    NumberFormat moeda = NumberFormat.getCurrencyInstance();
    StringBuilder relatorio = new StringBuilder();

    relatorio.append(String.format("Início: %s\n", inicioAluguel));
    relatorio.append(String.format("Término: %s\n", inicioAluguel.plusDays(duracaoAluguel)));
    relatorio.append(String.format("Valor diária: %s\n", moeda.format(valorDiario)));
    relatorio.append(moeda.format(valorAluguel()));

    return relatorio.toString();
  }

  /**
   * Verifica se a data especificada está dentro do período de aluguel,
   * considerando a data de início e a duração.
   * 
   * @param data A data a ser verificada.
   * @return {@code true} se a data estiver dentro do intervalo do aluguel,
   *         {@code false} caso contrário.
   */
  public boolean incluiData(LocalDate data) {
    if (inicioAluguel == null)
      return false;

    LocalDate fimAluguel = inicioAluguel.plusDays(duracaoAluguel);
    return !data.isBefore(inicioAluguel) && !data.isAfter(fimAluguel);
  }
}

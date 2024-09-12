import java.time.LocalDate;

public class Aluguel {

  private LocalDate inicioAluguel;
  private int duracaoAluguel;
  private Equipamento equipamento;
  private double valorDiario;

  public Aluguel(Equipamento equipamento, LocalDate inicio, int duracao) {
    this.inicioAluguel = inicio;
    this.duracaoAluguel = duracaoAluguel;
  }

  public double valorAluguel() {
    return valorDiario * duracaoAluguel;
  }

  public String relatorio() {
    //
  }

  public boolean incluiData(LocalDate data) {
    if (inicioAluguel == null)
      return false;

    LocalDate fimAluguel = inicioAluguel.plusDays(duracaoAluguel);
    return !data.isBefore(inicioAluguel) && !data.isAfter(fimAluguel);
  }
}

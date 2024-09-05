import java.time.LocalDate;

public class Equipamento {

  private static int ultimoId = 0;
  private int id;
  private String descricao;
  private LocalDate inicioAluguel;
  private int duracaoAluguel;
  private int duracaoMaxima;
  private double valorDiaria;
  private double totalArrecadado;

  public Equipamento(String descricao, double diaria, int duracaoMaxima) {
    if (descricao.length() >= 5 && diaria > 0 && duracaoMaxima > 0) {
      this.id = ++ultimoId;
      this.descricao = descricao;
      this.duracaoMaxima = duracaoMaxima;
      this.valorDiaria = diaria;
      this.totalArrecadado = 0d;
    }
  }

  public boolean estaDisponivelEm(LocalDate data) {
    LocalDate fimAluguel = inicioAluguel.plusDays(duracaoAluguel);
    return fimAluguel.isBefore(data);
  }

  public boolean alugar(LocalDate inicio, int duracaoAluguel) {
    if (duracaoAluguel <= this.duracaoMaxima && estaDisponivelEm(inicio)) {
      this.inicioAluguel = inicio;
      this.duracaoAluguel = duracaoAluguel;
      this.totalArrecadado += valorDiaria * duracaoAluguel;
      return true;
    }

    return false;
  }

  public double valorAluguel() {
    return valorDiaria * duracaoAluguel;
  }

  // public String dadosEquipamento() {

  // }
}

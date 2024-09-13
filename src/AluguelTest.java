import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AluguelTest {
  static Equipamento equipamento;
  static Aluguel aluguel;
  static Aluguel aluguel2;

  @BeforeEach
  public void setUp() {
    equipamento = new Equipamento("Betoneira", 50, 30, 10);
    aluguel = new Aluguel(equipamento, LocalDate.of(2024, 12, 12), 10);
  }

  @Test
  public void valorDoAluguel() {
    assertEquals(450, aluguel.valorAluguel(), 0.01);
  }

  @Test
  public void relatorioDoAluguel() {
    String relatAluguel = aluguel.relatorio();
    assertTrue(relatAluguel.contains("2024-12-12"));
    assertTrue(relatAluguel.contains("2024-12-22"));
    assertTrue(relatAluguel.contains("45,00"));
    assertTrue(relatAluguel.contains("450,00"));
  }

  @Test
  public void naoIncluiData() {
    assertFalse(aluguel.incluiData(LocalDate.of(2024, 12, 11)));
    assertFalse(aluguel.incluiData(LocalDate.of(2024, 12, 23)));
  }

  @Test
  public void incluiData() {
    assertTrue(aluguel.incluiData(LocalDate.of(2024, 12, 12)));
    assertTrue(aluguel.incluiData(LocalDate.of(2024, 12, 22)));
  }
}

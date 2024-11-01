package xhr;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class XHRAlugueis {
  private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private static final NumberFormat FORMATO_MOEDA = NumberFormat.getCurrencyInstance();
  private static final Scanner SCANNER = new Scanner(System.in);
  private static final String NOME_ARQ = "dados.csv";
  private static final String FIM_ARQ = "FIM";
  private static List<Equipamento> EQUIPAMENTOS = new LinkedList<>();

  private static void cabecalho(String titulo) {
    System.out.println("\n" + titulo);
    System.out.println("=".repeat(titulo.length()));
  }

  private static void limparTela() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private static void pausar() {
    System.out.println("\nPressione ENTER para continuar...");
    SCANNER.nextLine();
  }

  private static Equipamento localizarEquipamento(String descricao, List<Equipamento> equipamentos) {
    for (Equipamento equipamento : equipamentos) {
      if (equipamento.getDescricao().equalsIgnoreCase(descricao)) {
        return equipamento;
      }
    }
    return null;
  }

  private static void lerDadosAlugueis(Scanner dados, List<Equipamento> equipamentos) throws FileNotFoundException {
    String linha = dados.nextLine();
    Equipamento equipamento = null;

    while (!linha.equals(FIM_ARQ)) {
      String detalhes[] = linha.split(";");
      equipamento = localizarEquipamento(detalhes[0], equipamentos);
      equipamento.alugar(LocalDate.parse(detalhes[1], FORMATO_DATA), Integer.parseInt(detalhes[2]));
      linha = dados.nextLine();
    }
  }

  private static List<Equipamento> lerEquipamentos(String nomeArq) throws FileNotFoundException {
    Scanner dados = new Scanner(new File(nomeArq));
    String linha;
    int quantidade = Integer.parseInt(dados.nextLine());
    List<Equipamento> equipamentos = new LinkedList<>();

    for (int i = 0; i < quantidade; i++) {
      linha = dados.nextLine();
      String[] detalhes = linha.split(";");
      equipamentos.add(new Equipamento(detalhes[0], 
                           Double.parseDouble(detalhes[1]), 
                           Integer.parseInt(detalhes[2]),
                           Double.parseDouble(detalhes[3])));
    }

    lerDadosAlugueis(dados, equipamentos);
    dados.close();
    return equipamentos;
  }

  private static boolean descricaoExistente(String descricao, List<Equipamento> equipamentos) {
    for (Equipamento equipamento : equipamentos) {
      if (equipamento.getDescricao().equalsIgnoreCase(descricao)) {
        return true;
      }
    }
    return false;
  }

  private static Equipamento cadastrarEquipamento() {
    int duracaoMaxima;
    double valorDiaria, descontoSemanal;
    String descricao;
    
    System.out.print("Descrição: ");
    descricao = SCANNER.nextLine();
    if (descricaoExistente(descricao, EQUIPAMENTOS)) {
      descricao += EQUIPAMENTOS.size() + 1;
    }
    
    System.out.print("Valor diário: ");
    valorDiaria = Double.parseDouble(SCANNER.nextLine().replace(",", "."));
    
    System.out.print("Duração máxima: ");
    duracaoMaxima = Integer.parseInt(SCANNER.nextLine());
    
    System.out.print("Desconto semanal: ");
    descontoSemanal = Double.parseDouble(SCANNER.nextLine().replace(",", "."));
    return new Equipamento(descricao, valorDiaria, duracaoMaxima, descontoSemanal);
  }

  private static void listarEquipamentos(List<Equipamento> equipamentos) {
    for (Equipamento equipamento : equipamentos) {
      System.out.println(equipamento.dadosEquipamento());
    }
  }

  private static Equipamento pesquisarEquipamento(List<Equipamento> equipamentos) {
    String descricao;
    
    System.out.print("Descrição: ");
    descricao = SCANNER.nextLine();

    return localizarEquipamento(descricao, equipamentos);
  }

  private static Aluguel alugarEquipamento(Equipamento equipamento) {
    LocalDate inicio;
    int duracaoAluguel;
    
    System.out.print("Data de início (dd/mm/aaaa): ");
    inicio = LocalDate.parse(SCANNER.nextLine(), FORMATO_DATA);
    
    System.out.print("Duração do aluguel (dias): ");
    duracaoAluguel = Integer.parseInt(SCANNER.nextLine());
    
    return equipamento.alugar(inicio, duracaoAluguel);
  }

  private static Equipamento maiorArrecadacao(List<Equipamento> equipamentos) {
    Equipamento equipamentoMaiorArrecadacao = equipamentos.get(0);
    for (Equipamento equipamento : equipamentos) {
      if (equipamento.totalArrecadado() > equipamentoMaiorArrecadacao.totalArrecadado()) {
        equipamentoMaiorArrecadacao = equipamento;
      }
    }
    return equipamentoMaiorArrecadacao;
  }

  private static double arrecadacaoTotal(List<Equipamento> equipamentos) {
    double total = 0d;
    for (Equipamento equipamento : equipamentos) {
      total += equipamento.totalArrecadado();
    }
    return total;
  }
  
  private static int menu() {
    System.out.println("1 - Cadastrar equipamento");
    System.out.println("2 - Listar equipamentos");
    System.out.println("3 - Pesquisar equipamento");
    System.out.println("4 - Alugar equipamento");
    System.out.println("5 - Exibir equipamento com maior arrecadação");
    System.out.println("6 - Exibir arrecadação total");
    System.out.println("0 - Sair");
    System.out.print("Opção: ");
    return Integer.parseInt(SCANNER.nextLine());
  }

  private static int menuEquipamento() {
    System.out.println("1 - Alugar equipamento");
    System.out.println("2 - Exibir dados do equipamento");
    System.out.println("3 - Exibir relatório de aluguéis");
    System.out.println("4 - Exibir total arrecadado");
    System.out.println("0 - Voltar");
    System.out.print("Opção: ");
    return Integer.parseInt(SCANNER.nextLine());
  }

  public static void main(String[] args) throws Exception {
    int opcao;
    boolean sair = false, voltar = false;
    Equipamento equipamento;
    Aluguel aluguel;

    EQUIPAMENTOS = lerEquipamentos(NOME_ARQ);

    while (!sair) {
      limparTela();
      cabecalho("XULAMBS HEAVY RENTAL");
      opcao = menu();
      switch (opcao) {
        case 1:
          limparTela();
          cabecalho("CADASTRO DE EQUIPAMENTO");
          equipamento = cadastrarEquipamento();
          EQUIPAMENTOS.add(equipamento);
          System.out.print("\n" + equipamento.dadosEquipamento());
          pausar();
          break;
        case 2:
          limparTela();
          cabecalho("LISTA DE EQUIPAMENTOS");
          listarEquipamentos(EQUIPAMENTOS);
          pausar();
          break;
        case 3:
          limparTela();
          cabecalho("PESQUISA DE EQUIPAMENTO");
          equipamento = pesquisarEquipamento(EQUIPAMENTOS);
          if (equipamento != null) {
            voltar = false;
            while (!voltar) {
              limparTela();
              cabecalho("EQUIPAMENTO ENCONTRADO");
              opcao = menuEquipamento();
              switch (opcao) {
                case 1:
                  limparTela();
                  cabecalho("ALUGUEL DE EQUIPAMENTO");
                  aluguel = alugarEquipamento(equipamento);
                  if (aluguel != null) {
                    System.out.println("\n" + aluguel.relatorio());
                    pausar();
                  } else {
                    System.out.println("Aluguel não efetuado.");
                    pausar();
                  }
                  break;
                case 2:
                  limparTela();
                  cabecalho("DADOS DO EQUIPAMENTO");
                  System.out.println(equipamento.dadosEquipamento());
                  pausar();
                  break;
                case 3:
                  limparTela();
                  cabecalho("RELATÓRIO DE ALUGUÉIS");
                  System.out.println(equipamento.relatorioAlugueis());
                  pausar();
                  break;
                case 4:
                  System.out.println("Total arrecadado: " + FORMATO_MOEDA.format(equipamento.totalArrecadado()));
                  pausar();
                  break;
                case 0:
                  voltar = true;
                  break;
              } 
            }
          } else {
            System.out.println("Equipamento não encontrado.");
            pausar();
          }
          break;
        case 4:
          limparTela();
          cabecalho("ALUGUEL DE EQUIPAMENTO");
          equipamento = pesquisarEquipamento(EQUIPAMENTOS);
          if (equipamento != null) {
            aluguel = alugarEquipamento(equipamento);
            if (aluguel != null) {
              System.out.println("\n" + aluguel.relatorio());
              pausar();
            } else {
              System.out.println("Aluguel não efetuado.");
              pausar();
            }
          } else {
            System.out.println("Equipamento não encontrado.");
            pausar();
          }
          break;
        case 5:
          limparTela();
          cabecalho("EQUIPAMENTO COM MAIOR ARRECADAÇÃO");
          equipamento = maiorArrecadacao(EQUIPAMENTOS);
          System.out.println(equipamento.dadosEquipamento());
          pausar();
          break;
        case 6:
          System.out.println("Arrecadação total: " + FORMATO_MOEDA.format(arrecadacaoTotal(EQUIPAMENTOS)));
          pausar();
          break;
        case 0:
          System.out.println("Finalizando o sistema...");
          SCANNER.close();
          sair = true;
          break;
      }
    }
  }
}

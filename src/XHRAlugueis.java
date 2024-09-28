import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class XHRAlugueis {
  static Scanner sc;

  static Equipamento localizarEquipamento(String descricao, Equipamento[] equipamentos) {
    Equipamento eqp = null;

    for (Equipamento equipamento : equipamentos) {
      if (equipamento == null) {
        break;
      }
      if (equipamento.getDescricao().toLowerCase().equals(descricao.toLowerCase())) {
        eqp = equipamento;
        break;
      }
    }

    return eqp;
  }

  static void lerDadosAlugueis(Scanner dados, Equipamento[] equipamentos) throws FileNotFoundException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // para usar o "parse" do LocalDate
    String linha = dados.nextLine();
    Equipamento equipamento = null;

    while (!linha.equals("FIM")) {
      String detalhes[] = linha.split(";");
      equipamento = localizarEquipamento(detalhes[0], equipamentos);
      equipamento.alugar(LocalDate.parse(detalhes[1], formatter), Integer.parseInt(detalhes[2]));
      linha = dados.nextLine();
    }
  }

  static Equipamento[] lerEquipamentos(String nomeArq) throws FileNotFoundException {
    Scanner dados = new Scanner(new File(nomeArq));
    String linha;
    int quantidade = Integer.parseInt(dados.nextLine());
    Equipamento[] equipamentos = new Equipamento[quantidade];

    for (int i = 0; i < quantidade; i++) {
      linha = dados.nextLine();
      String[] detalhes = linha.split(";");
      String descricao = detalhes[0];
      double valor = Double.parseDouble(detalhes[1]);
      int duracao = Integer.parseInt(detalhes[2]);
      double desconto = Double.parseDouble(detalhes[3]);
      Equipamento novoEquipamento = new Equipamento(descricao, valor, duracao, desconto);
      equipamentos[i] = novoEquipamento;
    }

    lerDadosAlugueis(dados, equipamentos);
    dados.close();
    return equipamentos;
  }

  static void limparTela() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  static void pausar() {
    System.out.print("Aperte qualquer tecla para continuar...");
    sc.nextLine();
  }

  static int exibirMenuPrincipal() {
    System.out.println("[1] Cadastrar novo equipamento");
    System.out.println("[2] Cadastrar novo aluguel");
    System.out.println("[3] Mostrar equipamento com maior arrecadação");
    System.out.println("[4] Pesquisar equipamento");
    System.out.println("\n[0] Sair");
    System.out.print("\n>_ ");

    return Integer.parseInt(sc.nextLine());
  }

  static String lerDescricaoEquipamento() {
    System.out.print("Descrição: ");
    return sc.nextLine();
  }

  static Equipamento cadastrarEquipamento() {
    String descricao;
    int duracaoMaxima;
    double valorDiaria, desconto;

    descricao = lerDescricaoEquipamento();

    System.out.print("Valor diária: R$ ");
    valorDiaria = Double.parseDouble(sc.nextLine());

    System.out.print("Duração máxima permitida: ");
    duracaoMaxima = Integer.parseInt(sc.nextLine());

    System.out.print("Desconto (%): ");
    desconto = Double.parseDouble(sc.nextLine());

    return new Equipamento(descricao, valorDiaria, duracaoMaxima, desconto);
  }

  static LocalDate lerDataInicio() {
    int dia, mes, ano;
    LocalDate dataInicio = null;
    String stringDataInicio;
    String[] auxDataInicio;

    System.out.print("Data de início (dd/mm/aaaa): ");
    stringDataInicio = sc.nextLine();
    auxDataInicio = stringDataInicio.split("/");

    if (auxDataInicio.length != 3) {
      System.out.print("Formato de data inválida. Utilize dd/mm/aaaa");
      lerDataInicio();
    }

    dia = Integer.parseInt(auxDataInicio[0]);
    mes = Integer.parseInt(auxDataInicio[1]);
    ano = Integer.parseInt(auxDataInicio[2]);

    if ((dia > 0 && dia <= 31) && (mes >= 1 && mes <= 12)) {
      dataInicio = LocalDate.of(ano, mes, dia);
    }

    return dataInicio;
  }

  static int lerQtdDias() {
    int qtdDias;

    System.out.print("Duração (em dias): ");
    qtdDias = Integer.parseInt(sc.nextLine());

    if (qtdDias < 0) {
      System.out.print("Duração deve ser maior que 0!\n");
      lerQtdDias();
    }

    return qtdDias;
  }

  static Aluguel alugarEquipamento(Equipamento equipamento) {
    LocalDate dataInicio = lerDataInicio();
    int qtdDias = lerQtdDias();

    return equipamento.alugar(dataInicio, qtdDias);
  }

  public static void main(String[] args) throws Exception {
    sc = new Scanner(System.in);
    int opt;
    Equipamento[] equipamentos = lerEquipamentos("dados.csv");
    Equipamento[] equipamentosTemp;
    equipamentosTemp = Arrays.copyOf(equipamentos, 100);
    int posEquipamentosTemp = equipamentos.length;
    Equipamento equipamento;
    Aluguel aluguel;
    String descricaoEquipamento;

    opt = exibirMenuPrincipal();
    do {
      switch (opt) {
        case 1:
          limparTela();
          equipamentosTemp[posEquipamentosTemp] = cadastrarEquipamento();
          System.out.print(equipamentosTemp[posEquipamentosTemp].dadosEquipamento());
          posEquipamentosTemp++;
          pausar();
          break;
        case 2:
          limparTela();
          descricaoEquipamento = lerDescricaoEquipamento();
          equipamento = localizarEquipamento(descricaoEquipamento, equipamentosTemp);
          if (equipamento != null) {
            aluguel = alugarEquipamento(equipamento);
            if (aluguel != null) {
              System.out.print(aluguel.relatorio());
            } else {
              System.out.println("\nEquipamento não disponível para a data informada.");
            }
          } else {
            System.out.println("\nEquipamento não existe.");
          }
          pausar();
          break;
        case 3:
          Equipamento equipamentoAux = equipamentosTemp[0];

          for (int i = 1; i < posEquipamentosTemp; i++) {
            if (equipamentosTemp[posEquipamentosTemp].totalArrecadado() > equipamentoAux.totalArrecadado()) {
              equipamentoAux = equipamentosTemp[i];
            }
          }

          System.out.println(equipamentoAux.dadosEquipamento());
          pausar();
          break;
        default:
          System.out.print("Opção inválida!");
          break;
      }
      opt = exibirMenuPrincipal();
    } while (opt != 0);
    sc.close();
  }
}

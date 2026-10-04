
import java.util.Scanner;

public class SistemaEscolaEAD {
    private Scanner entrada;
    private ListaDeAlunos lista;
    private Curso[][] matrizCursos;

    public SistemaEscolaEAD() {
        entrada = new Scanner(System.in);
        lista = new ListaDeAlunos(100);
        matrizCursos = new Curso[3][3];
    }

    public static void main(String[] args) {
        SistemaEscolaEAD sistema = new SistemaEscolaEAD();
        sistema.executar();
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(entrada.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                String valor = entrada.nextLine().replace(",", ".");
                return Double.parseDouble(valor);
            } catch (NumberFormatException e) {
                System.out.println("Digite um valor numerico valido.");
            }
        }
    }

    private String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    private void cadastrarCursos() {
        System.out.println("\n=== CADASTRO DE CURSOS ===");

        int quantidade;
        do {
            quantidade = lerInteiro(
                "Quantos cursos deseja cadastrar (0 a 9)? ");
            if (quantidade < 0 || quantidade > 9) {
                System.out.println("Escolha entre 0 e 9.");
            }
        } while (quantidade < 0 || quantidade > 9);

        for (int i = 0; i < quantidade; i++) {
            System.out.println("\nCurso " + (i + 1));

            try {
                int codigo = lerInteiro("Codigo: ");
                String nome = lerTexto("Nome: ");
                int duracao = lerInteiro("Duracao em horas: ");

                Curso curso = new Curso(codigo, nome, duracao);

                if (buscarCurso(codigo) != null) {
                    System.out.println("Codigo de curso duplicado.");
                    i--;
                    continue;
                }

                int posicao = i;
                matrizCursos[posicao / 3][posicao % 3] = curso;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
                i--;
            }
        }
    }

    private Curso buscarCurso(int codigo) {
        for (int linha = 0; linha < matrizCursos.length; linha++) {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length; coluna++) {

                Curso curso = matrizCursos[linha][coluna];

                if (curso != null && curso.getCodigo() == codigo) {
                    return curso;
                }
            }
        }
        return null;
    }

    private void exibirCursos() {
        System.out.println("\n=== CURSOS CADASTRADOS ===");
        boolean encontrou = false;

        for (int linha = 0; linha < matrizCursos.length; linha++) {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length; coluna++) {

                Curso curso = matrizCursos[linha][coluna];

                if (curso != null) {
                    curso.exibeDados();
                    encontrou = true;

                    System.out.println("Alunos matriculados:");

                    boolean temAluno = false;
                    Aluno[] alunos = lista.getAlunos();

                    for (int i = 0; i < lista.getTotalAlunos(); i++) {
                        if (alunos[i].getCursoMatriculado() != null
                            && alunos[i].getCursoMatriculado()
                                .getCodigo() == curso.getCodigo()) {

                            System.out.println("- "
                                + alunos[i].getNome()
                                + " (codigo "
                                + alunos[i].getCodigo() + ")");
                            temAluno = true;
                        }
                    }

                    if (!temAluno) {
                        System.out.println("- Nenhum aluno matriculado.");
                    }

                    System.out.println();
                }
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum curso cadastrado.");
        }
    }

    private void adicionarAluno() {
        if (lista.getTotalAlunos() >= lista.getCapacidade()) {
            System.out.println("Limite de alunos atingido.");
            return;
        }

        System.out.println("\n=== CADASTRO DE ALUNO ===");

        try {
            int codigo = lerInteiro("Codigo do aluno: ");

            if (lista.buscarAluno(codigo) != null) {
                System.out.println("Ja existe um aluno com esse codigo.");
                return;
            }

            String nome = lerTexto("Nome: ");
            String nascimento = lerTexto("Data de nascimento: ");
            String email = lerTexto("Email: ");
            String senha = lerTexto("Senha: ");

            int tipo = lerInteiro(
                "Tipo (1 - Aluno comum | 2 - Bolsista): ");

            Aluno aluno;

            if (tipo == 2) {
                String bolsa = lerTexto("Tipo de bolsa: ");
                aluno = new AlunoBolsista(
                    codigo, nome, nascimento, email, senha, bolsa);
            } else if (tipo == 1) {
                aluno = new Aluno(
                    codigo, nome, nascimento, email, senha);
            } else {
                System.out.println("Tipo de aluno invalido.");
                return;
            }

            exibirCursos();

            if (buscarCurso(1) != null || haCursosCadastrados()) {
                int codigoCurso = lerInteiro(
                    "Codigo do curso para matricula (0 = nenhum): ");

                if (codigoCurso != 0) {
                    Curso curso = buscarCurso(codigoCurso);

                    if (curso == null) {
                        System.out.println("Curso nao encontrado.");
                        return;
                    }

                    aluno.setCursoMatriculado(curso);
                }
            }

            int quantidade = lerInteiro(
                "Quantidade de mensalidades (0 a 12): ");

            if (quantidade < 0 || quantidade > 12) {
                System.out.println("Quantidade invalida.");
                return;
            }

            if (quantidade > 0) {
                double valor = lerDouble("Valor de cada mensalidade: ");

                if (valor < 0) {
                    System.out.println("Valor invalido.");
                    return;
                }

                double[] valores = new double[quantidade];

                for (int i = 0; i < quantidade; i++) {
                    valores[i] = valor;
                }

                aluno.adicionarMensalidades(valores);
            }

            if (lista.adicionarAluno(aluno)) {
                System.out.println("Aluno cadastrado com sucesso!");
            } else {
                System.out.println("Nao foi possivel cadastrar o aluno.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private boolean haCursosCadastrados() {
        for (int linha = 0; linha < matrizCursos.length; linha++) {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length; coluna++) {
                if (matrizCursos[linha][coluna] != null) {
                    return true;
                }
            }
        }
        return false;
    }

    private Aluno localizarAluno() {
        int codigo = lerInteiro("Codigo do aluno: ");
        Aluno aluno = lista.buscarAluno(codigo);

        if (aluno == null) {
            System.out.println("Aluno nao encontrado.");
        }

        return aluno;
    }

    private void verificarNotas() {
        Aluno aluno = localizarAluno();

        if (aluno == null) {
            return;
        }

        System.out.println(
            "Digite as tres notas do aluno. Para manter uma nota ja lancada, "
            + "informe o valor novamente.");

        try {
            for (int i = 0; i < 3; i++) {
                double nota = lerDouble("Nota " + (i + 1) + " (0 a 10): ");
                aluno.lancarNotas(i, nota);
            }

            aluno.exibirNotas();

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verificarFinanceiro() {
        Aluno aluno = localizarAluno();

        if (aluno == null) {
            return;
        }

        aluno.exibirMensalidades();

        int pagar = lerInteiro(
            "Digite o numero da parcela para pagar (0 = voltar): ");

        if (pagar == 0) {
            return;
        }

        try {
            aluno.pagarMensalidade(pagar - 1);
            System.out.println("Pagamento registrado com sucesso!");
            aluno.exibirMensalidades();

        } catch (IllegalArgumentException
                 | IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void executar() {
        cadastrarCursos();

        int opcao;

        do {
            System.out.println("\n===== ESCOLA EAD =====");
            System.out.println("1 - Visualizar alunos e cursos");
            System.out.println("2 - Adicionar aluno");
            System.out.println("3 - Sair");
            System.out.println("4 - Verificar notas do aluno");
            System.out.println("5 - Verificar financeiro do aluno");

            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    lista.exibirLista();
                    exibirCursos();
                    break;

                case 2:
                    adicionarAluno();
                    break;

                case 3:
                    System.out.println("Encerrando o sistema...");
                    break;

                case 4:
                    verificarNotas();
                    break;

                case 5:
                    verificarFinanceiro();
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 3);

        entrada.close();
    }
}
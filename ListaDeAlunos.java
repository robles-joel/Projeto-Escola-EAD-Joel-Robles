
public class ListaDeAlunos {
    private Aluno[] alunos;
    private int totalAlunos;

    public ListaDeAlunos(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException(
                "A capacidade deve ser positiva.");
        }

        alunos = new Aluno[capacidade];
        totalAlunos = 0;
    }

    public boolean adicionarAluno(Aluno a) {
        if (a == null || totalAlunos >= alunos.length) {
            return false;
        }

        if (buscarAluno(a.getCodigo()) != null) {
            return false;
        }

        alunos[totalAlunos] = a;
        totalAlunos++;
        return true;
    }

    public Aluno buscarAluno(int codigo) {
        for (int i = 0; i < totalAlunos; i++) {
            if (alunos[i].getCodigo() == codigo) {
                return alunos[i];
            }
        }
        return null;
    }

    public void exibirLista() {
        if (totalAlunos == 0) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        for (int i = 0; i < totalAlunos; i++) {
            System.out.println("\n--------------------");
            alunos[i].exibeDados();
        }
    }

    public int getTotalAlunos() {
        return totalAlunos;
    }

    public int getCapacidade() {
        return alunos.length;
    }

    public Aluno[] getAlunos() {
        return alunos;
    }
}
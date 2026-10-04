
public class Curso {
    private int codigo;
    private String nome;
    private int duracao;

    public Curso(int codigo, String nome, int duracao) {
        setCodigo(codigo);
        setNome(nome);
        setDuracao(duracao);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException(
                "Codigo invalido.");
        }
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Nome do curso obrigatorio.");
        }
        this.nome = nome;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        if (duracao <= 0) {
            throw new IllegalArgumentException(
                "A duracao deve ser positiva.");
        }
        this.duracao = duracao;
    }

    public void exibeDados() {
        System.out.println("Codigo: " + codigo
            + " | Curso: " + nome
            + " | Duracao: " + duracao + "h");
    }
}
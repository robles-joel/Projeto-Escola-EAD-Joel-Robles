public class Aluno {
    private int codigo;
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;

    private Curso cursoMatriculado;

    private double[] notas = new double[3];
    private boolean[] lancada = new boolean[3];

    private Mensalidade[] mensalidades;
    private int numParcelas;

    public Aluno(int codigo, String nome, String dataNascimento,
                 String email, String senha) {
        setCodigo(codigo);
        setNome(nome);
        setDataNascimento(dataNascimento);
        setEmail(email);
        setSenha(senha);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo > 0) {
            this.codigo = codigo;
        } else {
            throw new IllegalArgumentException(
                "O codigo deve ser positivo.");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        } else {
            throw new IllegalArgumentException(
                "O nome nao pode ser vazio.");
        }
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException(
                "Email invalido.");
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha != null && !senha.isEmpty()) {
            this.senha = senha;
        } else {
            throw new IllegalArgumentException(
                "A senha nao pode ser vazia.");
        }
    }

    public Curso getCursoMatriculado() {
        return cursoMatriculado;
    }

    public void setCursoMatriculado(Curso curso) {
        this.cursoMatriculado = curso;
    }

    public void exibeDados() {
        System.out.println("Codigo: " + codigo);
        System.out.println("Nome: " + nome);
        System.out.println("Nascimento: " + dataNascimento);
        System.out.println("Email: " + email);

        if (cursoMatriculado != null) {
            System.out.println("Curso: "
                + cursoMatriculado.getNome());
        } else {
            System.out.println("Curso: Nao matriculado");
        }
    }

    public void lancarNotas(int indice, double nota) {
        if (indice < 0 || indice >= notas.length) {
            throw new IllegalArgumentException(
                "Indice de nota invalido.");
        }

        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException(
                "A nota deve estar entre 0 e 10.");
        }

        notas[indice] = nota;
        lancada[indice] = true;
    }

    public double calcularMedia() {
        double soma = 0;

        for (int i = 0; i < notas.length; i++) {
            if (!lancada[i]) {
                return -1;
            }
            soma += notas[i];
        }

        return soma / notas.length;
    }

    public void exibirNotas() {
        System.out.println("\nNotas do aluno: " + nome);

        for (int i = 0; i < notas.length; i++) {
            if (lancada[i]) {
                System.out.println("Nota " + (i + 1)
                    + ": " + notas[i]);
            } else {
                System.out.println("Nota " + (i + 1)
                    + ": Ainda nao lancada");
            }
        }

        double media = calcularMedia();

        if (media >= 0) {
            System.out.printf("Media: %.2f%n", media);
        } else {
            System.out.println(
                "Media indisponivel: faltam notas.");
        }
    }

    public void adicionarMensalidades(double[] valores) {
        if (valores == null || valores.length == 0) {
            throw new IllegalArgumentException(
                "Informe pelo menos uma mensalidade.");
        }

        mensalidades = new Mensalidade[valores.length];
        numParcelas = valores.length;

        for (int i = 0; i < valores.length; i++) {
            if (valores[i] < 0) {
                throw new IllegalArgumentException(
                    "O valor nao pode ser negativo.");
            }

            mensalidades[i] = new Mensalidade(valores[i], false);
        }
    }

    public void exibirMensalidades() {
        if (mensalidades == null) {
            System.out.println(
                "Nenhuma mensalidade cadastrada.");
            return;
        }

        System.out.println("\nFinanceiro de " + nome);

        for (int i = 0; i < numParcelas; i++) {
            System.out.printf("Parcela %d: R$ %.2f - %s%n",
                i + 1,
                mensalidades[i].getValor(),
                mensalidades[i].isPago() ? "PAGO" : "PENDENTE");
        }
    }

    public void pagarMensalidade(int indice) {
        if (mensalidades == null) {
            throw new IllegalStateException(
                "Mensalidades nao cadastradas.");
        }

        if (indice < 0 || indice >= numParcelas) {
            throw new IllegalArgumentException(
                "Numero de parcela invalido.");
        }

        mensalidades[indice].darBaixa();
    }
}
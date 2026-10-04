
public class AlunoBolsista extends Aluno {
    private String tipoBolsa;

    public AlunoBolsista(int codigo, String nome,
                         String dataNascimento, String email,
                         String senha, String tipoBolsa) {
        super(codigo, nome, dataNascimento, email, senha);
        setTipoBolsa(tipoBolsa);
    }

    public String getTipoBolsa() {
        return tipoBolsa;
    }

    public void setTipoBolsa(String tipoBolsa) {
        if (tipoBolsa != null && !tipoBolsa.trim().isEmpty()) {
            this.tipoBolsa = tipoBolsa;
        } else {
            throw new IllegalArgumentException(
                "Informe o tipo de bolsa.");
        }
    }

    @Override
    public void exibeDados() {
        super.exibeDados();
        System.out.println("Tipo de bolsa: " + tipoBolsa);
    }
}
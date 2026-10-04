
public class Mensalidade {
    private double valor;
    private boolean pago;

    public Mensalidade(double valor, boolean pago) {
        setValor(valor);
        this.pago = pago;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException(
                "Valor invalido.");
        }
        this.valor = valor;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public void darBaixa() {
        pago = true;
    }
}
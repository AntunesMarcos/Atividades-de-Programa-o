import java.text.NumberFormat;
abstract class Produto {
    private static final double MARGEM_PADRAO = 0.2;
    protected String descricao;
    protected double precoCusto;
    protected double margemLucro;

    public Produto(String descricao, double precoCusto, double margemLucro) {
        if (descricao.length() < 3 || precoCusto <= 0 || margemLucro < 0)
            throw new IllegalArgumentException("Valores inválidos para o produto");
        this.descricao = descricao;
        this.precoCusto = precoCusto;
        this.margemLucro = margemLucro;
    }

    public Produto(String descricao, double precoCusto) {
        this(descricao, precoCusto, MARGEM_PADRAO);
    }

    public double valorDeVenda() {
        return precoCusto * (1 + margemLucro);
    }

    @Override
    public String toString() {
        NumberFormat moeda = NumberFormat.getCurrencyInstance();
        return String.format("Produto: %s - Preço de venda: %s", descricao, moeda.format(valorDeVenda()));
    }

    public abstract String toFileString();
}

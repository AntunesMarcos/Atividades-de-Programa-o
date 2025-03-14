import java.time.LocalDate;

public class ProdutoPerecivel extends Produto {
    private LocalDate validade;

    public ProdutoPerecivel(String descricao, double precoCusto, double margemLucro, LocalDate validade) {
        super(descricao, precoCusto, margemLucro);
        if (validade.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("A data de validade não pode ser anterior à data atual.");
        this.validade = validade;
    }

    public ProdutoPerecivel(String descricao, double precoCusto, LocalDate validade) {
        super(descricao, precoCusto);
        if (validade.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("A data de validade não pode ser anterior à data atual.");
        this.validade = validade;
    }

    @Override
    public double valorDeVenda() {
        if (validade.isBefore(LocalDate.now().plusDays(2))) {
            return super.valorDeVenda() * 0.75;
        }
        return super.valorDeVenda();
    }

    @Override
    public String toString() {
        return super.toString() + " | Validade: " + validade;
    }

    @Override
    public String toFileString() {
        return descricao + ";" + precoCusto + ";" + margemLucro + ";" + validade;
    }
}

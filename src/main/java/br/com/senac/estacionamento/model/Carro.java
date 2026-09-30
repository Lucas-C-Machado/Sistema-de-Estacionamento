package br.com.senac.estacionamento.model;

public class Carro extends Veiculo {
    private int quantidadePortas;

    public Carro(String placa, String modelo, double valorHora, int quantidadePortas) {
        super(placa, modelo, valorHora);
        if (quantidadePortas <= 0) {
            throw new IllegalArgumentException("A quantidade de portas deve ser válida.");
        }
        this.quantidadePortas = quantidadePortas;
    }

    public int getQuantidadePortas() { return quantidadePortas; }

    @Override
    public String getTipo() { return "CARRO"; }
}

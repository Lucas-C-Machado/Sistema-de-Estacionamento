package br.com.senac.estacionamento.model;

public class Carro extends Veiculo {

    private int quantidadePortas;

    // Construtor utilizado para criar um carro novo
    public Carro(String placa, String modelo, double valorHora, int quantidadePortas) {
        super(placa, modelo, valorHora);

        if (quantidadePortas <= 0) {
            throw new IllegalArgumentException("A quantidade de portas deve ser válida.");
        }
        this.quantidadePortas = quantidadePortas;
    }

    // Construtor utilizado para reconstruir um carro vindo do banco
    public Carro(String placa, String modelo, double valorHora, int quantidadePortas, int horasEstacionado, boolean estacionado) {
        super(placa, modelo, valorHora, horasEstacionado, estacionado);

        if (quantidadePortas <= 0) {
            throw new IllegalArgumentException("A quantidade de portas deve ser válida.");
        }
        this.quantidadePortas = quantidadePortas;
    }


    public int getQuantidadePortas() {
        return quantidadePortas;
    }


    @Override
    public String getTipo() {
        return "CARRO";
    }
}
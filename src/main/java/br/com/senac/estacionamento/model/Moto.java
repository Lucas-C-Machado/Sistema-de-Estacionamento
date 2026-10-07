package br.com.senac.estacionamento.model;

public class Moto extends Veiculo {

    private int cilindradas;

    // Construtor utilizado para criar uma moto nova
    public Moto(String placa, String modelo, double valorHora,int cilindradas) {
        super(placa, modelo, valorHora);

        if (cilindradas <= 0) {
            throw new IllegalArgumentException("A cilindrada deve ser válida.");
        }
        this.cilindradas = cilindradas;
    }

    // Construtor utilizado para reconstruir uma moto vinda do banco
    public Moto(String placa, String modelo, double valorHora, int cilindradas, int horasEstacionado, boolean estacionado) {
        super(placa, modelo, valorHora, horasEstacionado, estacionado);

        if (cilindradas <= 0) {
            throw new IllegalArgumentException("A cilindrada deve ser válida.");
        }
        this.cilindradas = cilindradas;
    }


    public int getCilindradas() {
        return cilindradas;
    }


    @Override
    public String getTipo() {
        return "MOTO";
    }
}
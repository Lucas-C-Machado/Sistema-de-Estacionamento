package br.com.senac.estacionamento.model;

public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(String placa, String modelo, double valorHora, int cilindradas) {
        super(placa, modelo, valorHora);
        if (cilindradas <= 0) {
            throw new IllegalArgumentException("A cilindrada deve ser válida.");
        }
        this.cilindradas = cilindradas;
    }

    public int getCilindradas() { return cilindradas; }

    @Override
    public String getTipo() { return "MOTO"; }
}

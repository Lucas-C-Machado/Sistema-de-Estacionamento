package br.com.senac.estacionamento.model;

import br.com.senac.estacionamento.contract.Calculavel;

public abstract class Veiculo implements Calculavel {
    private int id;
    private String placa;
    private String modelo;
    private double valorHora;
    private int horasEstacionado;
    private boolean estacionado;

    public Veiculo(String placa, String modelo, double valorHora) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("A placa não pode estar vazia.");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("O modelo não pode estar vazio.");
        }
        if (valorHora <= 0) {
            throw new IllegalArgumentException("O valor por hora deve ser maior que zero.");
        }
        this.placa = placa;
        this.modelo = modelo;
        this.valorHora = valorHora;
        this.horasEstacionado = 0;
        this.estacionado = false;
    }

    // Segundo Construtor
    public Veiculo(int id, String placa, String modelo, double valorHora, int horasEstacionado, boolean estacionado) {

        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("A placa não pode estar vazia.");
        }

        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("O modelo não pode estar vazio.");
        }

        if (valorHora <= 0) {
            throw new IllegalArgumentException("O valor por hora deve ser maior que zero.");
        }

        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.valorHora = valorHora;
        this.horasEstacionado = horasEstacionado;
        this.estacionado = estacionado;
    }

    public boolean entrar() {
        if (estacionado)
            return false;
        estacionado = true;
        return true;
    }

    public boolean adicionarHoras(int horas) {
        if (!estacionado || horas <= 0)
            return false;
        horasEstacionado += horas;
        return true;
    }

    public boolean sair() {
        if (!estacionado)
            return false;
        estacionado = false;
        return true;
    }

    @Override
    public double calcularValor() {
        double valor = horasEstacionado * valorHora;
        if (horasEstacionado >= 5) {
            valor *= 0.90;
        }
        return valor;
    }

    public int getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public double getValorHora() {
        return valorHora;
    }

    public int getHorasEstacionado() {
        return horasEstacionado;
    }

    public boolean isEstacionado() {
        return estacionado;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "Tipo: " + getTipo()
                + " | Placa: " + placa
                + " | Modelo: " + modelo
                + " | Horas: " + horasEstacionado
                + " | Estacionado: " + estacionado
                + " | Valor: R$ " + String.format("%.2f", calcularValor());
    }
}

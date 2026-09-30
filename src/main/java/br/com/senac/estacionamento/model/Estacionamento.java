package br.com.senac.estacionamento.model;

import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private String nome;
    private List<Veiculo> veiculos;

    public Estacionamento(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O estacionamento precisa possuir um nome.");
        }
        this.nome = nome;
        this.veiculos = new ArrayList<>();
    }

    public boolean adicionarVeiculo(Veiculo veiculo) {
        if (veiculo == null || buscarPorPlaca(veiculo.getPlaca()) != null) return false;
        veiculos.add(veiculo);
        return true;
    }

    public Veiculo buscarPorPlaca(String placa) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) return veiculo;
        }
        return null;
    }

    public void listarVeiculos() {
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
        }
    }

    public String getNome() { return nome; }
    public int getQuantidadeVeiculos() { return veiculos.size(); }
}

package br.com.senac.estacionamento.controller;

import br.com.senac.estacionamento.dao.VeiculoDAO;
import br.com.senac.estacionamento.model.Carro;
import br.com.senac.estacionamento.model.Moto;
import br.com.senac.estacionamento.model.Veiculo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VeiculoController {

    private VeiculoDAO dao = new VeiculoDAO();

    // =========================================
    // READ - LISTAR
    // =========================================

    @GetMapping("/veiculos")
    public String listar(Model model) {
        List<Veiculo> veiculos = dao.listar();
        model.addAttribute("veiculos",veiculos);
        return "veiculos";
    }

    // =========================================
    // ABRIR FORMULÁRIO
    // =========================================

    @GetMapping("/veiculos/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    // =========================================
    // CREATE - CADASTRAR
    // =========================================

    @PostMapping("/veiculos/cadastrar")
    public String cadastrar(
            @RequestParam String placa,
            @RequestParam String modelo,
            @RequestParam String tipo,
            @RequestParam double valorHora,
            @RequestParam(required = false) Integer quantidadePortas,
            @RequestParam(required = false) Integer cilindradas) {

        Veiculo veiculo;

        if (tipo.equalsIgnoreCase("Carro")) {
            if (quantidadePortas == null || quantidadePortas <= 0) {
                throw new IllegalArgumentException("Informe a quantidade de portas do carro.");
            }

            veiculo = new Carro(placa, modelo, valorHora, quantidadePortas);

        } else if (tipo.equalsIgnoreCase("Moto")) {
            if (cilindradas == null || cilindradas <= 0) {
                throw new IllegalArgumentException("Informe as cilindradas da moto.");
            }

            veiculo = new Moto(placa, modelo, valorHora,cilindradas);

        } else {
            throw new IllegalArgumentException("Tipo de veículo inválido.");
        }

        dao.inserir(veiculo);
        return "redirect:/veiculos";
    }
}
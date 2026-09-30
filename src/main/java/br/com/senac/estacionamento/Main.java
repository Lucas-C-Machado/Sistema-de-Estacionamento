package br.com.senac.estacionamento;

import br.com.senac.estacionamento.dao.VeiculoDAO;
import br.com.senac.estacionamento.model.Carro;
import br.com.senac.estacionamento.model.Estacionamento;
import br.com.senac.estacionamento.model.Moto;
import br.com.senac.estacionamento.model.Veiculo;

public class Main {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" SISTEMA DE ESTACIONAMENTO");
        System.out.println("========================================");

        // ==================================================
        // 1. CRIANDO E TESTANDO NOSSOS OBJETOS
        // ==================================================
        System.out.println("\n--- TESTANDO NOSSO MODELO OO ---");

        Estacionamento estacionamento = new Estacionamento("Estacionamento Senac");

        Veiculo carro = new Carro("CBC1D23", "Civic", 10.0,4);
        Veiculo moto = new Moto("XYZ9A87", "CB 500", 7.0, 500);

        // ==================================================
        // 2. TRABALHANDO COM OS OBJETOS
        // ==================================================
        estacionamento.adicionarVeiculo(carro);
        estacionamento.adicionarVeiculo(moto);

        carro.entrar();
        carro.adicionarHoras(3);

        moto.entrar();
        moto.adicionarHoras(6);

        estacionamento.listarVeiculos();

        // ==================================================
        // 3. CRIANDO O DAO
        // ==================================================
        System.out.println("\n--- ACESSANDO O BANCO DE DADOS ---");

        VeiculoDAO veiculoDAO = new VeiculoDAO();

        // ==================================================
        // 4. CREATE - INSERT
        // JAVA -> DAO -> MYSQL
        // ==================================================
        System.out.println("\n--- INSERINDO VEÍCULOS NO BANCO ---");

        veiculoDAO.inserir(carro);
        veiculoDAO.inserir(moto);

        // ==================================================
        // 5. READ - SELECT
        // MYSQL -> DAO -> JAVA
        // ==================================================

        System.out.println("\n--- CONSULTANDO VEÍCULOS ---");

        veiculoDAO.listar();

        // ==================================================
        // 6. UPDATE
        // JAVA -> DAO -> MYSQL
        // ==================================================
        System.out.println("\n--- ATUALIZANDO VEÍCULO ---");

        boolean atualizou = veiculoDAO.atualizarValorHora("CBC1D23", 12.0);

        if (atualizou) {
            System.out.println("Veículo atualizado com sucesso!");
        } else {
            System.out.println("Veículo não encontrado.");
        }

        // ==================================================
        // 7. READ NOVAMENTE
        // VERIFICANDO SE O UPDATE FUNCIONOU
        // ==================================================
        System.out.println("\n--- CONSULTANDO APÓS UPDATE ---");

        veiculoDAO.listar();

        // ==================================================
        // 8. FINALIZAÇÃO
        // ==================================================
        System.out.println("\n========================================");
        System.out.println(" SISTEMA FINALIZADO");
        System.out.println("========================================");
    }
}
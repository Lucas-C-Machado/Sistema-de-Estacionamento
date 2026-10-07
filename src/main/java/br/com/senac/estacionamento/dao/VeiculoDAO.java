package br.com.senac.estacionamento.dao;

import br.com.senac.estacionamento.connection.Conexao;
import br.com.senac.estacionamento.model.Carro;
import br.com.senac.estacionamento.model.Moto;
import br.com.senac.estacionamento.model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    // ==================================================
    // CREATE - INSERT
    // JAVA -> MYSQL
    // ==================================================
    public boolean inserir(Veiculo veiculo) {

        String sql = """
                INSERT INTO veiculo
                (
                    placa,
                    modelo,
                    valor_hora,
                    horas_estacionado,
                    estacionado,
                    tipo,
                    quantidade_portas,
                    cilindradas
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql)) {

            // ==========================================
            // DADOS COMUNS A TODO VEÍCULO
            // ==========================================

            comando.setString(1, veiculo.getPlaca());
            comando.setString(2, veiculo.getModelo());
            comando.setDouble(3, veiculo.getValorHora());
            comando.setInt(4, veiculo.getHorasEstacionado());
            comando.setBoolean(5, veiculo.isEstacionado());
            comando.setString(6, veiculo.getTipo());

            // ==========================================
            // DADOS ESPECÍFICOS DE CARRO OU MOTO
            // ==========================================

            if (veiculo instanceof Carro carro) {
                // Carro possui quantidade de portas
                comando.setInt(7, carro.getQuantidadePortas());

                // Carro não possui cilindradas
                comando.setNull(8, Types.INTEGER);

            } else if (veiculo instanceof Moto moto) {
                // Moto não possui quantidade de portas
                comando.setNull(7, Types.INTEGER);

                // Moto possui cilindradas
                comando.setInt(8, moto.getCilindradas());

            } else {
                System.out.println("Tipo de veículo não reconhecido.");
                return false;
            }

            int linhas = comando.executeUpdate();
            System.out.println("Veículo inserido com sucesso! Linhas: " + linhas);
            return linhas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao inserir veículo: " + erro.getMessage());
            return false;
        }
    }

    // ==================================================
    // READ - SELECT
    // MYSQL -> JAVA
    // ==================================================
    public List<Veiculo> listar() {

        List<Veiculo> veiculos = new ArrayList<>();

        String sql = """
                SELECT *
                FROM veiculo
                ORDER BY id
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {

                // ======================================
                // DADOS COMUNS
                // ======================================

                int id = resultado.getInt("id");
                String placa = resultado.getString("placa");
                String modelo = resultado.getString("modelo");
                double valorHora = resultado.getDouble("valor_hora");
                int horasEstacionado = resultado.getInt("horas_estacionado");
                boolean estacionado = resultado.getBoolean("estacionado");
                String tipo = resultado.getString("tipo");

                Veiculo veiculo;

                // ======================================
                // RECONSTRUIR CARRO
                // ======================================

                if (tipo.equalsIgnoreCase("CARRO")) {

                    int quantidadePortas = resultado.getInt("quantidade_portas");

                    /*
                     * Se quantidade_portas for NULL
                     * no MySQL, getInt() retorna 0.
                     *
                     * Como Carro não aceita 0 portas,
                     * verificamos explicitamente.
                     */
                    if (resultado.wasNull()) {
                        System.out.println("Carro ignorado porque " + "quantidade_portas está NULL. " + "Placa: " + placa);
                        continue;
                    }

                    veiculo = new Carro(id, placa, modelo, valorHora, quantidadePortas, horasEstacionado, estacionado);

                    // ======================================
                    // RECONSTRUIR MOTO
                    // ======================================

                } else if (tipo.equalsIgnoreCase("MOTO")) {
                    int cilindradas = resultado.getInt("cilindradas");

                    if (resultado.wasNull()) {
                        System.out.println("Moto ignorada porque " + "cilindradas está NULL. " + "Placa: " + placa);
                        continue;
                    }

                    veiculo = new Moto(id, placa, modelo, valorHora, cilindradas, horasEstacionado, estacionado);

                } else {
                    System.out.println("Tipo de veículo desconhecido: " + tipo);
                    continue;
                }

                veiculos.add(veiculo);
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao consultar veículos: " + erro.getMessage());
        }

        return veiculos;
    }

    public Veiculo buscarPorId(int id) {

        String sql = """
                SELECT *
                FROM veiculo
                WHERE id = ?
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, id);

            try (
                    ResultSet resultado = comando.executeQuery()) {

                if (resultado.next()) {

                    String placa = resultado.getString("placa");
                    String modelo = resultado.getString("modelo");
                    double valorHora = resultado.getDouble("valor_hora");
                    int horasEstacionado = resultado.getInt("horas_estacionado");
                    boolean estacionado = resultado.getBoolean("estacionado");
                    String tipo = resultado.getString("tipo");

                    if (tipo.equalsIgnoreCase("CARRO")) {
                        int quantidadePortas = resultado.getInt("quantidade_portas");

                        if (resultado.wasNull()) {
                            return null;
                        }

                        return new Carro(id, placa, modelo, valorHora, quantidadePortas, horasEstacionado, estacionado);
                    }

                    if (tipo.equalsIgnoreCase("MOTO")) {

                        int cilindradas = resultado.getInt("cilindradas");

                        if (resultado.wasNull()) {
                            return null;
                        }

                        return new Moto(id, placa, modelo, valorHora, cilindradas, horasEstacionado, estacionado);
                    }
                }
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao buscar veículo: " + erro.getMessage());
        }
        return null;
    }

    // ==================================================
    // UPDATE
    // ATUALIZAR VALOR POR HORA
    // ==================================================
    public boolean atualizar(Veiculo veiculo) {

        String sql = """
                UPDATE veiculo
                SET placa = ?,
                    modelo = ?,
                    valor_hora = ?,
                    tipo = ?,
                    quantidade_portas = ?,
                    cilindradas = ?
                WHERE id = ?
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando =
                conexao.prepareStatement(sql)
        ) {

            comando.setString(1, veiculo.getPlaca());
            comando.setString(2, veiculo.getModelo());
            comando.setDouble(3, veiculo.getValorHora());
            comando.setString(4, veiculo.getTipo());

            if (veiculo instanceof Carro carro) {
                comando.setInt(5, carro.getQuantidadePortas());
                comando.setNull(6, Types.INTEGER);

            } else if (veiculo instanceof Moto moto) {
                comando.setNull(5, Types.INTEGER);
                comando.setInt(6, moto.getCilindradas());

            } else {
                return false;
            }

            comando.setInt(7, veiculo.getId());
            int linhas = comando.executeUpdate();

            return linhas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao atualizar veículo: " + erro.getMessage());
            return false;
        }
    }

    // ==================================================
    // DELETE
    // ==================================================
    public boolean excluir(int id) {

        String sql = """
            DELETE FROM veiculo
            WHERE id = ?
            """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.setInt(1, id);
            int linhasAfetadas = comando.executeUpdate();
            System.out.println("Veículo excluído. Linhas afetadas: " + linhasAfetadas);

            return linhasAfetadas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao excluir veículo: " + erro.getMessage());
            
            return false;
        }
    }
}
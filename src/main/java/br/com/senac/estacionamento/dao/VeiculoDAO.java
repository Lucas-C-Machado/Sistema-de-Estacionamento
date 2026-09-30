package br.com.senac.estacionamento.dao;

import br.com.senac.estacionamento.connection.Conexao;
import br.com.senac.estacionamento.model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeiculoDAO {

    // ==================================================
    // INSERT
    // JAVA -> MYSQL
    // ==================================================
    public void inserir(Veiculo veiculo) {

        String sql = """
                INSERT INTO veiculo
                (placa, modelo, valor_hora,
                 horas_estacionado, estacionado, tipo)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            // OBJETO JAVA -> SQL
            comando.setString(1, veiculo.getPlaca());
            comando.setString(2, veiculo.getModelo());
            comando.setDouble(3, veiculo.getValorHora());
            comando.setInt(4, veiculo.getHorasEstacionado());
            comando.setBoolean(5, veiculo.isEstacionado());
            comando.setString(6, veiculo.getTipo());

            int linhas = comando.executeUpdate();

            System.out.println(
                    "Veículo inserido com sucesso! Linhas: " + linhas
            );

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao inserir veículo: " + erro.getMessage()
            );
        }
    }

    // ==================================================
    // SELECT
    // MYSQL -> JAVA
    // ==================================================
    public void listar() {

        String sql = "SELECT * FROM veiculo";

        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()
        ) {

            // Percorre os registros retornados pelo MySQL
            while (resultado.next()) {

                int id = resultado.getInt("id");
                String placa = resultado.getString("placa");
                String modelo = resultado.getString("modelo");
                double valorHora = resultado.getDouble("valor_hora");
                int horasEstacionado = resultado.getInt("horas_estacionado");
                boolean estacionado = resultado.getBoolean("estacionado");
                String tipo = resultado.getString("tipo");

                System.out.println(
                        "ID: " + id
                                + " | Placa: " + placa
                                + " | Modelo: " + modelo
                                + " | Valor/hora: R$ " + valorHora
                                + " | Horas: " + horasEstacionado
                                + " | Estacionado: " + estacionado
                                + " | Tipo: " + tipo
                );
            }

        } catch (SQLException erro) {
            System.out.println("Erro ao consultar veículos: " + erro.getMessage());
        }
    }

    public boolean atualizarValorHora(
        String placa,
        double novoValor){

        String sql = """
                        UPDATE veiculo
                        SET valor_hora = ?
                        WHERE placa ?
                        """;
        
        try (
                Connection conexao = Conexao.conectar();
                PreparedStatement comando = conexao.prepareStatement(sql)
        ){
                comando.setDouble(1, novoValor);
                comando.setString(2, placa);

                int linhas = comando.executeUpdate();

                return linhas > 0;

        } catch(SQLException erro){
                System.out.println("Erro ao atualizar: " + erro.getMessage());
                return false;
        }

    }

    public boolean excluir(String placa) {

        String sql = """
            DELETE FROM veiculo
            WHERE placa = ?
            """;

        try (
            Connection conexao = Conexao.conectar();

            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {

            comando.setString(1, placa);
            int linhas = comando.executeUpdate();
            return linhas > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao excluir: " + erro.getMessage());
            return false;
        }
    }

}
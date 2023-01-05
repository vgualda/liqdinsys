
package test;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.SQLException;


public class TesteConexao {
    public static void main(String[] args) {
        Connection conexao = null;
        
        try {
            conexao = ConexaoFactory.conectar();
            System.out.println("Conexao efetuada com sucesso!");
            conexao.close();
        } catch (SQLException e) {
            System.out.println("Falha na comunicação com o banco de dados: "
             + e.getMessage()); 
        }
    }
    
}

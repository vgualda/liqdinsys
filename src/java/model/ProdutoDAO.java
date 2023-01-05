package model;
/**
 * @author mushr
 */

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";

    public boolean gravar(Produto pd) throws SQLException {
        con = ConexaoFactory.conectar();

        if (pd.getIdProduto() == 0) {
            sql = "INSERT INTO produto(nome, estoque, preco, nomeArquivo, caminho, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, pd.getNome());
            ps.setInt(2, pd.getEstoque());
            ps.setDouble(3, pd.getPreco());
            ps.setString(4, pd.getNomeArquivo());
            ps.setString(5, pd.getCaminho());
            ps.setInt(6, pd.getStatus());
            
        } else {
            sql = "UPDATE produto SET nome = ?, estoque = ?, preco = ?, nomeArquivo = ?, caminho = ?, status = ? "
                    + "WHERE idProduto = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, pd.getNome());
            ps.setInt(2, pd.getEstoque());
            ps.setDouble(3, pd.getPreco());
            ps.setString(4, pd.getNomeArquivo());
            ps.setString(5, pd.getCaminho());
            ps.setInt(6, pd.getStatus());
            ps.setInt(7, pd.getIdProduto());
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public ArrayList<Produto> listar() throws SQLException {
        ArrayList<Produto> produtos = new ArrayList<>();
        sql = "SELECT idProduto, nome, estoque, preco, nomeArquivo, caminho, status "
                + "FROM produto";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()) {
            Produto pd = new Produto();
            pd.setIdProduto(rs.getInt("idProduto"));
            pd.setNome(rs.getString("nome"));
            pd.setEstoque(rs.getInt("estoque"));
            pd.setPreco(rs.getDouble("preco"));
            pd.setNomeArquivo(rs.getString("nomeArquivo"));
            pd.setCaminho(rs.getString("caminho"));
            pd.setStatus(rs.getInt("status"));
            produtos.add(pd);
        }
        ConexaoFactory.close(con);
        return produtos;
    }
    
    public Produto getProdutoPorId(int idProduto) throws SQLException {
        Produto pd = new Produto();
        sql = "SELECT idProduto, nome, estoque, preco, nomeArquivo, caminho, status "
                + "FROM produto "
                + "WHERE idProduto = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idProduto);
        rs = ps.executeQuery();
                
        if(rs.next()) {
            pd.setIdProduto(rs.getInt("idProduto"));
            pd.setNome(rs.getString("nome"));
            pd.setEstoque(rs.getInt("estoque"));
            pd.setPreco(rs.getDouble("preco"));
            pd.setNomeArquivo(rs.getString("nomeArquivo"));
            pd.setCaminho(rs.getString("caminho"));
            pd.setStatus(rs.getInt("status"));
        }
        ConexaoFactory.close(con);
        return pd;
    }
    
    public boolean desativar(Produto pd) throws SQLException {
        sql = "UPDATE produto SET status = 0 WHERE idProduto = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, pd.getIdProduto());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public boolean ativar(Produto pd) throws SQLException {
        sql = "UPDATE produto SET status = 1 WHERE idProduto = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, pd.getIdProduto());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
}

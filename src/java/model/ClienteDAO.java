package model;

/**
 * @author mushr
 */
import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";

    public ArrayList<Cliente> getAllClientes() throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        sql = "SELECT idCliente, nome, cpf, endereco, telefone, status, dataCadastro "
                + " FROM cliente";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            Cliente c = new Cliente();
            c.setIdCliente(rs.getInt("idCliente"));
            c.setNome(rs.getString("nome"));
            c.setCpf(rs.getString("cpf"));
            c.setEndereco(rs.getString("endereco"));
            c.setTelefone(rs.getString("telefone"));
            c.setStatus(rs.getInt("status"));
            c.setDataCadastro(rs.getDate("dataCadastro"));
            clientes.add(c);
        }
        ConexaoFactory.close(con);
        return clientes;
    }

    public boolean gravar(Cliente c) throws SQLException {
        con = ConexaoFactory.conectar();

        if (c.getIdCliente() == 0) {
            sql = "INSERT INTO cliente(nome, cpf, endereco, telefone, dataCadastro, status) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getEndereco());
            ps.setString(4, c.getTelefone());
            ps.setDate(5, new Date(c.getDataCadastro().getTime()));
            ps.setInt(6, c.getStatus());
        } else {
            sql = "UPDATE cliente SET nome = ?, cpf = ?, endereco = ?, telefone = ?, dataCadastro = ?,  status = ? "
                    + "WHERE idCliente = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getEndereco());
            ps.setString(4, c.getTelefone());
            ps.setDate(5, new Date(c.getDataCadastro().getTime()));
            ps.setInt(6, c.getStatus());
            ps.setInt(7, c.getIdCliente());
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public Cliente getClientePorId(int idCliente) throws SQLException {
        Cliente c = new Cliente();
        sql = "SELECT idCliente, nome, cpf, endereco, telefone, status, dataCadastro "
                + "FROM cliente "
                + "WHERE idCliente = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idCliente);
        rs = ps.executeQuery();

        if (rs.next()) {
            c.setIdCliente(rs.getInt("idCliente"));
            c.setNome(rs.getString("nome"));
            c.setCpf(rs.getString("cpf"));
            c.setEndereco(rs.getString("endereco"));
            c.setTelefone(rs.getString("telefone"));
            c.setStatus(rs.getInt("status"));
            c.setDataCadastro(rs.getDate("dataCadastro"));
        }

        ConexaoFactory.close(con);
        return c;
    }

    public boolean desativar(Cliente c) throws SQLException {
        sql = "UPDATE cliente SET status = 0 WHERE idCliente = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, c.getIdCliente());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public boolean ativar(Cliente c) throws SQLException {
        sql = "UPDATE cliente SET status = 1 WHERE idCliente = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, c.getIdCliente());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
}

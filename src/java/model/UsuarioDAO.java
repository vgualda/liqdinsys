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


public class UsuarioDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    public ArrayList<Usuario> getLista() throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        sql = "SELECT p.idPerfil, p.nome, u.idUsuario, u.nome, u.login, "
                + "u.senha, u.status, u.idPerfil "
                + "FROM perfil p "
                + "INNER JOIN usuario u "
                + "ON p.idPerfil = u.idPerfil;";

        //alias = apelido (u=usuario / p=perfil)
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("u.idUsuario"));
            u.setNome(rs.getString("u.nome"));
            u.setLogin(rs.getString("u.login"));
            u.setSenha(rs.getString("u.senha"));
            u.setStatus(rs.getInt("u.status"));

            Perfil p = new Perfil();
            p.setIdPerfil(rs.getInt("p.idPerfil"));
            p.setNome(rs.getString("p.nome"));

            //Associacao entre usuario e perfil:
            u.setPerfil(p);

            usuarios.add(u);
        }
        ConexaoFactory.close(con);
        return usuarios;
    }

    public boolean gravar(Usuario u) throws SQLException {
        con = ConexaoFactory.conectar();

        if (u.getIdUsuario() == 0) {
            sql = "INSERT INTO USUARIO "
                    + "(nome, login, senha, status, idPerfil) "
                    + "VALUES (?,?,?,?,?);";
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getSenha());
            ps.setInt(4, u.getStatus());
            ps.setInt(5, u.getPerfil().getIdPerfil());

        } else {
            sql = "UPDATE USUARIO "
                    + "SET nome = ?, login = ?, senha = ?, "
                    + "status = ?, idPerfil = ? "
                    + "WHERE idUsuario = ?;";
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getSenha());
            ps.setInt(4, u.getStatus());
            ps.setInt(5, u.getPerfil().getIdPerfil());
            ps.setInt(6, u.getIdUsuario());
        }

        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public Usuario getUsuarioPorId(int idUsuario) throws SQLException {
        Usuario u = new Usuario();
        sql = "SELECT p.nome, p.idPerfil, u.idUsuario, "
                + "u.nome, u.login, u.status, u.idPerfil "
                + "FROM usuario u "
                + "INNER JOIN perfil p "
                + "ON p.idPerfil = u.idPerfil "
                + "WHERE u.idUsuario = ?;";

        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        rs = ps.executeQuery();

        if (rs.next()) {
            u.setIdUsuario(rs.getInt("u.idUsuario"));
            u.setNome(rs.getString("u.nome"));
            u.setLogin(rs.getString("u.login"));
            u.setStatus(rs.getInt("u.status"));

            Perfil p = new Perfil();
            p.setIdPerfil(rs.getInt("p.idPerfil"));
            p.setNome(rs.getString("p.nome"));
            
            //associação entre obj da classe Usuario e Perfil 
            u.setPerfil(p);
        }
        ConexaoFactory.close(con);
        return u;
    }

    public boolean desativar(Usuario u) throws SQLException {
        sql = "UPDATE usuario SET status = 0 "
                + "WHERE idUsuario = ?;";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, u.getIdUsuario());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    public boolean ativar(Usuario u) throws SQLException {
        sql = "UPDATE usuario SET status = 1 "
                + "WHERE idUsuario = ?;";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, u.getIdUsuario());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }

    
    public Usuario getRecuperarUsuario(String login) throws SQLException {
        Usuario u = new Usuario();
        sql = "SELECT idUsuario, nome, login, senha, status, idPerfil "
                + "FROM usuario WHERE login = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, login);
        rs = ps.executeQuery();
        if(rs.next()) {
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setNome(rs.getString("nome"));
            u.setLogin(rs.getString("login"));
            u.setSenha(rs.getString("senha"));
            u.setStatus(rs.getInt("status"));
            PerfilDAO pdao = new PerfilDAO();
            u.setPerfil(pdao.getCarregarPorId(rs.getInt("idPerfil")));
            
        }
        ConexaoFactory.close(con);
        return u;
        
    }


}

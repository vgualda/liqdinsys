package model;

import factory.ConexaoFactory;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author mushr
 */
public class PedidoDAO {

    PreparedStatement ps;
    Connection con;
    ResultSet rs;
    String sql = "";

    public boolean registrar(Pedido ped) throws SQLException {
        con = ConexaoFactory.conectar();
        sql = "INSERT INTO pedido (dataPedido, precoTotal, idCliente, idUsuario) "
                + "VALUES (now(), ?, ?, ?)";
        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, ped.getValorTotal());
        ps.setInt(2, ped.getCliente().getIdCliente());
        ps.setInt(3, ped.getUsuario().getIdUsuario());
        ps.execute();
        rs = ps.getGeneratedKeys();
        if (rs.next()) {
            ped.setIdPedido(rs.getInt(1));
        }

        for (PedidoProduto pedpro : ped.getCarrinho()) {
            String sql_pedpro = "INSERT INTO pedido_produto (idVenda, idProduto, qtd, preco) "
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement ps_pedpro = con.prepareCall(sql_pedpro);
            ps_pedpro.setInt(1, ped.getIdPedido());
            ps_pedpro.setInt(2, pedpro.getProduto().getIdProduto());
            ps_pedpro.setInt(3, pedpro.getQtd());
            ps_pedpro.setDouble(4, pedpro.getPrecoUnitario());
            ps_pedpro.execute();
        }

        ConexaoFactory.close(con);
        return true;
    }

    public ArrayList<Pedido> listar() throws SQLException {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        sql = "SELECT idPedido, dataPedido, precoTotal, idCliente, idUsuario "
                + "FROM pedido;";

        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Pedido ped = new Pedido();
            ped.setIdPedido(rs.getInt("idPedido"));
            ped.setDataPedido(rs.getDate("dataPedido"));
            ped.setValorTotal(rs.getDouble("precoTotal"));

            //Colocando o idCliente e idUsuario - registrados em cada pedido - em objetos (c) e (u)
            Cliente c = new Cliente();
            Usuario u = new Usuario();
            c.setIdCliente(rs.getInt("idCliente"));
            u.setIdUsuario(rs.getInt("idUsuario"));

            //Associando os objetos (c) e (u) ao objeto (ped)
            ped.setUsuario(u);
            ped.setCliente(c);

            pedidos.add(ped);
        }
        ConexaoFactory.close(con);
        return pedidos;
    }

    public Pedido getPedidoPorId(int idPedido) throws SQLException {
        Pedido ped = new Pedido();
        ClienteDAO cdao = new ClienteDAO();
        UsuarioDAO udao = new UsuarioDAO();
        sql = "SELECT idPedido, dataPedido, precoTotal, idCliente, idUsuario "
                + "FROM pedido "
                + "WHERE idPedido = ?;";

        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idPedido);
        rs = ps.executeQuery();

        if (rs.next()) {
            ped.setIdPedido(rs.getInt("idPedido"));
            ped.setDataPedido(rs.getDate("dataPedido"));
            ped.setValorTotal(rs.getInt("precoTotal"));
            ped.setCliente(cdao.getClientePorId(rs.getInt("idCliente")));
            ped.setUsuario(udao.getUsuarioPorId(rs.getInt("idUsuario")));
            ped.setIdPedido(rs.getInt("idPedido"));
        }
        ConexaoFactory.close(con);
        return ped;         
    }

    public PedidoProduto getPedidoProdutoPorIdPedido(int idPedido) throws SQLException {
        PedidoProduto pedprod = new PedidoProduto();
        PedidoDAO peddao = new PedidoDAO();
        ProdutoDAO pddao = new ProdutoDAO();
        sql = "SELECT pp.idVenda, pp.idProduto, pp.qtd, pp.preco "
                + "FROM pedido_produto pp "
                + "INNER JOIN pedido p "
                + "ON pp.idVenda = p.idPedido "
                + "WHERE p.idPedido = ?;";

        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        if (rs.next()) {
            pedprod.setPedido(peddao.getPedidoPorId(rs.getInt("idVenda")));
            pedprod.setProduto(pddao.getProdutoPorId(rs.getInt("idProduto")));
            pedprod.setQtd(rs.getInt("qtd"));
            pedprod.setPrecoUnitario(rs.getDouble("preco"));       
        }
        ConexaoFactory.close(con);
        return pedprod;
    }

}

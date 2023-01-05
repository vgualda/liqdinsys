package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pedido;
import model.PedidoProduto;
import model.Produto;
import model.ProdutoDAO;

/**
 *
 * @author mushr
 */
@WebServlet(name = "GerenciarCarrinho", urlPatterns = {"/gerenciarCarrinho"})
public class GerenciarCarrinho extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String mensagem = "";

        try {
            Pedido ped = (Pedido) session.getAttribute("pedido");
            ArrayList<PedidoProduto> carrinho = ped.getCarrinho();
            String acao = request.getParameter("acao");
            String idProduto = request.getParameter("idProduto");
            String qtd = request.getParameter("qtd");
            ProdutoDAO pdao = new ProdutoDAO();

            if (acao.equals("add")) {
                if (session.getAttribute("carrinho") == null) {
                    carrinho.add(new PedidoProduto(pdao.getProdutoPorId(Integer.parseInt(idProduto)), 1));
                }

                Produto pd = new Produto();
                pd = pdao.getProdutoPorId(Integer.parseInt(idProduto));
                
                PedidoProduto pedpro = new PedidoProduto();
                
                pedpro.setProduto(pd);
                pedpro.setQtd(Integer.parseInt(qtd));
                pedpro.setPrecoUnitario(pd.getPreco());
                //Associar o objeto PedidoProduto ao ArrayList<Pedido> carrinho;
                carrinho.add(pedpro);

                //Associar o objeto carrinho ao pedido;
                ped.setCarrinho(carrinho);
                session.setAttribute("pedido", ped);
                response.sendRedirect("formPedido.jsp?acao=c");

            } else if (acao.equals("del")) {
                int index = Integer.parseInt(request.getParameter("index"));
                carrinho.remove(index);
                ped.setCarrinho(carrinho);
                session.setAttribute("pedido", ped);
                response.sendRedirect("formFinalizarPedido");

            }

        } catch (SQLException e) {
            out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int isExisting(int id, ArrayList<PedidoProduto> carrinho) {
        for (int i = 0; i < carrinho.size(); i++) {
            if (carrinho.get(i).getProduto().getIdProduto() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}

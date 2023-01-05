package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Pedido;
import model.PedidoDAO;
import model.PedidoProduto;

/**
 *
 * @author mushr
 */
@WebServlet(name = "GerenciarPedido", urlPatterns = {"/gerenciarPedido"})
public class GerenciarPedido extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String acao = request.getParameter("acao");
        String mensagem = "";

        try {
            Pedido ped = (Pedido) session.getAttribute("pedido");
            PedidoDAO peddao = new PedidoDAO();

            if (acao.equals("registrar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    if (peddao.registrar(ped)) {
                        mensagem = "Pedido finalizado com sucesso!";
                    } else {
                        mensagem = "Falha ao registrar o pedido!";
                    }
                } else {
                    mensagem = "Acesso não autorizado!";
                    // fim do registrar
                }

            } 
            
            if (acao.equals("listar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    ArrayList<Pedido> pedidos = new ArrayList<>();
                    pedidos = peddao.listar();
                    for (Pedido pedido : pedidos) {
                        System.out.println(pedido);
                    }
                    RequestDispatcher dispatcher
                            = getServletContext().getRequestDispatcher("/listarPedidos.jsp");
                    request.setAttribute("pedidos", pedidos);
                    dispatcher.forward(request, response);
                } else {
                    mensagem = "Acesso não autorizado!";
                    //fim do listar
                }
            }
            
            if (acao.equals("exibirDetalhes")) {
                
            }
            
        } catch (SQLException e) {
            out.println("Erro :" + e.getMessage());
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='listarPedidos.jsp';"
                + "</script>"
        );

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}

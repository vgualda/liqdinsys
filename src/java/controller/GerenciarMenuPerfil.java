package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Perfil;
import model.PerfilDAO;

/**
 * @author mushr
 */
@WebServlet(name = "GerenciarMenuPerfil", urlPatterns = {"/gerenciarMenuPerfil"})
public class GerenciarMenuPerfil extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=utf-8");
        String acao = request.getParameter("acao");
        String idPerfil = request.getParameter("idPerfil");
        String mensagem = "";

        Perfil p = new Perfil();
        PerfilDAO pdao = new PerfilDAO();

        try {

            if (acao.equals("vincular")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    p = pdao.getCarregarPorId(Integer.parseInt(idPerfil));
                    if (p.getIdPerfil() > 0) {
                        request.setAttribute("perfilv", p);
                        despacharRequisicao(request, response);
                        
                    } else {
                        mensagem = "Perfil não localizado na base de dados!";
                    }
                } else {
                    mensagem = "Acesso não autorizado!";
                }

            } else if (acao.equals("desvincular")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    String idMenu = request.getParameter("idMenu");
                    if (idMenu.equals("") || idMenu.isEmpty()) {
                        request.setAttribute("msg", "É necessário selecionar um menu!");
                        despacharRequisicao(request, response);
                    } else {
                        if (pdao.desvincular(
                                Integer.parseInt(idMenu), Integer.parseInt(idPerfil))) {
                            mensagem = "Menu desvinculado com sucesso!";
                        } else {
                            mensagem = "Falha ao desvincular o menu!";
                        }
                    }
                } else {
                    mensagem = "Acesso não autorizado!";
                }

            } else {
                response.sendRedirect("index.jsp"); //se nao houver nenhuma especificacao de qual acao, encaminhar para a pagina home
            }

        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarMenuPerfil?acao=vincular&idPerfil=" + idPerfil + "';"
                + "</script>"
        );

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String idMenu = request.getParameter("idMenu");
        String idPerfil = request.getParameter("idPerfil");
        String mensagem = "";

        try {
            PerfilDAO pdao = new PerfilDAO();
            System.out.println(idMenu);
            if (idMenu.equals("") || idMenu.isEmpty()) {
                request.setAttribute("msg", "Escolha um menu!");
                despacharRequisicao(request, response);
            } else {
                if (pdao.vincular(
                        Integer.parseInt(idMenu), Integer.parseInt(idPerfil))) {
                    mensagem = "Menu vinculado com sucesso!";
                } else {
                    mensagem = "Falha ao vincular o menu!";
                }
            }

        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarMenuPerfil?acao=vincular&idPerfil=" + idPerfil + "';"
                + "</script>"
        );
    }

    private void despacharRequisicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/cadastrarMenuPerfil.jsp").forward(request, response);

    }
    
}

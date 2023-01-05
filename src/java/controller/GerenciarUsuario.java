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
import model.Perfil;
import model.Usuario;
import model.UsuarioDAO;

/**
 * @author mushr
 */
@WebServlet(name = "GerenciarUsuario", urlPatterns = {"/gerenciarUsuario"})
public class GerenciarUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=utf-8");
        String acao = request.getParameter("acao");
        String idUsuario = request.getParameter("idUsuario");
        String mensagem = "";

        Usuario u = new Usuario();
        UsuarioDAO udao = new UsuarioDAO();

        try {
            if (acao.equals("listar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    ArrayList<Usuario> usuarios = new ArrayList<>();
                    usuarios = udao.getLista();
                    RequestDispatcher dispatcher
                            = getServletContext().getRequestDispatcher("/listarUsuarios.jsp");
                    request.setAttribute("usuarios", usuarios);
                    dispatcher.forward(request, response);
                } else {
                    mensagem = "Acesso não autorizado!";
                }

            } else if (acao.equals("alterar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    u = udao.getUsuarioPorId(Integer.parseInt(idUsuario));
                    if (u.getIdUsuario() > 0) {
                        RequestDispatcher dispatcher
                                = getServletContext().getRequestDispatcher("/cadastrarUsuario.jsp");
                        request.setAttribute("usuario", u);
                        dispatcher.forward(request, response);
                    } else {
                        mensagem = "Usuário não encontrado na base de dados!";
                    }
                } else {
                    mensagem = "Usuário não autorizado!";
                }

            } else if (acao.equals("ativar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    u.setIdUsuario(Integer.parseInt(idUsuario));
                    if (udao.ativar(u)) {
                        mensagem = "Usuário ativado com sucesso!";
                    } else {
                        mensagem = "Falha ao ativar o usuário!";
                    }
                } else {
                    mensagem = "Usuário não autorizado!";
                }

            } else if (acao.equals("desativar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {

                    u.setIdUsuario(Integer.parseInt(idUsuario));
                    if (udao.desativar(u)) {
                        mensagem = "Usuário desativado com sucesso!";
                    } else {
                        mensagem = "Falha ao desativar o usuário!";
                    }

                } else {
                    response.sendRedirect("index.jsp");
                }
            } else {
                mensagem = "Usuário não autorizado!";
            }

        } catch (SQLException ex) {
            mensagem = "Erro: " + ex.getMessage();
            ex.printStackTrace();

        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarUsuario?acao=listar';"
                + "</script>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String idUsuario = request.getParameter("idUsuario");
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String status = request.getParameter("status");
        String idPerfil = request.getParameter("idPerfil");
        String mensagem = "";

        Usuario u = new Usuario();

        if (!idUsuario.isEmpty()) {
            u.setIdUsuario(Integer.parseInt(idUsuario));
        }

        if (nome.equals("") || nome.isEmpty()) {
            request.setAttribute("msg", "Informe o nome do usuário!");
            despacharRequisicao(request, response);

        } else {
            u.setNome(nome);
        }

        if (login.equals("") || login.isEmpty()) {
            request.setAttribute("msg", "Informe o login do usuário!");
            despacharRequisicao(request, response);
        } else {
            u.setLogin(login);
        }

        if (senha.equals("") || senha.isEmpty()) {
            request.setAttribute("msg", "Informe a senha do usuário!");
            despacharRequisicao(request, response);
        } else {
            u.setSenha(senha);
        }

        if (status.equals("") || status.isEmpty()) {
            request.setAttribute("msg", "Informe o status do usuário!");
            despacharRequisicao(request, response);
        } else {
            try {
                u.setStatus(Integer.parseInt("status"));
            } catch (NumberFormatException en) {
                mensagem = "Erro " + en.getMessage();
            }

        }

        Perfil p = new Perfil();
        if (idPerfil.equals("") || idPerfil.isEmpty()) {
            despacharRequisicao(request, response);
        } else {
            p.setIdPerfil(Integer.parseInt(idPerfil));
            //Associação
            u.setPerfil(p);
        }

        UsuarioDAO udao = new UsuarioDAO();
        try {
            if (udao.gravar(u)) {
                mensagem = "Usuário salvo com sucesso na base de dados!";

            } else {
                mensagem = "Falha ao salvar o usuário na base de dados!";
            }
        } catch (SQLException e) {
            mensagem = "Erro " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarUsuario?acao=listar';"
                + "</script>"
        );

    }

    private void despacharRequisicao(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().
                getRequestDispatcher("/cadastrarUsuario.jsp").
                forward(request, response);

    }

}

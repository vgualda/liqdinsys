package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import model.ClienteDAO;

@WebServlet(name = "GerenciarCliente", urlPatterns = {"/gerenciarCliente"})
public class GerenciarCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=utf-8");
        String acao = request.getParameter("acao");
        String idCliente = request.getParameter("idCliente");
        String mensagem = "";

        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();

        try {
            switch (acao) {

                case "listar":
                    ArrayList<Cliente> clientes = new ArrayList<Cliente>();
                    clientes = cdao.getAllClientes();
                    RequestDispatcher dispatcher = getServletContext().
                            getRequestDispatcher("/listarClientes.jsp");
                    request.setAttribute("clientes", clientes); //
                    dispatcher.forward(request, response);
                    break;

                case "alterar":
                    if (GerenciarLogin.verificarPermissao(request, response)) {
                        c = cdao.getClientePorId(Integer.parseInt(idCliente));
                        if (c.getIdCliente() > 0) {
                            request.setAttribute("cliente", c);
                            despacharRequisicao(request, response);

                        } else {
                            mensagem = "Cliente não encontrado na base dados!";
                        }
                    } else {
                        mensagem = "Usuário não autorizado!";
                    }
                    break;

                case "desativar":
                    if (GerenciarLogin.verificarPermissao(request, response)) {
                        c.setIdCliente(Integer.parseInt(idCliente));
                        if (cdao.desativar(c)) {
                            mensagem = "Cliente desativado com sucesso!";
                        } else {
                            mensagem = "Falha ao desativar o cliente!";
                        }
                    } else {
                        mensagem = "Usuário não autorizado!";
                    }
                    break;

                case "ativar":
                    if (GerenciarLogin.verificarPermissao(request, response)) {
                        c.setIdCliente(Integer.parseInt(idCliente));
                        if (cdao.ativar(c)) {
                            mensagem = "Cliente ativado com sucesso!";
                        } else {
                            mensagem = "Falha ao ativar o cliente!";
                        }
                    } else {
                        mensagem = "Usuário não autorizado!";
                    }
                    break;

                default:
                    response.sendRedirect("/index.jsp");
                    break;
            }

        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarCliente?acao=listar';"
                + "</script>");

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8"); //prepar os dados para entrada no mesmo padrão de caracteres
        String idCliente = request.getParameter("idCliente");
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String endereco = request.getParameter("endereco");
        String telefone = request.getParameter("telefone");
        String status = request.getParameter("status");
        String dataCadastro = request.getParameter("dataCadastro");
        String mensagem = "";

        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();

        if (!idCliente.isEmpty()) {
            c.setIdCliente(Integer.parseInt(idCliente));
        }

        if (nome.isEmpty() || nome.equals("")) {
            request.setAttribute("msg", "Campo Nome é obrigatório!");
            despacharRequisicao(request, response);

        } else {
            c.setNome(nome);
        }

        if (cpf.isEmpty() || cpf.equals("")) {
            request.setAttribute("msg", "Campo CPF é obrigatório!");
            despacharRequisicao(request, response);

        } else {
            c.setCpf(cpf);
        }

        if (endereco.isEmpty() || endereco.equals("")) {
            request.setAttribute("msg", "Campo endereco é obrigatório!");
            despacharRequisicao(request, response);

        } else {
            c.setEndereco(endereco);
        }

        if (telefone.isEmpty() || telefone.equals("")) {
            request.setAttribute("msg", "Campo CPF é obrigatório!");
            despacharRequisicao(request, response);

        } else {
            c.setTelefone(telefone);
        }

        //Tem como estabelecer uma data de cadastro automática?
        if (dataCadastro.isEmpty() || dataCadastro.equals("")) {
            request.setAttribute("msg", "Campo da Data de Cadastro é obrigatório!");
            despacharRequisicao(request, response);

        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                c.setDataCadastro(df.parse(dataCadastro));
            } catch (ParseException pe) {
                mensagem = "Erro: " + pe.getMessage();
                pe.printStackTrace();
            }
        }

        if (status.isEmpty() || status.equals("")) {
            request.setAttribute("msg", "Informe o status do Cliente!");
            despacharRequisicao(request, response);

        } else {
            c.setStatus(Integer.parseInt(status));
        }

        try {
            if (cdao.gravar(c)) {
                mensagem = "Cliente salvo na base de dados!";
            } else {
                mensagem = "Falha ao salvar o registro na base de dados!";

            }
        } catch (SQLException ex) {
            mensagem = "Erro: " + ex.getMessage();
            ex.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarCliente?acao=listar';"
                + "</script>"
        );

    }

    private void despacharRequisicao(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().
                getRequestDispatcher("/cadastrarCliente.jsp").
                forward(request, response);

    }

}

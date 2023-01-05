
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="model.Pedido, model.Cliente, model.Usuario, model.Produto,
         model.PedidoProduto, model.ClienteDAO, model.ProdutoDAO,
         controller.GerenciarLogin, java.util.ArrayList, 
         java.sql.SQLException" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0",
              shrink-to-fit=no>
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="fonts/css/all.css" type="text/css">
        <link rel="stylesheet" href="css/menu.css" type="text/css">
        <link rel="stylesheet" href="css/styles.css" type="text/css">
        <link rel="stylesheet" href="datatables/css/dataTables.bootstrap4.min.css" type="text/css">
        <link rel="stylesheet" href="datatables/css/jquery.dataTables.min.css" type="text/css">
        <title>Pedido - liQdin</title>
    </head>
    <body class="bg-background bg">

        <%
            //HTTP 1.1
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            // HTTP 1.0
            response.setHeader("Pragma", "no-cache");
            //HTTP Proxie
            response.setHeader("Expires", "0");

            if (session.getAttribute("ulogado") == null) {
                response.sendRedirect("formLogin.jsp");
            }
        %>

        <div id="container">

            <div id="header">
                <jsp:include page="banner.jsp"></jsp:include>
                </div>
                <div id="menu">
                <jsp:include page="menu.jsp"></jsp:include>
                </div>
                <div id="conteudo" class="bg-background">

                <%
                    Pedido ped = new Pedido();
                    Cliente c = new Cliente();
                    Usuario ulogado = new Usuario();
                    ulogado = GerenciarLogin.verificarAcesso(request, response);
                    request.setAttribute("ulogado", ulogado);

                    try {
                        String acao = request.getParameter("acao");
                        ClienteDAO cdao = new ClienteDAO();
                        ArrayList<PedidoProduto> carrinho = new ArrayList<PedidoProduto>();
                        
                        if (acao.equals("novo")) {
                            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                            c = cdao.getClientePorId(idCliente);
                            ped.setCliente(c);
                            ped.setUsuario(ulogado);
                            ped.setCarrinho(carrinho);
                            session.setAttribute("pedido", ped);
                            session.setAttribute("carrinho", carrinho);
                            
                        } else {
                            ped = (Pedido) session.getAttribute("pedido");
                            carrinho = (ArrayList<PedidoProduto>) session.getAttribute("carrinho");
                        }
                        
                    } catch (SQLException e) {
                        out.print("Erro: " + e.getMessage());
                        e.printStackTrace();
                    }

                %>

                <div class="h-100 justify-content-center align-items-center">
                    <div class="col-12">
                        <h3 class="text-center mt-3 font-weight-bold">Carrinho</h3>
                        <div class="col-sm-12">
                            <div>
                                <h5 class="text-center mt-1">
                                    <i>Qtd de produtos: <%= ped.getCarrinho().size()%></i>
                                </h5>
                            </div>
                            <div class="table-responsive">
                                <input type="hidden" name="idCliente" value="<%= ped.getCliente().getIdCliente() %>">
                                <table class="table table-hover table-active">
                                    <thead>
                                        <tr>
                                            <%
                                                ProdutoDAO pdao = new ProdutoDAO();
                                                ArrayList<Produto> lista = pdao.listar();
                                                int salto = 0;
                                                for (Produto produto : lista) {
                                            %>
                                            <th>
                                                <div>
                                                    <img class="center" width="100" height="100"
                                                         src="imagens/produtos/<%= produto.getNomeArquivo() %>">
                                                </div>
                                                <div class="mt-1">
                                                    <%=produto.getNome()%>
                                                </div>
                                                <div>
                                                    R$&nbsp;<fmt:formatNumber pattern="#,##0.00"
                                                    value="<%= produto.getPreco()%>"/>

                                                    <input type="hidden" name="idProduto"/>
                                                    <input type="text" name="qtd" value="1"
                                                           size="2" maxlength="3" readonly />
                                                </div>
                                                <div>
                                                    <a href="gerenciarCarrinho?acao=add&idProduto=<%=produto.getIdProduto()%>&qtd=1"
                                                       class="btn btn-primary btn-sm mt-1" role="button">
                                                        Adicionar&nbsp;<i class="fas fa-cart-plus"></i>
                                                    </a>
                                                </div>
                                            </th>
                                            <%
                                                salto++;
                                                if (salto == 4) {
                                            %>
                                            </th>
                                        </tr>
                                        <%
                                                    salto = 0;
                                                }
                                            }
                                        %>

                                    </thead>
                                </table>

                                <div class="d-sm-flex justify-content-sm-end">
                                    <a href="formFinalizarPedido.jsp" class="btn btn-sm btn-primary mr-2" 
                                               role="button">Prosseguir&nbsp;<i class="fas fa-arrow-right-from-bracket"></i>
                                    </a>
                                    <a href="listarClientes.jsp" class="btn btn-sm btn-danger mr-2" 
                                       role="button">Cancelar&nbsp;<i class="fas fa-stop-circle"></i>
                                    </a>
               

                                </div><!-- fim da div responsive -->
                            </div><!-- fim da div col-12 -->


                        </div><!-- fim da div justify-content -->

                    </div><!-- fim da div content -->



                </div>

                <!--JQuery.js -->
                <script src="js/jquery.min.js"></script>
                <!--Popper.js via cdn -->
                <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
                <!-- Bootstrap.js -->
                <script src="js/bootstrap.min.js"></script>
                <script src="datatables/js/jquery.dataTables.min.js"></script>
                <script src="datatables/js/dataTables.bootstrap4.min.js"></script>


                </body>
                </html>


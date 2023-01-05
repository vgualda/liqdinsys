
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
        <title>Finalizar Pedido - liQdin</title>
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
        <script type="text/javascript">
            //Função do botão excluir da página;
            function excluir(index, item) {
                if (confirm("Tem certeza que deseja excluir o item "
                        + item + "?"))
                    window.open("gerenciarCarrinho?acao=del&index=" + index, "_self");
            }


        </script>
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
                </div><!-- fim da div header -->
                <div id="menu">
                <jsp:include page="menu.jsp"></jsp:include>
                </div><!-- fim da div menu -->


                <div id="conteudo" class="bg-background" style="margin-top: 2em;">

                <%
                    Pedido ped = new Pedido();
                    try {
                        ped = (Pedido) session.getAttribute("pedido");

                    } catch (Exception e) {
                        out.print("Erro: " + e.getMessage());
                        e.printStackTrace();
                    }

                %>
                <div class="bg-background">
                    <div class="h-100 justify-content-center align-items-center">
                        <form action="gerenciarPedido" method="POST" class="">
                            <div class="justify-content-sm-center form-inline">
                                <label class="text-left btn-info btn-sm">Cliente</label>
                                <input type="text" class="form-control" name="cliente"
                                       id="idCliente" readonly 
                                       value="<%= ped.getCliente().getNome()%>">
                            </div>
                            <table class="table table-hover table-responsive-smresponsive">
                                <thead class="bg-secondary">
                                    <tr class="text-dark">
                                        <th>Item</th>
                                        <th>Produto</th>
                                        <th>Quantidade</th>
                                        <th>Preço Un.</th>
                                        <th>Subtotal</th>
                                        <th>Ação</th>
                                    </tr>
                                </thead>
                                <tbody>

                                    <%                                        double total = 0;
                                        int cont = 0;
                                        for (PedidoProduto pedpro : ped.getCarrinho()) {
                                    %>

                                    <tr>
                                        <td class="text-center">
                                            <%= cont + 1%>
                                        </td>
                                        <td>
                                            <div>

                                                <img class="center" width="70" height="70"
                                                     src="imagens/produtos/<%= pedpro.getProduto().getNomeArquivo()%>" style="border-radius: 70% " />
                                                <%= pedpro.getProduto().getNome()%>
                                            </div>
                                        </td>
                                        <td>
                                            <input class="form-control-sm" type="text" name="qtd" style="width: 50px; height: 50px"
                                                   value="<%= pedpro.getQtd()%>"/>
                                        </td>
                                        <td>
                                            R$&nbsp;<fmt:formatNumber pattern="#,##0.00"
                                            value="<%= pedpro.getPrecoUnitario()%>"/>
                                        </td>
                                        <td>
                                            R$&nbsp;<fmt:formatNumber pattern="#,##0.00"
                                            value="<%= pedpro.getPrecoUnitario() * pedpro.getQtd()%>"/>
                                        </td>
                                        <td align="center">
                                            <a href="" onclick="excluir(<%=cont%>, <%=cont + 1%>)"
                                               class="btn btn-danger btn-sm" role="button">
                                                Excluir&nbsp;<i class="fas fa-thrash-alt"></i>
                                            </a>
                                        </td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                    <%
                                            if (ped.getCarrinho().size() > 0) {
                                                total = total + (pedpro.getQtd() * pedpro.getPrecoUnitario());
                                                cont++;
                                                ped.setValorTotal(total);
                                            } else {
                                                total = total - (pedpro.getQtd() * pedpro.getPrecoUnitario());
                                                cont++;
                                                ped.setValorTotal(total);
                                            }

                                        }
                                    %>
                                </tbody>
                            </table>

                            <div class="form-inline mb-3">
                                <div class="form-group d-xl-flex justify-content-around mb-3">
                                    <label for="valorTotal" class="btn-sm text-center btn-sm btn-secondary mr-3">
                                        Preço Total
                                    </label>
                                </div>
                                <div class="form-group d-xl-flex justify-content-around">
                                    <input type="text" class="form-control-sm text-center mr-5 mb-3" name="valorTotal" maxlength="1"
                                           id="valorTotal" readonly 
                                           value="R$&nbsp;<fmt:formatNumber pattern="#,##0.00"
                                           value="<%= total%>"></fmt:formatNumber>
                                           ">
                                </div><!-- fim da div input fmt -->
                                
                                <div class="d-sm-flex justify-content-around">

                                    <a href="formPedido.jsp?acao=cont" class="btn btn-success btn-sm mr-3 mb-3" role="button">
                                        Comprar +&nbsp<i class="fas fa-cart-plus"></i>
                                    </a>

                                    <a href="gerenciarPedido?acao=registrar" class="btn btn-info btn-sm mr-3 mb-3" role="button">
                                        Finalizar Pedido&nbsp<i class="fas fa-money-check-alt"></i>
                                    </a>

                                    <a href="listarClientes.jsp?" class="btn btn-danger btn-sm mr-3 mb-3" role="button">
                                        Cancelar&nbsp<i class="fas fa-stop-circle"></i>
                                    </a>

                                </div>
                            </div>

                        </form>
                    </div><!-- fim da div valorTotal -->

                </div><!-- fim da div content-center -->

            </div><!-- fim da div bg -->


        </div><!-- fim da div conteudo -->

    </div><!-- fim da div container -->


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


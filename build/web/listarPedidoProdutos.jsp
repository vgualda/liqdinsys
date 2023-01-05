
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="model.PedidoDAO"%>
<%@page import="model.UsuarioDAO"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page import=
        "model.Pedido, model.Cliente, model.Usuario, model.Produto,
        model.PedidoProduto, model.ClienteDAO, model.ProdutoDAO,
        controller.GerenciarLogin, java.util.ArrayList, 
        java.sql.SQLException, java.text.NumberFormat, java.text.DateFormat" 
        %>

<!DOCTYPE html>
<html>
    <head>
        <title>Pedidos - liQdin</title>
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


    </head>

    <body class="bg-background mr-2">

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

                    <div class="h-100 justify-content-center align-items-center">
                        <div class="col-12">
                            <h3 class="text-center mt-3 font-weight-bold">Pedidos</h3>
                        </div>

                        <div class="table-responsive-sm">
                            <table class="table-responsive-sm table-hover bordered responsive" 
                                   id="listarPerfis">
                                <thead class="bg-secondary border-dark">

                                    <tr class="text-dark">

                                        <th>idPedido</th>
                                        <th>Produto</th>
                                        <th>Quantidade</th>
                                        <th>Preço Unitário</th>

                                    </tr>
                                </thead>

                                <tbody>
                                <%
//                                    
                                    PedidoProduto pedprod = new PedidoProduto();
                                    ProdutoDAO pddao = new ProdutoDAO();
                                    PedidoDAO peddao = new PedidoDAO();
                                    Pedido ped = new Pedido();
                                    

//                                    Locale ptBr = new Locale("pt", "BR");
//                                    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, ptBr);
//                                    NumberFormat nf = NumberFormat.getCurrencyInstance(ptBr);
//
//                                    String data = df.format(pedido.getDataPedido());
//                                    String valorTot = nf.format(pedido.getValorTotal());

                                %>
                                <tr>

                                    <td> <%= pedprod.getPedido()%></td>
                                    <td> <%= pedprod.getQtd()%> </td>
                                    <td> <%= pedprod.getPrecoUnitario()%> </td>

                                </tr>
                            </tbody>
                        </table>



                    </div><!-- fim da div responsive -->
                </div><!-- fim da div col-12 -->


            </div><!-- fim da div justify-content -->

        </div><!-- fim da div content -->



    </div>
</body>
<!--JQuery.js -->
<script src="js/jquery.min.js"></script>
<!--Popper.js via cdn -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<!-- Bootstrap.js -->
<script src="js/bootstrap.min.js"></script>
<script src="datatables/js/jquery.dataTables.min.js"></script>
<script src="datatables/js/dataTables.bootstrap4.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#listarPerfis").DataTable({
            "bJQueryUI": true,
            "lengthMenu": [[5, 10, 20, 25, -1], [5, 10, 20, 25, "Todos"]],
            "oLanguage": {
                "sProcessing": "Processando..",
                "sLengthMenu": "Mostrar _MENU_ registros",
                "sZeroRecords": "Não foram encontrados resultados",
                "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
                "sInfoFiltered": "",
                "sInfoPostFix": "",
                "sSearch": "Pesquisar",
                "sUrl": "",
                "oPaginate": {
                    "sFirst": "Primeiro",
                    "sPrevious": "Anterior",
                    "sNext": "Próximo",
                    "sLast": "Último"
                }
            }
        });
    });
</script>


</html>
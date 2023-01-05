
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <title>Perfis - liQdin</title>
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
                    <div class="h-100 justify-content-center align-items-center">
                        <div class="col-12">
                            <h3 class="text-center mt-3 font-weight-bold ">Perfis</h3>
                            <div class="col-sm-12" style="padding-bottom: 15px">
                                <a href="cadastrarPerfil.jsp"
                                   class="btn btn-dark btn-sm"
                                   role="button">Cadastrar Perfil&nbsp;
                                    <i class="fa-solid fa-floppy-disk"></i>

                                </a>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-hover table-responsive-smresponsive" 
                                       id="listarPerfis">
                                    <thead class="bg-secondary">
                                        <tr class="text-dark">
                                            <th>Código</th>
                                            <th>Nome</th>
                                            <th>Data de Cadastro</th>
                                            <th>Status</th>
                                            <th>Ação</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${perfis}" var="p">
                                        <tr>
                                            <td>${p.idPerfil}</td>
                                            <td>${p.nome}</td>
                                            <td>
                                                <fmt:formatDate pattern="dd/MM/yyyy" value = "${p.dataCadastro}" />
                                            </td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.status == 1}">
                                                        Ativado
                                                    </c:when>
                                                    <c:otherwise>
                                                        Desativado
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>
                                                <script type="text/javascript">
                                                    function confirmDesativar(id, nome) {
                                                        if (confirm('Deseja desativar o perfil ' +
                                                                nome + '?')) {
                                                            location.href = "gerenciarPerfil?acao=desativar&idPerfil=" + id;
                                                        }
                                                    }

                                                    function confirmAtivar(id, nome) {
                                                        if (confirm('Deseja ativar o perfil ' +
                                                                nome + '?')) {
                                                            location.href = "gerenciarPerfil?acao=ativar&idPerfil=" + id;
                                                        }
                                                    }
                                                </script>
                                                
                                                <div class="btn-group">
                                                    <a href="gerenciarPerfil?acao=alterar&idPerfil=${p.idPerfil}" 
                                                       class="btn btn-dark btn-sm" role="button">
                                                        Alterar&nbsp;<i class="fa-solid fa-pen-to-square"></i>
                                                    </a>
                                                    <a href="gerenciarMenuPerfil?acao=vincular&idPerfil=${p.idPerfil}" 
                                                       class="btn btn-primary btn-sm" role="button">
                                                        Vínculo&nbsp;<i class="fa-solid fa-user-tag"></i>

                                                    </a>   
                                                    <c:choose>
                                                        <c:when test="${p.status == 1}">
                                                            <button class="btn btn-danger btn-sm"
                                                                    onclick="confirmDesativar('${p.idPerfil}', '${p.nome}')">
                                                                Desativar&nbsp;
                                                                <i class="fas fa-user fa-user-lock"></i>
                                                            </button>

                                                        </c:when>
                                                        <c:otherwise>
                                                            <button class="btn btn-success btn-sm"
                                                                    onclick="confirmAtivar('${p.idPerfil}', '${p.nome}')">
                                                                Ativar&nbsp;
                                                                <i class="fa-solid fa-user-shield"></i>
                                                            </button>

                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>    
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
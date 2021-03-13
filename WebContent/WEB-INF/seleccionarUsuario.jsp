<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:plantilla>
    <jsp:body>
        <h4>Seleccionar Usuario</h4>
        <hr />
        <br />
        <div class="form-group">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>Nickname</th>
                  <th>Nombre</th>
                  <th>Apellido</th>
                  <th>Tipo</th>
                </tr>
              </thead>
              <tbody>
				<c:forEach items="${usuarios}" var="u" >
                <tr>
                  <td><a class="link" href="ConsultaUsuario?nickname=${u.getNickname()}">${u.getNickname()}</a>
                  </td>
                  <td>${u.getNombre()}</td>
                  <td>${u.getApellido()}</td>
                  <td>${u.getTipo()}</td>
                </tr>
   				</c:forEach>
              </tbody>
            </table>
        </div>
		<div class="form-group">
		  <div class="col-sm-offset-2 col-sm-10">
		  </div>
		</div>
    </jsp:body>
</t:plantilla>

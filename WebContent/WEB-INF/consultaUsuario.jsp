<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
		<h4>Consulta ${usuario.getTipo()}</h4>
         <hr />
         <br />
        <div class="form-group">
          <div class="row">
            <div class="col-md-3">
              <div class="form-group row">
 	            <c:set var="nophoto" value="${pageContext.request.contextPath}/imagenes/no-photo.jpg" />
                <img src="${usuario.getUrlFoto() != '' ? usuario.getUrlFoto() : nophoto}" class="img-fluid" alt="Foto del usuario">
              </div>
            </div>
            <div class="col-md-9">
              <div class="form-group row">
               <label class="control-label col-sm-2" for="nickname">Nickname:</label>
               <div class="col-sm-10">
                 <input type="text" class="form-control" name="nickname" id="nickname" readonly value="${usuario.getNickname()}">
               </div>
              </div>           
              <div class="form-group row">
               <label class="control-label col-sm-2" for="nombre">Nombre:</label>
               <div class="col-sm-10">
                 <input type="text" class="form-control" name="nombre" id="nombre" readonly value="${usuario.getNombre()}">
               </div>
              </div>
              <div class="form-group row">
               <label class="control-label col-sm-2" for="apellido">Apellido:</label>
               <div class="col-sm-10">
                 <input type="text" class="form-control" name="apellido" id="apellido" readonly value="${usuario.getApellido()}">
               </div>
              </div>
              <div class="form-group row">
               <label class="control-label col-sm-2" for="email">Correo:</label>
               <div class="col-sm-10">
                 <input type="email" class="form-control" name="email" id="email" readonly value="${usuario.getCorreoElectronico()}">
               </div>
              </div>
              <div class="form-group row">
               <label class="control-label col-sm-2" for="fechaNacimiento">Fecha Nac.:</label>
               <div class="col-sm-10">
				   <fmt:formatDate value="${usuario.getFechaNacimiento().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fecha"/>
	               <input type="date" class="form-control" name="fechaNacimiento" id="fechaNacimiento" readonly value="${fecha}">
               </div>
              </div>
			  <c:if test="${usuario.getTipo() == 'Docente'}">
		           <div class="form-group row">
		            <label class="control-label col-sm-2" for="instituto">Instituto:</label>
		            <div class="col-sm-10">
		              <input type="text" class="form-control" name="instituto" id="instituto" readonly value="${usuario.getInstituto()}">
		            </div>
		           </div>
	 		  </c:if>
		    </div>
          </div>
        </div>

		  <c:if test="${usuario.getTipo() == 'Docente'}">
	        <ul class="nav nav-tabs">
	          <li class="nav-item">
	            <a class="nav-link active" data-toggle="tab" href="#ediciones"><strong>Ediciones de cursos</strong></a>
	          </li>
	        </ul>
	
	        <div class="tab-content">
	          <div id="ediciones" class="tab-pane container active">
	            <table class="table table-bordered table-striped">
	              <thead>
	                <tr>
	                  <th>Nombre de la edición de curso</th>
	                  <th></th>
	                </tr>
	              </thead>
	              <tbody>
					<c:if test="${not empty usuario.getEdiciones()}">
						<c:forEach items="${usuario.getEdiciones()}" var="ed" >
				                <tr>
				                  <td><a class="link" href="ConsultaEdicionCurso?nombre=${fn:escapeXml(ed.getNombre())}">${ed.getNombre()}</a></td>
								  <td>
									<c:if test="${usuario.getNickname() == usuarioLogueado.getNickname()}">
									  	<a class="link" href="ListarAceptadosEdicionCurso?edicion=${fn:escapeXml(ed.getNombre())}">Listar aceptados</a>
				            		</c:if>
								  </td>
				                </tr>
						</c:forEach>
            		</c:if>
					<c:if test="${empty usuario.getEdiciones()}">
			                <tr>
			                  <td>No tiene...</td>
			                  <td></td>
			                </tr>
            		</c:if>
	              </tbody>
	            </table>                    
	          </div>
	        </div>
 		  </c:if>
		  <c:if test="${usuario.getTipo() == 'Estudiante'}">
	        <ul class="nav nav-tabs">
	          <li class="nav-item">
	            <a class="nav-link active" data-toggle="tab" href="#ediciones"><strong>Inscripciones a ediciones de cursos</strong></a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link" data-toggle="tab" href="#programas"><strong>Inscripciones a programas de formación</strong></a>
	          </li>
	        </ul>
	
	        <div class="tab-content">
	          <div id="ediciones" class="tab-pane container active">
	            <table class="table table-bordered table-striped">
	              <thead>
	                <tr>
	                  <th>Nombre</th>
	                  <th>Fecha</th>
	                  <th>Estado</th>
	                </tr>
	              </thead>
	              <tbody>
					<c:if test="${not empty usuario.getInscripEdiciones()}">
						<c:forEach items="${usuario.getInscripEdiciones()}" var="ed" >
		               		<tr>
				                <td><a class="link" href="ConsultaEdicionCurso?nombre=${fn:escapeXml(ed.getEdicion())}">${ed.getEdicion()}</a></td>
			                  	<td> <fmt:formatDate value="${ed.getFecha().toGregorianCalendar().time}" pattern="dd/MM/yyyy"/> </td>
			                  	<td> ${ed.getEstado()} </td>
			                </tr>
						</c:forEach>
            		</c:if>
					<c:if test="${empty usuario.getInscripEdiciones()}">
			                <tr>
			                  <td>No tiene...</td>
			                </tr>
            		</c:if>
	              </tbody>
	            </table>                    
	          </div>
	          <div id="programas" class="tab-pane container">
	            <table class="table table-bordered table-striped">
	              <thead>
	                <tr>
	                  <th>Nombre</th>
	                  <th>Fecha</th>
	                  <th>Estado</th>
	                </tr>
                  </thead>
	              <tbody>
					<c:if test="${not empty usuario.getInscripProgramas()}">
						<c:forEach items="${usuario.getInscripProgramas()}" var="pro" >
			                <tr>
			                  <td><a class="link" href="ConsultaProgramaFormacion?nombre=${fn:escapeXml(pro.getPrograma())}">${pro.getPrograma()}</a></td>
			                  <td> <fmt:formatDate value="${pro.getFecha().toGregorianCalendar().time}" pattern="dd/MM/yyyy"/> </td>
			                  <td> ${pro.getEstado()} </td>
			                </tr>
						</c:forEach>
            		</c:if>
					<c:if test="${empty usuario.getInscripProgramas()}">
			                <tr>
			                  <td>No tiene...</td>
			                </tr>
            		</c:if>
	              </tbody>
	            </table>                    
	          </div>
	        </div>
 		  </c:if>
  		<div class="form-group">
		  <div class="col-sm-offset-2 col-sm-10">
		  </div>
		</div>
    </jsp:body>
</t:plantilla>			
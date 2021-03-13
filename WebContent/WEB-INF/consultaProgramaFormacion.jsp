<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>${programa.getNombre()}</h4>
         <hr />
         <br />
           <div class="form-group row">
             <div class="col-sm-3">
 	            <c:set var="nophoto" value="${pageContext.request.contextPath}/imagenes/no-image.png" />
                <img src="${programa.getUrlImagen() != '' ? programa.getUrlImagen() : nophoto}" class="img-fluid" alt="Imagen del programa">
             </div>
             <div class="col-sm-9">
                <p>${programa.getDescripcion()}</p>
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaInicio">Fecha de inicio:</label>
             <div class="col-sm-10">
				<fmt:formatDate value="${programa.getFechaInicio().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaInicio"/>
               	<input type="date" class="form-control" name="fechaInicio" id="fechaInicio" readonly value="${fechaInicio}">
             </div>
           </div>
           <div class="form-group row">
             <label class="control-label col-sm-2" for="fechaFin">Fecha de fin:</label>
             <div class="col-sm-10">
				<fmt:formatDate value="${programa.getFechaFin().toGregorianCalendar().time}" pattern="yyyy-MM-dd" var="fechaFin"/>
               	<input type="date" class="form-control" name="fechaFin" id="fechaFin" readonly value="${fechaFin}">
             </div>
           </div>
           <ul class="nav nav-tabs">
            <li class="nav-item">
              <a class="nav-link active" data-toggle="tab" href="#cursos"><strong>Cursos</strong></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" data-toggle="tab" href="#categorias"><strong>Categorías</strong></a>
            </li>
          </ul>
           <div class="tab-content">
            <div id="cursos" class="tab-pane container active table-responsive">
                <table class="table table-bordered table-striped">
                   <thead>
                      <tr>
                        <th>Nombres</th>
                      </tr>
                   </thead>
                   <tbody>
						<c:if test="${not empty programa.getCursos()}">
							<c:forEach items="${programa.getCursos()}" var="curso" >                       
				              <tr>
				                <td><a class="link" href="SeleccionarCurso?nombre=${fn:escapeXml(curso)}">${curso}</a></td>
				              </tr>
							</c:forEach>
			       		</c:if>
						<c:if test="${empty programa.getCursos()}">
				              <tr>
				                <td>No hay cursos...</td>
				              </tr>
			       		</c:if>
                   </tbody>
                </table>                    
            </div>
            <div id="categorias" class="tab-pane container fade table-responsive">
               <table class="table table-bordered table-striped">
                  <thead>
                    <tr>
                     <th>Nombres</th>
                    </tr>
                  </thead>
                  <tbody>
					<c:if test="${not empty programa.getCategorias()}">
						<c:forEach items="${programa.getCategorias()}" var="categoria" >                       
			              <tr>
			                <td><a class="link" href="SeleccionarCursoCategoria?nombre=${fn:escapeXml(categoria)}">${categoria}</a></td>
			              </tr>
						</c:forEach>
		       		</c:if>
					<c:if test="${empty programa.getCategorias()}">
			              <tr>
			                <td>No hay categorías...</td>
			              </tr>
		       		</c:if>
                  </tbody>
               </table>                  
            </div>
         </div>
    </jsp:body>
</t:plantilla>
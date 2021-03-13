<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
         <h4>Tramitar Certificado Programa de Formación</h4>
         <hr />
         <br />
        <div class="form-group table-responsive">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
				<c:if test="${not empty inscripciones}">
					<c:forEach items="${inscripciones}" var="ins" >                       
		              <tr>
		                <td>${ins.getPrograma()}</td>
		                <td>
			                <c:if test="${!ins.isAprobada()}">	                
			                	<a class="link" href="TramitarCertificadoProgramaFormacion?programa=${fn:escapeXml(ins.getPrograma())}">Tramitar certificado</a>
		       				</c:if>
			                <c:if test="${ins.isAprobada()}">
			                	<a class="link" href="GenerarPdf?programa=${fn:escapeXml(ins.getPrograma())}"><i class="fas fa-download"></i> Descargar certificado</a>
		       				</c:if>
	       				</td>
		              </tr>
					</c:forEach>
	       		</c:if>
				<c:if test="${empty inscripciones}">
		              <tr>
		                <td>No tiene inscripciones a programas de formación...</td>
		                <td></td>
		              </tr>
	       		</c:if>
              </tbody>
            </table>
        </div>
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
        </div>
      </div>
    </jsp:body>
</t:plantilla>
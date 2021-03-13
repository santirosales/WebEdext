<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>Valorar Curso ${curso.getNombre()}</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div> 
		<form class="form-horizontal" action="ValorarCurso" method="post">
		   <input type="hidden" name="curso" id="curso" value="${fn:escapeXml(curso.getNombre())}">
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="texto">Puntaje promedio:</label>
             <div class="col-sm-10">
 	            <c:set var="promedio"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${curso.getPuntaje()}" /></c:set>
             	<input type="text" class="form-control" readonly value="${promedio}">
			</div>
           </div>           
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="texto">Puntaje:</label>
             <div class="col-sm-10">
				<div class="rate" style="font-size: 32px;" data-rate-value="${valoracion}"></div>
             	<input type="hidden" class="form-control" name="valoracion" id="valoracion" required value="${valoracion}">
				<c:if test="${existeValoracion}">
					<span id="mensaje" class="">Ya valoraste el curso, puedes modificar el puntaje si lo deseas.</span>
				</c:if>
             </div>
           </div>
           <div class="form-group row">
             <div class="col-sm-offset-2 col-sm-10">
               <button type="submit" class="btn btn-info">Guardar</button>
             </div>
           </div>		   
         </form>
    <script>
        $(document).ready(function(){
            var options = {
                max_value: 5,
                step_size: 1,
                update_input_field_name: $("#valoracion")
            }
            $(".rate").rate(options);
        });       
    </script>                  
    </jsp:body>
</t:plantilla>
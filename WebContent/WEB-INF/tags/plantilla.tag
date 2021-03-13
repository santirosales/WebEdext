<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Web EdEXT</title>

<%@ include file="_scripts.jsp"%>
</head>
<body>
	<div class="wrapper">
	  <!-- barra lateral  pordria ser: _sidebar.jsp -->
	  <%@ include file="_sidebar.jsp"%>
	  <!-- contenedor pagina va siempre-->
	  <div id="content">
	      <!-- menu del usuario _usuario.jsp -->
	      <%@ include file="_usuario.jsp"%>
	      <!-- contenido del sitio -->
	      <div id="contenido">
            <div id="spinner"  class="float-right" style="display: none;">
            	<i class='fa fa-spinner fa-pulse fa-2x' style="color: #6d7fcc;"></i>
        	</div>
	      	<jsp:doBody/>
	      </div>
	   </div>
	</div>
 <%@ include file="_pie.jsp"%>
</body>
</html>
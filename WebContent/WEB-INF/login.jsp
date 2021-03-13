<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Web EdEXT</title>

    <script type="text/javascript" src="js/jquery-3.5.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
    <script type="text/javascript" src="js/bootstrap.min.js"></script> 
    <script type="text/javascript" src="iconos/fontawesome/js/all.min.js"></script> 
</head>

<body>
	<form class="form-horizontal" action="Login" method="post">
	    <div class="container">
	        <div class="col-sm-8 mx-auto">
	            <div class="card">
	                <div class="card-header">
	                    <h5 class="card-title">Ingreso al sistema</h5>
	                </div>
	                <div class="card-body">
						<div class="form-group row">
							<label class="control-label col-sm-4" for="nickname">Nickname:</label>
							<div class="col-sm-8">
						    	<input type="text" class="form-control" name="nickname" required maxlength="45" value="${nickname}">
							</div>
						</div>
						<div class="form-group row">
							<label class="control-label col-sm-4" for="clave">Contraseña:</label>
						  	<div class="col-sm-8">
						  		<input type="password" class="form-control" name="password" maxlength="45">
						  	</div>
						</div>
						<div class="form-group row">
							<div class="form-check">
							  <input class="form-check-input" type="checkbox" value="" id="recordarme" name="recordarme" ${recordarme == true ? "checked" : ""}>
							  <label class="form-check-label" for="recordarme">
							    Recordarme
							  </label>
							</div>
						</div>
						<div class="form-group row">
		                	<div class="mx-auto">
		                		<%String error = (String)request.getAttribute("error"); %>
			                	<span id="error" class="error text-danger"><%= error == null ? "" : error %></span>
							</div>
						</div>
						<div class="form-group row">
						  <div class="col-sm-offset-4 col-sm-8">
						    <button type="submit" class="btn btn-info">Login</button>
						    <a href="Index" class="btn btn-info">Invitado</a>
						  </div>
						</div>
					</div>
	            </div>        
	        </div>
	    </div>
	</form>
</body>

</html>
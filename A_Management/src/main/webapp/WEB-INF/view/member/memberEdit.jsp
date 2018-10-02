<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원정보 수정</title>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login.js"></script>
</head>
<body>
<div class="container">
    	<div class="container">
    	<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-12">
								<a href="#" class="active" id="register-form-link">회원정보 수정</a>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="register-form" action="/member/update" method="POST" role="form" style="display: block;">
									<div class="form-group">
										<input type="text" name="userId" id="userId" tabindex="1" class="form-control" placeholder="User Id" value="${userId}" readonly="readonly">
									</div>
									<div class="form-group">
										<input type="password" name="pwd" id="pwd" tabindex="2" class="form-control" placeholder="비밀번호">
									</div>
									<div class="form-group">
										<input type="text" name="name" id="name" tabindex="1" class="form-control" placeholder="이름" value="">
									</div>
									<div class="form-group">
										<input type="hidden" name="auth" id="auth" tabindex="1" class="form-control" value="${auth}">
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="수정">
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
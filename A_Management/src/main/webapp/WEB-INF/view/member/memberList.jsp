<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/member/memberList.js"></script>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/memberList.css" />
<title>회원 관리</title>
</head>
<body>
	<div class="space100"></div>
	<div class="container">
	    <div class="row">
	       <div class="col-md-10 col-md-offset-1">
	            <div class="panel panel-default panel-table">
	              <div class="panel-heading">
	                <div class="row">
	                  <div class="col col-xs-6">
	                    <h3 class="panel-title">Panel Heading</h3>
	                  </div>
	                  <div class="col col-xs-6 text-right">
	                    <button type="button" class="btn btn-sm btn-primary btn-create" onclick="location.href='/member/create'">Create New</button>
	                  </div>
	                </div>
	              </div>
	              <div class="panel-body">
	                <table class="table table-striped table-bordered table-list">
	                  <thead class="align-center">
	                    <tr>
	                        <th class="hidden-xs">ID</th>
	                        <th>이름</th>
	                        <th>권한</th>
	                        <th>생성 일자</th>
	                        <th>수정 일자</th>
	                        <th><em class="fa fa-trash"></em></th>
	                    </tr> 
	                  </thead>
	                  <tbody class="align-center">
	                          <tr>
	                          	<td>admin</td>
	                          	<td>admin</td>
	                          	<td>1</td>
	                          	<td>2018-09-28 09:40:57</td>
	                          	<td>2018-09-28 09:40:57</td>
	                          	<td><a href class="fa fa-trash"></a></td>
	                          </tr>
	                        </tbody>
	                </table>
	            
	              </div>
	              <div class="panel-footer">
	                <div class="row">
	                  <div class="col col-xs-4">Page 1 of 5
	                  </div>
	                  <div class="col col-xs-8">
	                    <ul class="pagination hidden-xs pull-right">
	                      <li><a href="#">1</a></li>
	                      <li><a href="#">2</a></li>
	                      <li><a href="#">3</a></li>
	                      <li><a href="#">4</a></li>
	                      <li><a href="#">5</a></li>
	                    </ul>
	                    <ul class="pagination visible-xs pull-right">
	                        <li><a href="#">«</a></li>
	                        <li><a href="#">»</a></li>
	                    </ul>
	                  </div>
	                </div>
	              </div>
	            </div>
			</div>
		</div>
	</div>
</body>
</html>
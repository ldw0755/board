<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<link rel="stylesheet" href="/resources/css/mycss.css"/>
<%@include file="../includes/header.jsp"%>
<div class="container">
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Register</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Register Page</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<form action="" method="post" role="form">
					<div class="form-group">
						<label>Title</label> <input class="form-control" name="title">
					</div>
					<div class="form-group">
						<label>Content</label>
						<textarea class="form-control" rows="3" name="content"></textarea>
					</div>
					<div class="form-group">
						<label>Writer</label> <input class="form-control" name="writer" value="<sec:authentication property="principal.member.userid"/>" readonly>
					</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<button type="submit" class="btn btn-default">Submit</button>
					<button type="reset" class="btn btn-default"
						onclick="location.href='list'">List</button>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- 파일 등록 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">File Attach</div>
			<div class="panel-body">
				<div class="form-group uploadDiv">
					<input type="file" name="uploadFile" id="" multiple />
				</div>
				<div class="uploadResult">
					<ul></ul>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="bigPictureWrapper">
		<div class="bigPicture"></div>
</div>
<script>
//토큰값 생성
var csrfHeaderName = "${_csrf.headerName}";
var csrfTokenValue= "${_csrf.token}";
</script>
</div><!-- container end -->
<script src="/resources/js/register.js"></script>
<%@include file="../includes/footer.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" session="true"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">  
    <title>Signin</title>
    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Custom styles for this template -->
    <link href="/resources/css/signin.css" rel="stylesheet">    
  </head>
  <body class="text-center">
    <form class="form-signin" method="post" action="/login">      
      <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>           
      <label for="inputId" class="sr-only">아이디</label>
      <input type="text" id="inputId" name="username" class="form-control" placeholder="아이디" required autofocus>
      <label for="inputPassword" class="sr-only">비밀번호</label>
      <input type="password" id="inputPassword" name="password" class="form-control" placeholder="비밀번호" required>
      <div class="checkbox mb-3">
        <label>
        	<!-- spring-security의 remember-me 사용 위해 name 속성은 반드시 "remember-me" 로 설정한다. -->
          <input type="checkbox" name="remember-me"> Remember me
        </label>
      </div>    
      <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>       
      <p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form> 
    <h3>${error}</h3>  
  </body>
</html>
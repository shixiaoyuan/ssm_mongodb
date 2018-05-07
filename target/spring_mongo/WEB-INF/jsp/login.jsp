<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>Purple_loginform Website Template | Home :: w3layouts</title>
    <link href="<%=path%>/login/css/style.css" rel="stylesheet" type="text/css" media="all" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- -->
    <script>var __links = document.querySelectorAll('a');function __linkClick(e) { parent.window.postMessage(this.href, '*');} for (var i = 0, l = __links.length; i < l; i++) {if ( __links[i].getAttribute('data-t') == '_blank' ) { __links[i].addEventListener('click', __linkClick, false);}}</script>
    <script src="<%=path%>/login/js/jquery.min.js"></script>
    <script>$(document).ready(function(c) {
        $('.alert-close').on('click', function(c){
            $('.message').fadeOut('slow', function(c){
                $('.message').remove();
            });
        });
    });
    </script>
</head>
<body>
<!-- contact-form -->
<div class="message warning">
    <div class="inset">
        <div class="login-head">
            <h1>Login Form</h1>
            <div class="alert-close"> </div>
        </div>
        <form action="${pageContext.servletContext.contextPath}/rest/authenticate" method="post">
            <ul>
                <li>
                    <input type="text" class="text" name="username" value="Username" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Username';}" />
                    <a href="#" class=" icon user"></a>
                </li>
                <div class="clear"> </div>
                <li>
                    <input type="password" name="password" value="Password" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Password';}" />
                    <a href="#" class="icon lock"></a>
                </li>
            </ul>
            <div class="clear"> </div>
            <div class="submit">
                <input type="submit" value="Sign in" >
                <h4><a href="#">Lost your Password ?</a></h4>
                <div class="clear">  </div>
            </div>

        </form>
    </div>
</div>
<div class="clear"> </div>
<!--- footer --->
<div class="footer">
    <p>Copyright &copy; 2014.</p>
</div>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: xzx
  Date: 2016/1/20
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title></title>
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<!--  <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
    -->
	<!-- <link href="http://fonts.useso.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
     -->
	<link href="<%=path%>/resources/metronic/assets/global/plugins/font-awesome/css/fonts.css" rel="stylesheet"
		  type="text/css"/>

	<link href="<%=path%>/resources/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css"
		  rel="stylesheet" type="text/css"/>
	<link href="<%=path%>/resources/metronic/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"
		  rel="stylesheet" type="text/css"/>
	<link href="<%=path%>/resources/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet"
		  type="text/css"/>
	<link href="<%=path%>/resources/metronic/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet"
		  type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link href="<%=path%>/resources/metronic/assets/admin/pages/css/login.css" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME STYLES -->
	<link href="<%=path%>/resources/metronic/assets/global/css/components.css" id="style_components" rel="stylesheet"
		  type="text/css"/>
	<link href="<%=path%>/resources/metronic/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="<%=path%>/resources/metronic/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
	<link href="<%=path%>/resources/metronic/assets/admin/layout/css/themes/darkblue.css" rel="stylesheet"
		  type="text/css" id="style_color"/>
	<link href="<%=path%>/resources/metronic/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="<%=path%>/resources/metronic/assets/global/img/favicon.ico">

</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
<div class="menu-toggler sidebar-toggler">
</div>
<!-- END SIDEBAR TOGGLER BUTTON -->
<!-- BEGIN LOGO -->
<%--<div class="logo">
    <img src="<%=path%>/resources/metronic/assets/admin/layout/img/bgb_logo_big.png" alt=""/>
</div>--%>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content">
	<!-- BEGIN LOGIN FORM -->
	<form class="login-form" action="${pageContext.servletContext.contextPath}/rest/authenticate" method="post">
		<h3 class="form-title">系统登录123</h3>

		<div class="alert alert-danger display-hide">
			<button class="close" data-close="alert"></button>
			<span>
			Enter any username and password. </span>
		</div>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label visible-ie8 visible-ie9">用户名/手机号</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off"
				   placeholder="Username" id="username" name="username"/>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9">密码</label>
			<input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off"
				   placeholder="Password" id="password" name="password"/>
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-success uppercase">登录</button>
			<label class="rememberme check">
				<input type="checkbox" name="remember" value="1"/>Remember me </label>
			<a href="javascript:;" id="forget-password" class="forget-password">忘记密码?</a>
		</div>
		<!-- 登录错误信息 -->
		<div>
			<font> ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}</font>
		</div>
	</form>
	<!-- END LOGIN FORM -->
	<!-- BEGIN FORGOT PASSWORD FORM -->
	<form class="forget-form" action="index.html" method="post">
		<h3>忘记密码 ?</h3>

		<p>
			请输入邮件信息找回密码.
		</p>

		<div class="form-group">
			<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email"
				   name="email"/>
		</div>
		<div class="form-actions">
			<button type="button" id="back-btn" class="btn btn-default">返回</button>
			<button type="submit" class="btn btn-success uppercase pull-right">提交</button>
		</div>
	</form>
	<!-- END FORGOT PASSWORD FORM -->
</div>
<%--<div class="copyright">
    Copyright © 2015-2016
</div>--%>
<!-- END LOGIN -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="../../assets/global/plugins/respond.min.js"></script>
<script src="../../assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="<%=path%>/resources/metronic/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="<%=path%>/resources/metronic/assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="<%=path%>/resources/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js"
		type="text/javascript"></script>
<script src="<%=path%>/resources/metronic/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="<%=path%>/resources/metronic/assets/global/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="<%=path%>/resources/metronic/assets/global/plugins/uniform/jquery.uniform.min.js"
		type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="<%=path%>/resources/metronic/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
		type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="<%=path%>/resources/metronic/assets/global/scripts/metronic.js" type="text/javascript"></script>
<script src="<%=path%>/resources/metronic/assets/admin/layout/scripts/layout.js" type="text/javascript"></script>
<script src="<%=path%>/resources/metronic/assets/admin/layout/scripts/demo.js" type="text/javascript"></script>
<script src="<%=path%>/resources/metronic/assets/admin/pages/scripts/login.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
	jQuery(document).ready(function () {
		Metronic.init(); // init metronic core components
		Layout.init(); // init current layout
		Login.init();
		Demo.init();
	});
</script>
<!-- END JAVASCRIPTS -->
</body>
</html>

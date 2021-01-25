<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<script type="text/javascript">
	$(function () {
		//alert("测试页面是否加载完成");
		$("#loginAct").focus();
		$("#loginAct").val("");
		$("#loginPwd").val("");
		$("#loginBtn").click(function () {
			login();
		})
		$(window).keydown(function (event) {
			//alert(event.keyCode);
			if(event.keycode==13){
				login();
			}
		});

	})
	function login() {
		//alert("登录方法");
		//获取账号和密码,并且去除前后空格
		var loginAct = $.trim($("#loginAct").val());
		var loginPwd = $.trim($("#loginPwd").val());
		if(loginAct==""||loginPwd==""){
			$("#msg").html("<font color='#FF0000'>账号密码不能为空呀</font>");
			return false;
		}
		$.ajax({
			url:"settings/user/login",
			data:{
				"loginAct":loginAct,
				"loginPwd":loginPwd
			},
			dataType:"JSON",
			type:"POST",
			success:function (data) {
				//分析需要返回的数据
				/*
                    data:{
                        "flag":true/false,
                        //message是错误提示
                        "message",message
                    }
                * */
				if(data.flag){
					//跳转到相关页面
					document.location.href = "workbench/index.html";
				}else{
					//在span标签当中显示错误提示
					$("#msg").html(data.message);
				}

			}
		})
	}
</script>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" id="loginAct" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" id="loginPwd" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg"></span>
						
					</div>
					<button type="button" class="btn btn-primary btn-lg btn-block"  id="loginBtn" style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
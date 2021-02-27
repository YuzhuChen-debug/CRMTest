<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	Map<String,String> pmap = (Map<String, String>) application.getAttribute("pmap");
	//现在pmap当中是｛stage,properity｝
	Set<String> set = pmap.keySet();

%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
</head>
<body>
<script>
	var json = {
		<%
		for(String key:set){
			String value = pmap.get(key);
		%>
			"<%=key%>":<%=value%>,
		<%
		}
		%>
	}
	//alert(json);



	$(function () {

		pageList();
		$(".time1").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});
		$(".time2").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "top-left"
		});

		$("#create-customerName").typeahead({
			source: function (query, process) {
				$.get(
						"workbench/transaction/getCustomerName.do",
						{ "name" : query },
						function (data) {
							//alert(data);
							process(data);
						},
						"json"
				);
			},
			delay: 1500
		});

		$("#create-transactionStage").change(function () {
			//alert(1);
			var stage = $("#create-transactionStage").val();
			var propertiy = json[stage];
			$("#create-possibility").val(propertiy);
		});

		$("#searchText").keydown(function (event) {
			if(event.keyCode==13){
				$.ajax({
					url:"workbench/transaction/getActivityList.do",
					data:{
						"name":$.trim($("#searchText").val())
					},
					dataType:"json",
					type:"get",
					success:function (data) {
						//处理返回的数据
						/*
						* 	data:{success:true,aList:[{a1},{a2},{a3}]}
						* or data:{success:false,msg:msg}
						* */
						if(data.success){
							var html="";
							$.each(data.aList,function (i,n) {
								html+= '<tr>';
								html+= '	<td><input type="radio" name="activity" value="'+n.id+'"/></td>';
								html+= '	<td id="t'+n.id+'">'+n.name+'</td>';
								html+= '	<td>'+n.startDate+'</td>';
								html+= '	<td>'+n.endDate+'</td>';
								html+= '	<td>'+n.owner+'</td>';
								html+= '</tr>';
								$("#activityBody").html(html);
							})

						}else{
							alert(data.msg);
						}


					}
				})
				//alert(111);
				return false;
			}
		})
		$("#searchModalBtn").click(function () {
			var id = $("input[name=activity]:checked").val();
			var name = $("#t"+id).html();
			//alert(id);
			//alert(name);
			$("#create-activitySrc").val(name);
			$("#create-activityId").val(id);
			$("#findMarketActivity").modal("hide");
		})

		$("#searchContactsText").keydown(function (event) {
			if(event.keyCode==13){
				//alert(1);
				$.ajax({
					url:"workbench/transaction/getContactsList.do",
					data:{
						"name":$.trim($("#searchContactsText").val())
					},
					dataType:"json",
					type:"get",
					success:function (data) {
						//处理返回的数据
						/*
						* 	data:{success:true,coList:[{a1},{a2},{a3}]}
						* or data:{success:false,msg:msg}
						* */
						if(data.success){
							var html="";
							$.each(data.coList,function (i,n) {
								html+= '<tr>';
								html+= '	<td><input type="radio" name="activity" value="'+n.id+'"/></td>';
								html+= '	<td id="f'+n.id+'">'+n.fullname+'</td>';
								html+= '	<td>'+n.email+'</td>';
								html+= '	<td>'+n.mphone+'</td>';
								html+= '</tr>';

								$("#searchContactsBody").html(html);
							})

						}else{
							alert(data.msg);
						}


					}
				})
				//alert(111);
				return false;
			}
		})

		$("#searchContactsModalBtn").click(function () {
			var id = $("input[name=activity]:checked").val();
			var name = $("#f"+id).html();
			//alert(id);
			//alert(name);
			$("#create-contactsName").val(name);
			$("#create-contactsId").val(id);
			$("#findContacts").modal("hide");
		})

		$("#saveBtn").click(function () {
			var customerName=$.trim($("#create-customerName").val());
			if(customerName==null ||customerName==""){
				alert("客户名称不能为空");
			}else{
				$("#saveForm").submit();
			}

		})
		$("#cansalBtn").click(function () {
			window.location.href="workbench/transaction/index.jsp";
		})
	})
</script>

	<!-- 查找市场活动 -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;"  id="searchText" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
							</tr>
						</thead>
						<tbody id="activityBody">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>
						</tbody>
					</table>
					<button type="button" class="btn btn-primary" id="searchModalBtn">提交</button>
					<button type="button" class="btn btn-default">取消</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 查找联系人 -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">查找联系人</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" id="searchContactsText" style="width: 300px;" placeholder="请输入联系人名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>邮箱</td>
								<td>手机</td>
							</tr>
						</thead>
						<tbody id="searchContactsBody">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>
							<tr>
								<td><input type="radio" name="activity"/></td>
								<td>李四</td>
								<td>lisi@bjpowernode.com</td>
								<td>12345678901</td>
							</tr>--%>
						</tbody>
					</table>
					<button type="button" class="btn btn-primary" id="searchContactsModalBtn">提交</button>
					<button type="button" class="btn btn-default">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>创建交易</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
			<button type="button" class="btn btn-default" id="cansalBtn">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;" id="saveForm" action="workbench/transaction/save.do" method="post">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner" name="save-owner">
					<option></option>
					<c:forEach var="c" items="${uList}">
						<option value="${c.id}" ${user.id eq c.id ? "selected" : ""}>${c.name}</option>
					</c:forEach>
				  <%--<option>zhangsan</option>
				  <option>lisi</option>
				  <option>wangwu</option>--%>
				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">金额</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-amountOfMoney" name="save-money">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-transactionName" name="save-name">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">预计成交日期<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time1" readonly id="create-expectedClosingDate" name="save-expectedDate">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">客户名称<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-customerName" placeholder="支持自动补全，输入客户不存在则新建" name="save-customerName">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">阶段<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-transactionStage" name="save-stage">
			  	<option></option>
				  <c:forEach items="${stageList}" var="a">
					  <option value="${a.value}">${a.text}</option>
				  </c:forEach>
			  	<%--<option>资质审查</option>
			  	<option>需求分析</option>
			  	<option>价值建议</option>
			  	<option>确定决策者</option>
			  	<option>提案/报价</option>
			  	<option>谈判/复审</option>
			  	<option>成交</option>
			  	<option>丢失的线索</option>
			  	<option>因竞争丢失关闭</option>--%>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">类型</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType" save-type>
				  <option></option>
					<c:forEach items="${transactionTypeList}" var="a">
						<option value="${a.value}">${a.text}</option>
					</c:forEach>
				  <%--<option>已有业务</option>--%>
				  <%--<option>新业务</option>--%>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">可能性</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility" >
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">来源</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource" name="save-source">
				  <option></option>
					<c:forEach items="${sourceList}" var="a">
						<option value="${a.value}">${a.text}</option>
					</c:forEach>
				  <%--<option>广告</option>
				  <option>推销电话</option>
				  <option>员工介绍</option>
				  <option>外部介绍</option>
				  <option>在线商场</option>
				  <option>合作伙伴</option>
				  <option>公开媒介</option>
				  <option>销售邮件</option>
				  <option>合作伙伴研讨会</option>
				  <option>内部研讨会</option>
				  <option>交易会</option>
				  <option>web下载</option>
				  <option>web调研</option>
				  <option>聊天</option>--%>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findMarketActivity"><span class="glyphicon glyphicon-search" ></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-activitySrc">
				<input type="hidden" id="create-activityId" name="save-activityId"/>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">联系人名称&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#findContacts"><span class="glyphicon glyphicon-search"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-contactsName">
				<input type="hidden" id="create-contactsId" name="save-contactsId"/>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe" name="save-description"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary" name="save-contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time2" readonly id="create-nextContactTime" name="save-nextContactTime">
			</div>
		</div>
		
	</form>
</body>
</html>
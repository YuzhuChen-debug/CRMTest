<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
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
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){
		$("#createBtn").click(function () {
			$("#createForm")[0].reset("");

            $(".time").datetimepicker({
                minView: "month",
                language:  'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });
			//alert(123);
			//查询用户信息，放到所有者对话框当中
			$.ajax({
				"url":"workbench/Activity/UserList.do",
				"dataType":"json",
				"type":"get",
				"success":function (data) {
					/*
						data:[{用户列表1},{用户列表2},{用户列表3}],
					*/
					//alert(1);
					var html = "<option></option>";
					$.each(data,function (i,n) {
						html+="<option value='"+n.id+"'>"+n.name+"</option>";
					})
					$("#create-owner").html(html);
					//获取session当中的当前用户信息，设置为默认选项
					var id = "${user.id}";
					$("#create-owner").val(id);
					$("#createActivityModal").modal("show");


				}

			})



		})


		$("#saveBtn").click(function () {
			/*var owner = $.trim($("#create-owner").val());
			var name = $.trim($("#create-name").val());
			alert(name);
			alert(owner);*/
			save();
d

		})

		function save(){
			$.ajax({
				url:"workbench/Activity/save.do",
				data:{
					owner: $.trim($("#create-owner").val()),
					name: $.trim($("#create-name").val()),
					startDate: $.trim($("#create-startDate").val()),
					endDate: $.trim($("#create-endDate").val()),
					cost: $.trim($("#create-cost").val()),
					description:$.trim( $("#create-description").val())
				},
				dataType:"json",
				type:"post",
				success:function (data) {
					/*
						"data":{"success":true/false,"msg":msg}
					* */
					if(data.success){
						//刷新市场活动信息列表
						pageList(1 ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						//关闭模态窗口
						$("#createActivityModal").modal("hide");
					}else{
						alert("有问题");
					}

				}
			})
		}

		//页面杠加载的时候进行查询函数
		pageList(1 ,2);
		//点击查询按钮,进行查询函数
		$("#serchBtn").click(function () {
			$("#hidden-owner").val($("#serch-owner").val());
			$("#hidden-owner").val($("#serch-owner").val());
			$("#hidden-owner").val($("#serch-owner").val());
			$("#hidden-owner").val($("#serch-owner").val());
			//pageList(1,3);
			pageList(1 ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		})



		//查询函数,包括条件查询和分页查询
		//
		function pageList(pageNumber,pageSize) {
			$("#qx").prop("checked",false);
			$("#serch-owner").val($("#hidden-owner").val());
			$("#serch-owner").val($("#hidden-owner").val());
			$("#serch-owner").val($("#hidden-owner").val());
			$("#serch-owner").val($("#hidden-owner").val());
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			//alert("进行查询操作");
			var pageNumber = pageNumber;
			var pageSize = pageSize;
			//alert(pageNumber);
			//alert(pageSize);
			var name = $.trim($("#serch-name").val());
			var owner = $.trim($("#serch-owner").val());
			var startDate = $.trim($("#serch-startDate").val());
			var endDate = $.trim($("#serch-endDate").val());
			$.ajax({
				url:"workbench/Activity/pageList.do",
				dataType:"json",
				type:"post",
				data:{
					pageNumber:pageNumber,
					pageSize:pageSize,
					name:name,
					owner:owner,
					startDate:startDate,
					endDate:endDate
				},
				success:function (data) {
				    //alert(data.caav.aList);
				    ///alert(111);
					/*
						data:{success:true,
                              caav:{count:count,
                                    aList:[{a1},{a2},{a3}]}},
						or
						data:{success:false,msg:msg}
					* */

					if(data.success){
                        var html= "";
					    $.each(data.caav.aList,function (i,n) {
                            html += '<tr class="active">';
                            html += '	<td><input name="xz" type="checkbox"  value="'+n.id+'"/></td>';
                            html += '	<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/Activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
                            html += '	<td>'+n.owner+'</td>';
                            html += '	<td>'+n.startDate+'</td>';
                            html += '	<td>'+n.endDate+'</td>';
                            html += '</tr>';

                        })
                        $("#tbodyBtn").html(html);
						var totalPages  = data.caav.count%pageSize==0 ? data.caav.count/pageSize :parseInt(data.caav.count/pageSize)+1;
						$("#activityPage").bs_pagination({
							currentPage: pageNumber, // 页码
							rowsPerPage: pageSize, // 每页显示的记录条数
							maxRowsPerPage: 20, // 每页最多显示的记录条数
							totalPages: totalPages, // 总页数
							totalRows: data.caav.count, // 总记录条数

							visiblePageLinks: 3, // 显示几个卡片

							showGoToPage: true,
							showRowsPerPage: true,
							showRowsInfo: true,
							showRowsDefaultInfo: true,

							onChangePage : function(event, data){
								pageList(data.currentPage , data.rowsPerPage);
							}
						});
					}else{
						alert(data.msg);

					}


				}
			})
		}
		$("#qx").click(function () {
            $("input[name=xz]").prop("checked",this.checked);
        })
        $("#tbodyBtn").on("click",$("input[name=xz]"),function () {
            $("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length);
        })

		$("#deleteBtn").click(function () {
			var $xz = $("input[name=xz]:checked");
			if($xz.length==0){
				alert("请选择需要删除的选项");
			}else{
				if(confirm("您是否要删除所选项")){
					var param = "";
					for(var i = 0; i<$xz.length;i++){
						param += "id=" + $($xz[i]).val();
						if(i<$xz.length-1){
							param+="&";
						}
					}
					//这里的id应该加在哪里
					// alert(param);
					$.ajax({
						url:"workbench/Activity/delete.do",
						data:param,
						type:"post",
						dataType:"json",
						success:function (data) {
							/*
                            * 	分析一下我们需要返回什么数值
                            *  data :{success:true}
                            *  or data:{success:false,msg:msg}
                            * */
							if(data.success){
								//刷新列表
								pageList(1,2);

							}else{
								alert(data.msg);
							}
						}
					})
				}

			}
		})

		$("#editBtn").click(function () {

			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			var $xz = $("input[name=xz]:checked");
			if($xz.length==0){
				alert("请选择要修改的对象");
			}else if($xz.length>1){
				alert("一次只能修改一条");
			}else{
				var id = $xz.val();
				$.ajax({
					"url":"workbench/Activity/getUserListAndActivity.do",
					"dataType":"json",
					"type":"get",
					data:{
						id:id
					},
					"success":function (data) {
						/*
                            data:{uList:[{u1},{u2},{u3}],activity:activity}
                        */
						//alert(1);
						var html = "<option></option>";
						$.each(data.uList,function (i,n) {
							html+="<option value='"+n.id+"'>"+n.name+"</option>";
						})
						$("#edit-owner").html(html);
						//把activity列表铺在模块当中对应位置
						$("#edit-id").val(data.activity.id);
						$("#edit-owner").val(data.activity.owner);
						$("#edit-name").val(data.activity.name);
						$("#edit-startDate").val(data.activity.startDate);
						$("#edit-endDate").val(data.activity.endDate);
						$("#edit-cost").val(data.activity.cost);
						$("#edit-description").val(data.activity.description);

						$("#editActivityModal").modal("show");


					}

				})
			}

		})

		$("#updateBtn").click(function () {
			$.ajax({
				url:"workbench/Activity/update.do",
				data:{
				    id:$.trim($("#edit-id").val()),
					owner: $.trim($("#edit-owner").val()),
					name: $.trim($("#edit-name").val()),
					startDate: $.trim($("#edit-startDate").val()),
					endDate: $.trim($("#edit-endDate").val()),
					cost: $.trim($("#edit-cost").val()),
					description:$.trim( $("#edit-description").val())
				},
				dataType:"json",
				type:"post",
				success:function (data) {
					/*
						"data":{"success":true/false,"msg":msg}
					* */
					if(data.success){
						//刷新市场活动信息列表
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
								,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						//关闭模态窗口
						$("#editActivityModal").modal("hide");
					}else{
						alert(data.msg);
					}

				}
			})
		})
		
	});
	
</script>
</head>
<body>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="createForm">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								  <%--<option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>--%>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"  id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								  <%--<option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>--%>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startDate" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endDate" readonly >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<!--textare当汇总 赋值用val 不用html，并且两个标签对之间不能有空格-->
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"  id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="serch-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="serch-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="serch-startDate"  readonly/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="serch-endDate" readonly/>
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="serchBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="qx" type="checkbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tbodyBtn">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div id="activityPage">

			</div>
			
		</div>
		
	</div>
</body>
</html>
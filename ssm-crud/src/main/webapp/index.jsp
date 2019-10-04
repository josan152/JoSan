<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>员工列表</title>

<% 
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>

<!-- 引入 jquery -->
	<script type="text/javascript" src="${APP_PATH }/static/js/jquery-3.3.1.min.js"></script>
<!-- 引入样式 -->
    <link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	
</head>
<body>



	<!-- 修改 员工的模态框Modal -->
	<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">员工修改</h4>
	      </div>
	      <div class="modal-body">
				<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">empName</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="empName_update_static"></p>
								<span class="help-block"></span>	
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">email</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="email_update_input" name="email">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">gender</label>
							<div class="col-sm-10">
								<label class="radio-inline">
								  <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
								</label>
								<label class="radio-inline">
								  <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">deptName</label>
							<div class="col-sm-4">
								<!-- 部门提交部门id即可 -->
								<select class="form-control" name="dId">
								</select>
							</div>
						</div>
					</form>	
		  </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="emp_update_btn">更新</button>
	      </div>
	    </div>
	  </div>
	</div>



	<!-- 新增 员工的模态框Modal -->
	<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">员工添加</h4>
	      </div>
	      <div class="modal-body">
				<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">empName</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="empName_add_input" name="empName"
									placeholder="empName">
								<span class="help-block"></span>	
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">email</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="email_add_input" name="email"
									placeholder="TOM@152.com">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">gender</label>
							<div class="col-sm-10">
								<label class="radio-inline">
								  <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
								</label>
								<label class="radio-inline">
								  <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">deptName</label>
							<div class="col-sm-4">
								<!-- 部门提交部门id即可 -->
								<select class="form-control" name="dId" id="dept_add_select">
								</select>
							</div>
						</div>
					</form>	
		  </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
	      </div>
	    </div>
	  </div>
	</div>


	<!-- 搭建员工列表 -->
	<div class="container">
		<!-- 题目 -->
		<div class="row">
			<div class="col-md-12">
			<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary" id="emp_add_modal_btn">新增</button>
				<button class="btn btn-danger" id="emp_delete_all_btn">删除</button>
			</div>
		</div>
		<!-- 表格 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="check_all" />
							</th>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
		</div>
		<!-- 分页 -->
		<div class="row">
			<!-- 显示数目 -->
			<div class="col-md-6" id="page_info_area">
				
			</div>
			<!-- 显示分页 -->
			<div class="col-md-6" id="page_nav_area">
				
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//设置一个全局的总页码数，用于最后跳转；当前页码数，用于回到当前页
		var totalRecord,currentPage;
		//页面加载完成以后直接发送一个ajax请求，要到分页数据
		$(function(){
			//去首页
			to_page(1);
		});
		
		function to_page(pn){
			$.ajax({
				url:"${APP_PATH}/emps",
				data:"pn="+pn,
				type:"get",
				success:function(result){
					//console.log(result);
					//解析并显示员工数据
					build_emps_table(result);
					//解析并显示分页信息
					build_page_info(result);
					build_page_nav(result);
				}
			});
		}
		
		//解析并显示员工数据
		function build_emps_table(result){
			$("#emps_table tbody").empty();
			var emps = result.extend.pageInfo.list;
			$.each(emps,function(index,item){
				//alert(item.empName);
				var checkBoxTd = $("<td><input type='checkbox' class='check_item' /></td>");
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var genderTd = $("<td></td>").append(item.gender=='M'?"男":"女");
				var emailTd = $("<td></td>").append(item.email);
				var deptNameTd = $("<td></td>").append(item.dept.deptName);
				/**<button class="btn btn-primary btn-sm">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									编辑
					</button>**/
				var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn")
								.append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
				//给编辑按钮添加一个自定义的属性，得到当前信息的 id 值
				editBtn.attr("edit_id",item.empId);
				var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm delete_btn")
								.append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");	
				//给删除按钮添加一个自定义的属性，得到当前信息的 id 值
				delBtn.attr("del_id",item.empId);
				var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
				//append方法执行完成后还是返回原来的元素
				$("<tr></tr>").append(checkBoxTd)
							.append(empIdTd)
							.append(empNameTd)
							.append(genderTd)
							.append(emailTd)
							.append(deptNameTd)
							.append(btnTd)
							.appendTo("#emps_table tbody");
			});
		}
		
		//解析并显示分页信息
		function build_page_info(result){
			$("#page_info_area").empty();
			$("#page_info_area").append("当前"+result.extend.pageInfo.pageNum+"页，总"+
					result.extend.pageInfo.pages+"页，总"+
					result.extend.pageInfo.total+"条记录");
			
			totalRecord = result.extend.pageInfo.total;	
			currentPage = result.extend.pageInfo.pageNum;
		}
		
		//解析并显示分页条
		function build_page_nav(result){
			$("#page_nav_area").empty();
			var ul = $("<ul></ul>").addClass("pagination");
			//构建元素
			var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
			if(result.extend.pageInfo.hasPreviousPage == false){
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			}else{
				//给元素添加点击翻页事件
				firstPageLi.click(function(){
					to_page(1);
				});
				prePageLi.click(function(){
					to_page(result.extend.pageInfo.pageNum-1);
				});
			}
			var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
			var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			if(result.extend.pageInfo.hasNextPage == false){
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
			}else{
				nextPageLi.click(function(){
					to_page(result.extend.pageInfo.pageNum+1);
				});
				lastPageLi.click(function(){
					to_page(result.extend.pageInfo.pages);
				});
			}
			//添加首页和第一页的提示
			ul.append(firstPageLi).append(prePageLi);
			//遍历5个连续页码提示
			$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
				var numLi = $("<li></li>").append($("<a></a>").append(item));
				if(result.extend.pageInfo.pageNum == item){
					numLi.addClass("active")
				}
				numLi.click(function(){
					to_page(item);
				});
				ul.append(numLi);
			});
			//添加下一页和末页的提示
			ul.append(nextPageLi).append(lastPageLi);
			//把ul 加入到 nav元素中 
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		}
		
		//表单重置 方法
		function rest_form(ele){
			//因为js没有 重置方法，所以用 dom里的方法 需要加[0]
			$(ele)[0].reset();
			//清除表单样式
			$(ele).find("*").removeClass("has-success has-error");
			$(ele).find(".help-block").text("");
		}
		
		//点击新增按钮弹出模态框
		$("#emp_add_modal_btn").click(function(){
			//清除表单数据  表单重置(需要重置数据内容，文本框样式，提示内容)
			rest_form("#empAddModal form");
			//$("#empAddModal form")[0].reset();
			//调用ajax方法，得到部门信息
			getDepts("#dept_add_select");
			//弹出模态框
			$('#empAddModal').modal({
				backdrop:"static"
			});
		});
		
		//查出所有的部门信息并显示在下拉列表中
		function getDepts(ele){
			//清空之前下拉列表里的值
			$(ele).empty();
			$.ajax({
				url:"${APP_PATH}/depts",
				type:"GET",
				success:function(result){
					//console.log(result);
					//添加到下拉选表中
					$("#dept_add_select").empty();
					$.each(result.extend.depts,function(){
						var optionEle = $("<option></option>").append(this.deptName).attr("value",this.deptId)
						optionEle.appendTo(ele);
					});
				}
			});
		}
		
		//校验表单数据
		function validata_add_form(){
			//拿到要校验的数据，使用正则表达式
			var empName = $("#empName_add_input").val();
			var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
			//alert(regName.test(empName));
			if(!regName.test(empName)){
				//alert("用户名可以是2-5位中文或者6-16位英文和数字的组合");
				//由于验证的方法都相同，使用下面封装的方法
				show_validate_msg("#empName_add_input","error","用户名可以是2-5位中文或者6-16位英文和数字的组合");
				//$("#empName_add_input").parent().addClass("has-error");
				//$("#empName_add_input").next("span").text("用户名可以是2-5位中文或者6-16位英文和数字的组合");
				return false;
			}else{
				show_validate_msg("#empName_add_input","success","");
				//$("#empName_add_input").parent().addClass("has-success");
				//$("#empName_add_input").next("span").text("");
			};
			//校验邮箱信息 email_add_input
			var email = $("#email_add_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!regEmail.test(email)){
				//alert("邮件格式不正确");
				show_validate_msg("#email_add_input","error","邮件格式不正确");
				//$("#email_add_input").parent().addClass("has-error");
				//$("#email_add_input").next("span").text("邮件格式不正确");
				return false;
			}else{
				show_validate_msg("#email_add_input","success","");
				//$("#email_add_input").parent().addClass("has-success");
				//$("#email_add_input").next("span").text("");
			}
			return true;
		}
		
		//抽取共同的验证方法,显示验证信息
		function show_validate_msg(ele,status,msg){
			//清除当前元素的校验状态
			$(ele).parent().removeClass("has-success has-error")
			$(ele).next("span").text("");
			if("success"==status){
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			}else if("error"==status){
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		
		//校验用户名是否可用
		$("#empName_add_input").change(function(){
			//发送ajax请求校验用户名是否可用
			var empName = this.value;
			$.ajax({
				url:"${APP_PATH}/checkuser",
				data:"empName="+empName,
				type:"POST",
				success:function(result){
					if(result.code==100){
						show_validate_msg("#empName_add_input","success","用户名可用");
						$("#emp_save_btn").attr("ajax-va","success");
					}else{
						show_validate_msg("#empName_add_input","error",result.extend.va_msg);
						$("#emp_save_btn").attr("ajax-va","error");
					}
				}
				
			});
		});
		
		//点击保存，保存员工信息
		$("#emp_save_btn").click(function(){
			//模态框中填写的表单数据提交给服务器进行保存
			//要先对提交给服务器的数据进行校验
			/* if(!validata_add_form()){
				return false;
			}; */
			//判断之前的ajax用户名校验是否成功
			if($(this).attr("ajax-va")=="error"){
				return false;
			}
			
			//发送ajax请求保存员工
			//alert($("#empAddModal form").serialize());
			$.ajax({
				url:"${APP_PATH}/emp",
				type:"POST",
				data:$("#empAddModal form").serialize(),
				success:function(result){
					//alert(result.msg);
					if(result.code == 100){
						//员工保存成功
						//1、关闭模态框
						$("#empAddModal").modal("hide");
						//2、来到最后一页显示刚才保存的数据
						//发送ajax请求显示最后一页数据即可
						//to_page(9999); 
						//分页插件配置中只要跳转页数大于总页数，就当做跳转到最后一页
						//如果觉得固定数字不保险，可用总记录数代替
						to_page(totalRecord);
					}else{
						//显示失败信息
						if(undefined != result.extend.errorFields.email){
							//显示邮箱错误信息
							show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
						}
						if(undefined != result.extend.errorFields.empName){
							//显示员工名字的错误信息
							show_validate_msg("#empName_add_input","error",result.extend.errorFields.empName);
						}
						
					}
				}
			});
		});
		
		//点击编辑按钮触发事件
		//由于是先读取绑定事件 ，再创建的编辑按钮，所以不能直接用click方法
		//jQuery的on方法 绑定事件处理程序到当前选定的jQuery对象中的元素，
		//	on(events,[selector],[data],fn)
		//	events:一个或多个用空格分隔的事件类型和可选的命名空间，如"click"或"keydown.myPlugin" 。
		//  selector:一个选择器字符串用于过滤器的触发事件的选择器元素的后代。如果选择的< null或省略，当它到达选定的元素，事件总是触发。
		//  data:当一个事件被触发时要传递event.data给事件处理函数。 
		$(document).on("click",".edit_btn",function(){
			//alert("edit");
			//1、查出部门信息，并显示部门列表
			getDepts("#empUpdateModal select");
			//1、查出员工信息，显示员工信息
			getEmp($(this).attr("edit_id"));
			//把员工的id传递给模态框的更新按钮
			$("#emp_update_btn").attr("edit_id",$(this).attr("edit_id"));
			//弹出模态框
			$('#empUpdateModal').modal({
				backdrop:"static"
			});
		});
		
		
		function getEmp(id){
			$.ajax({
				url:"${APP_PATH}/emp/"+id,
				type:"GET",
				success:function(result){
					//console.log(result);
					var empData = result.extend.emp;
					$("#empName_update_static").text(empData.empName);
					$("#email_update_input").val(empData.email);
					$("#empUpdateModal input[name=gender]").val([empData.gender]);
					$("#empUpdateModal select").val([empData.dId]);
				}
			});
		}
		
		//点击更新按钮，更新员工数据
		$("#emp_update_btn").click(function(){
			//验证邮箱是否合法
			var email = $("#email_update_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!regEmail.test(email)){
				show_validate_msg("#email_update_input","error","邮件格式不正确");
				return false;
			}else{
				show_validate_msg("#email_update_input","success","");
			}
			//发送ajax请求 更新员工信息
			$.ajax({
				url:"${APP_PATH}/emp/"+$(this).attr("edit_id"),
				type:"PUT",
				data:$("#empUpdateModal form").serialize(),
				success:function(result){
					//alert(result.msg);
					//关闭对话框
					$("#empUpdateModal").modal("hide");
					//回到本页面
					to_page(currentPage);
				}
			});
		});
		
		//点击删除按钮触发事件,单个删除
		$(document).on("click",".delete_btn",function(){
			//弹出是否确认删除对话框
			//alert($(this).parents("tr").find("td:eq(1)").text());
			var empName = $(this).parents("tr").find("td:eq(2)").text();
			var empId = $(this).attr("del_id")
			if(confirm("确认删除【"+empName+"】吗?")){
				//确认  方式ajax请求删除即可
				$.ajax({
					url:"${APP_PATH}/emp/"+empId,
					type:"DELETE",
					success:function(result){
						alert(result.msg);
						//回到本页
						to_page(currentPage);
					}
				});
			}
		});
		
		//完成全选、全不选功能
		$("#check_all").click(function(){
			//可用prop方法获取这些dom原生的属性，attr方法获取自定义的属性
			//alert($(this).prop("checked"));  选中为TRUE 未选中为false
			//把全选按钮的checked状态 赋给 下面的单个按钮
			$(".check_item").prop("checked",$(this).prop("checked"));
		});
		
		//当下面5个选择框都选了的时候，全选框也要自动选上
		$(document).on("click",".check_item",function(){
			//查看已选中的个数
			//alert($(".check_item:checked").length);
			//查看已选中的个数 是否 等于当前页面选择框的个数
			var flag = $(".check_item:checked").length == $(".check_item").length;
			$("#check_all").prop("checked",flag);
		});
		
		//点击全部删除  执行批量删除
		$("#emp_delete_all_btn").click(function(){
			var empNames = "";
			var del_idstr = "";
			//$(".check_item:checked")  遍历被选中的元素
			$.each($(".check_item:checked"),function(){
				//组装员工名字的字符串
				empNames += $(this).parents("tr").find("td:eq(2)").text() + ",";
				//组装员工id的字符串
				del_idstr += $(this).parents("tr").find("td:eq(1)").text() + "-";
			});
			//拼接的字符串结尾多个 逗号，去除
			empNames = empNames.substring(0,empNames.length-1);			
			del_idstr = del_idstr.substring(0,empNames.length-1);			
			if(confirm("确认删除【"+empNames+"】吗?")){
				//确认  方式ajax请求删除即可
				$.ajax({
					url:"${APP_PATH}/emp/"+del_idstr,
					type:"DELETE",
					success:function(result){
						alert(result.msg);
						//回到本页
						to_page(currentPage);
					}
				}); 
			}
		});
		
	</script>
</body>
</html>
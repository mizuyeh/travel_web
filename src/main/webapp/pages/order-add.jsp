<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 页面meta -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<title>Travel Web</title>
	<meta name="description" content="Travel Web">
	<meta name="keywords" content="Travel Web">

<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no"
	name="viewport">

<link rel=“stylesheet”
	href="${pageContext.request.contextPath}/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/ionicons/css/ionicons.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/iCheck/square/blue.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/morris/morris.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/datepicker/datepicker3.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/datatables/dataTables.bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/treeTable/jquery.treetable.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/treeTable/jquery.treetable.theme.default.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/select2/select2.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/colorpicker/bootstrap-colorpicker.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/adminLTE/css/AdminLTE.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/adminLTE/css/skins/_all-skins.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/ionslider/ion.rangeSlider.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/ionslider/ion.rangeSlider.skinNice.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap-slider/slider.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css">
</head>

<body class="hold-transition skin-blue sidebar-mini">

	<div class="wrapper">

		<!-- 页面头部 -->
		<jsp:include page="header.jsp"></jsp:include>
		<!-- 页面头部 /-->

		<!-- 导航侧栏 -->
		<jsp:include page="aside.jsp"></jsp:include>
		<!-- 导航侧栏 /-->

		<!-- 内容区域 -->
		<div class="content-wrapper">

			<!-- 内容头部 -->
			<section class="content-header">
			<h1>
				订单管理 <small>全部订单</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="all-admin-index.html"><i
						class="fa fa-dashboard"></i> 首页</a></li>
				<li><a href="all-order-manage-list.html">订单管理</a></li>
				<li class="active">订单详情</li>
			</ol>
			</section>
			<!-- 内容头部 /-->

			<form action="${pageContext.request.contextPath}/order/save.do" id="myForm"
				  method="post">
			<!-- 正文区域 -->
			<section class="content"> <!--订单信息-->
			<div class="panel panel-default">
				<div class="panel-heading">订单信息</div>
				<div class="row data-type">

					<div class="col-md-2 title">订单编号</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" placeholder="订单编号"
							value="" name="order.orderNum">
					</div>

					<div class="col-md-2 title">出游人数</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" placeholder="出游人数"
							value="" name="order.travelerCount">
					</div>

					<div class="col-md-2 title">下单时间</div>
					<div class="col-md-10 data">
						<div class="input-group date">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" class="form-control pull-right"
								   id="datepicker-a4" placeholder="下单时间" name="order.orderTime">
						</div>
					</div>

					<div class="col-md-2 title">支付方式</div>
					<div class="col-md-4 data">
						<select class="form-control select2" style="width: 100%"
								name="order.payType">
							<option value="0" selected="selected">支付宝</option>
							<option value="1">微信</option>
							<option value="2">其他</option>
						</select>
					</div>

					<div class="col-md-2 title">订单状态</div>
					<div class="col-md-4 data">
						<select class="form-control select2" style="width: 100%"
								name="order.orderStatus">
							<option value="0" selected="selected">未支付</option>
							<option value="1">已支付</option>
						</select>
					</div>

					<div class="col-md-2 title rowHeight2x">其他信息</div>
					<div class="col-md-10 data rowHeight2x">
						<textarea class="form-control" rows="3" placeholder="其他信息"
									name = "order.orderDesc">
						</textarea>
					</div>

				</div>
			</div>

			<!--产品信息-->
			<div class="panel panel-default">
				<div class="panel-heading">产品信息</div>
				<div class="row data-type">

					<div class="col-md-2 title">产品编号</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" name="product.productNum"
							   placeholder="产品编号" value="">
					</div>
					<div class="col-md-2 title">产品名称</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" name="product.productName"
							   placeholder="产品名称" value="">
					</div>
					<div class="col-md-2 title">出发时间</div>
					<div class="col-md-4 data">
						<div class="input-group date">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" class="form-control pull-right"
								   id="datepicker-a3" placeholder="出发时间" name="product.departureTime">
						</div>
					</div>


					<div class="col-md-2 title">出发城市</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" name="product.cityName"
							   placeholder="出发城市" value="">
					</div>

					<div class="col-md-2 title">产品价格</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" placeholder="产品价格"
							   name="product.productPrice" value="">
					</div>

					<div class="col-md-2 title">产品状态</div>
					<div class="col-md-4 data">
						<select class="form-control select2" style="width: 100%"
								name="product.productStatus">
							<option value="0" selected="selected">关闭</option>
							<option value="1">开启</option>
						</select>
					</div>

					<div class="col-md-2 title rowHeight2x">其他信息</div>
					<div class="col-md-10 data rowHeight2x">
						<textarea class="form-control" rows="3" placeholder="其他信息"
								  name="product.productDesc"></textarea>
					</div>

				</div>
			</div>
			<!--订单信息/--> <!--游客信息-->
			<div class="panel panel-default panel-body1">
				<div class="panel-heading">游客信息</div>

				<button type="button" class="btn bg-maroon btn-flat margin" id="btn-add">添加游客</button>
				<!--数据列表-->
				<div class="row data-type" id="traveler-tpl">

					<div class="col-md-2 title">人群</div>
					<div class="col-md-4 data">
						<select class="form-control select2" style="width: 100%"
								name="travelerType">
							<option value="0" selected="selected">成人</option>
							<option value="1">儿童</option>
						</select>
					</div>

					<div class="col-md-2 title">游客姓名</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" name="travelerName"
							   placeholder="游客姓名" value="">
					</div>

					<div class="col-md-2 title">性别</div>
					<div class="col-md-4 data">
						<select class="form-control select2" style="width: 100%"
								name="sex">
							<option selected="selected">男</option>
							<option>女</option>
						</select>
					</div>

					<div class="col-md-2 title">电话号码</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" name="phoneNum"
							   placeholder="电话号码" value="">
					</div>

					<div class="col-md-2 title">证件类型</div>
					<div class="col-md-4 data">
						<select class="form-control select2" style="width: 100%"
								name="credentialsType">
							<option value="0" selected="selected">身份证</option>
							<option value="1">护照</option>
							<option value="2">军官证</option>
						</select>
					</div>

					<div class="col-md-2 title">证件号码</div>
					<div class="col-md-4 data">
						<input type="text" class="form-control" placeholder="证件号码"
							   name="credentialsNum" value="">
					</div>

					<hr style="border:1px dashed #000; height:1px">
				</div>
				<!--数据列表-->
			</div>
			<!--游客信息/--> <!--联系人信息-->
			<div class="panel panel-default">
				<div class="panel-heading">联系人信息</div>
				<div class="row data-type">

					<div class="col-md-2 title">会员</div>
					<div class="col-md-4 data text">
						<input type="text" class="form-control" placeholder="昵称"
							   name="member.nickname" value="">
					</div>

					<div class="col-md-2 title">联系人</div>
					<div class="col-md-4 data text">
						<input type="text" class="form-control" placeholder="姓名"
							   name="member.memberName" value="">
					</div>

					<div class="col-md-2 title">手机号码</div>
					<div class="col-md-4 data text">
						<input type="text" class="form-control" placeholder="手机号码"
							   name="member.phoneNum" value="">
					</div>

					<div class="col-md-2 title">邮箱</div>
					<div class="col-md-4 data text">
						<input type="text" class="form-control" placeholder="邮箱"
							   name="member.email" value="">
					</div>

				</div>
			</div>
			<!--联系人信息/--> <!--费用信息-->
			<%--<c:if test="${order.orderStatus==1}">
				<div class="panel panel-default">
					<div class="panel-heading">费用信息</div>
					<div class="row data-type">

						<div class="col-md-2 title">支付方式</div>
						<div class="col-md-4 data text">在线支付-${order.payTypeStr}</div>

						<div class="col-md-2 title">金额</div>
						<div class="col-md-4 data text">￥${order.product.productPrice}</div>

					</div>
				</div>
			</c:if> --%>
			<!--费用信息/--> <!--工具栏-->
			<div class="box-tools text-center">
				<button type="submit" class="btn bg-maroon">保存</button>
				<button type="button" class="btn bg-default"
					onclick="history.back(-1);">返回</button>
			</div>
			</section>
			</form>
			</div>
		</div>
		<!-- 内容区域 /-->

		<!-- 底部导航 -->
		<footer class="main-footer">
		<div class="pull-right hidden-xs">
			<b>Version</b> 1.0.8
		</div>
		<strong>Copyright &copy; 2014-2017 <a
			href="http://www.itcast.cn">研究院研发部</a>.
		</strong> All rights reserved. </footer>
		<!-- 底部导航 /-->

	</div>

	<script
		src="${pageContext.request.contextPath}/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/jQueryUI/jquery-ui.min.js"></script>
	<script>
		$.widget.bridge('uibutton', $.ui.button);
	</script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/raphael/raphael-min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/morris/morris.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/sparkline/jquery.sparkline.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/knob/jquery.knob.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/daterangepicker/moment.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.zh-CN.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datepicker/bootstrap-datepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/fastclick/fastclick.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/iCheck/icheck.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/adminLTE/js/app.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/treeTable/jquery.treetable.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/select2/select2.full.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/colorpicker/bootstrap-colorpicker.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.zh-CN.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-markdown/js/bootstrap-markdown.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-markdown/locale/bootstrap-markdown.zh.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-markdown/js/markdown.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-markdown/js/to-markdown.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/ckeditor/ckeditor.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/input-mask/jquery.inputmask.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/input-mask/jquery.inputmask.extensions.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/chartjs/Chart.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/flot/jquery.flot.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/flot/jquery.flot.resize.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/flot/jquery.flot.pie.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/flot/jquery.flot.categories.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/ionslider/ion.rangeSlider.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-slider/bootstrap-slider.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>

	<script>
		$(document).ready(function() {
			// 选择框
			$(".select2").select2();

			// WYSIHTML5编辑器
			$(".textarea").wysihtml5({
				locale : 'zh-CN'
			});
		});

		// 设置激活菜单
		function setSidebarActive(tagUri) {
			var liObj = $("#" + tagUri);
			if (liObj.length > 0) {
				liObj.parent().parent().addClass("active");
				liObj.addClass("active");
			}
		}

		function addTraveler() {
			// var travelerEditer = $('#traveler-tpl').html();
			$(".panel-body1").append($('#traveler-tpl').clone());
		}

		$(document).ready(function() {
			$('#datepicker-a3').datetimepicker({
				format : "yyyy-mm-dd hh:ii",
				autoclose : true,
				todayBtn : true,
				language : "zh-CN"
			});
			$('#datepicker-a4').datetimepicker({
				format : "yyyy-mm-dd hh:ii",
				autoclose : true,
				todayBtn : true,
				language : "zh-CN"
			});
		});

		$(document).ready(function() {
			// 增加游客
			$("#btn-add").click(function() {
				//如果当前 表单下面有多个 select元素，则需要使用下面的遍历方。#否则它只会清除第一个元素
				$("#myForm").find(".select2").each(function(){
					if ($(this).data('select2')) {
						$(this).select2('destroy');
					}
				})
				addTraveler();
				$(".select2").select2();
			});

			// 激活导航位置
			setSidebarActive("order-manage");

			// 列表按钮 
			$("#dataList td input[type='checkbox']").iCheck({
				checkboxClass : 'icheckbox_square-blue',
				increaseArea : '20%'
			});
			// 全选操作 
			$("#selall").click(function() {
				var clicks = $(this).is(':checked');
				if (!clicks) {
					$("#dataList td input[type='checkbox']").iCheck("uncheck");
				} else {
					$("#dataList td input[type='checkbox']").iCheck("check");
				}
				$(this).data("clicks", !clicks);
			});
		});
	</script>
</body>


</html>
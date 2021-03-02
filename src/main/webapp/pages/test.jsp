<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mizuyeh
  Date: 2021/2/25
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>跳转测试</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
    </script>
</head>
<body>


    执行成功！
    <form action="${pageContext.request.contextPath}/order/save.do" method="post">
        <div id="hah">
            <input type="text" name="order.orderNum">
            <input type="text" name="travelerName" placeholder="订单编号">
            <input type="text" name="sex" placeholder="产品编号">
            <input type="submit" value="提交">
        </div>
    </form>

    <button onclick="x()">点击</button>

    <script type="text/javascript">
        function x() {
            $("#hah").clone().appendTo("form");
        }
    </script>
</body>
</html>

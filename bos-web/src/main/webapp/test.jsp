<%--
  Created by IntelliJ IDEA.
  User: meng
  Date: 2018/7/24
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>

</head>
<body>

<a id="save" icon="icon-save" href="#">保存</a>
<script type="text/javascript">
    $(function(){

            $("#save").click(function () {
                $("#addSubareaForm").submit();
            })

    });
</script>

<form id="addSubareaForm" method="post" action="subareaAction_add.action">
    <table class="table-edit" width="80%" align="center">
        <tr class="title">
            <td colspan="2">分区信息</td>
        </tr>
        <tr>
            <td>选择区域</td>
            <td>
                <input class="easyui-combobox" name="region.id"
                       data-options="valueField:'id',textField:'name',mode:'remote',
    							url:'regionAction_listajax.action'" />
            </td>
        </tr>
        <tr>
            <td>关键字</td>
            <td><input type="text" name="addresskey" class="easyui-validatebox" required="true"/></td>
        </tr>
        <tr>
            <td>起始号</td>
            <td><input type="text" name="startnum" class="easyui-validatebox" required="true"/></td>
        </tr>
        <tr>
            <td>终止号</td>
            <td><input type="text" name="endnum" class="easyui-validatebox" required="true"/></td>
        </tr>
        <tr>
            <td>单双号</td>
            <td>
                <select class="easyui-combobox" name="single" style="width:150px;">
                    <option value="0">单双号</option>
                    <option value="1">单号</option>
                    <option value="2">双号</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>位置信息</td>
            <td><input type="text" name="position" class="easyui-validatebox" required="true" style="width:250px;"/></td>
        </tr>
    </table>
</form>
</body>
</html>

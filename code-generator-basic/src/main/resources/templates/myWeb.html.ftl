<!DOCTYPE html>
<html>
<head>
    <title>模版引擎测试页面</title>
</head>
<body>
<h1>欢迎来到模版引擎测试页面</h1>
<ul>
    <#-- 循环渲染导航条 -->
    <#list menuItems as item>
        <li><a href="${item.url}">${item.label}</a></li>
    </#list>
</ul>
<#-- 底部版权信息（注释部分，不会被输出）-->
<footer>
    模版引擎测试页面. Copyright &copy; 2017-${currentYear} 模版引擎测试页面.
</footer>
</body>
</html>

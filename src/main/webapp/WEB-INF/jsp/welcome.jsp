<%--@elvariable id="message" type="java.lang.String"--%>
<%--@elvariable id="name" type="java.lang.String"--%>
<%--@elvariable id="time" type="java.util.Date"--%>
<%--@elvariable id="numbers" type="java.util.List"--%>
<!DOCTYPE html>

<html lang="en">
<title>Hello world</title>
<link rel="stylesheet" type="text/css"
      href="webjars/bootstrap/3.3.5/css/bootstrap.min.css"/>
<body>
<kbd>${time}<span class="glyphicon glyphicon-console"></span>${message}</kbd>
<br/>
<p>${name}</p>
<p>${numbers}</p>
<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.min.js"></script>
</body>

</html>
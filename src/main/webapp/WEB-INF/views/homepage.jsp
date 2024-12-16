<!DOCTYPE html>
<html>
<head>
    <title>Homepage</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
       
    </script>
</head>
<body>

 <%@ include file="header.jsp" %>


<div id="searchResults"></div>

<div class="main-container">
	<h1>Welcome, <%= session.getAttribute("username") %>!</h1>
	
	<h2>Select an Option</h2>
	<form action="physicalExercises" method="get">
	    <input type="submit" value="Physical Exercises">
	</form>
	
	<form action="mentalHealthActivities" method="get">
	    <input type="submit" value="Mental Health Activities">
	</form>
</div>
</body>
</html>
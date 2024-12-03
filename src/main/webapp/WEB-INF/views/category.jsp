<%@ page import="java.util.List" %>
<%@ page import="models.Exercise" %>
<!DOCTYPE html>
<html>
<head>
    <title>Exercises for <%= request.getAttribute("category") %></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        
    </script>
</head>
<body>

<header>
	<div class="brand-container"><p>Cap'n Stone Fitness</p></div>
	<div class="search-container">
		<input type="text" id="searchInput" placeholder="Search for an exercise">

	</div>
	<div class="profile-header">
		<form action="profile" method="get">
	    <input type="submit" id="editprofile" value="Edit Profile">
	</form>
	<form action="logout" method="post">
	    <input type="submit" id="logout" value="Log Out">
	</form>
	</div>
</header>

<div id="searchResults"></div>

<h2>Exercises for <%= request.getAttribute("category") %></h2>
<%
    List<Exercise> exercises = (List<Exercise>) request.getAttribute("exercises");
    if (exercises != null) {
        for (Exercise exercise : exercises) {
%>
    <div>
        <h3><%= exercise.getName() %></h3>
        <p><%= exercise.getDescription() %></p>
        <form action="selectExercise" method="get">
            <input type="hidden" name="exerciseId" value="<%= exercise.getId() %>">
            <input type="submit" value="View Exercise">
        </form>
    </div>
<%
        }
    } else {
%>
    <p>No exercises found for this category.</p>
<%
    }
%>

<jsp:include page="backToHomeButton.jsp" />

</body>
</html>
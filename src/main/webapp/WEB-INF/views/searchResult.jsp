<%@ page import="java.util.List" %>
<%@ page import="models.Exercise" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<h2>Search Results</h2>
<%
    List<Exercise> exercises = (List<Exercise>) request.getAttribute("exercises");
    if (exercises != null && !exercises.isEmpty()) {
        for (Exercise exercise : exercises) {
%>
<div>
    <h3><%= exercise.getName() %></h3>
    <p><%= exercise.getDescription() %></p>
    <img src="<%= exercise.getImageUrl() %>" alt="<%= exercise.getName() %>">
    <form action="selectExercise" method="get">
        <input type="hidden" name="exerciseId" value="<%= exercise.getId() %>">
        <input type="submit" value="View Exercise">
    </form>
</div>
<%
    }
} else {
%>
<p>No exercises found matching your search criteria.</p>
<%
    }
%>

<jsp:include page="backToHomeButton.jsp" />

</body>

</html>
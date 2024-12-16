<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
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

 <%@ include file="header.jsp" %>

<div id="searchResults"></div>

<h2>Exercises for <%= request.getAttribute("category") %></h2>
<%
	HashMap<Integer, Exercise> favorites = (HashMap<Integer, Exercise>) session.getAttribute("favorites");
	
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
            <% 
            //verify if any exercises by category is in the favorites hash
            //if not found returns null
        	if (favorites.get(exercise.getId()) != null) {
     	%>
    		<a href="favorites?action=unfav_category&id=<%= exercise.getId()%>&category=<%= request.getAttribute("category")%>" title="Favorite" class="favorite-link">
    			<i class="fa-solid fa-star"></i> <!-- Font Awesome Star Icon -->
			</a>
		<%	
        	}
        	else {
    	%>		
    		<a href="favorites?action=fav_category&id=<%= exercise.getId()%>&category=<%= request.getAttribute("category")%>" title="Favorite" class="favorite-link">
    			<i class="fa-regular fa-star"></i> <!-- Font Awesome Star Icon -->
			</a>
    	<% 
        	}
        %>
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
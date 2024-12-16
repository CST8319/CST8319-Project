<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="models.Exercise" %>

<!DOCTYPE html>
<html>
<head>
    <title>Favorites</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        
    </script>
</head>
<body>

 <%@ include file="header.jsp" %>

<div id="searchResults"></div>

<h2>Favorites</h2>
<%
	HashMap<Integer, Exercise> favorites = (HashMap<Integer, Exercise>) session.getAttribute("favorites");
	//if favorites is empty 
	if (favorites == null) {
		favorites = new HashMap<>();
	}
	//List<Exercise> exercises = (List<Exercise>) request.getAttribute("exercises");
    if (favorites != null || !favorites.isEmpty()) {
        for (int id : favorites.keySet()) {
%>
    <div>
        <h3><%= favorites.get(id).getName() %></h3>
        <p><%= favorites.get(id).getDescription() %></p>
        <form action="selectExercise" method="get">
            <input type="hidden" name="exerciseId" value="<%= favorites.get(id).getId() %>">
            <input type="submit" value="View Exercise">
        
    		<a href="favorites?action=unfav_favorites&id=<%= favorites.get(id).getId()%>" title="Favorite" class="favorite-link">
    			<i class="fa-solid fa-star"></i> <!-- Font Awesome Star Icon -->
			</a>
		
        </form>
    </div>
<%	
		}
	} else {
%>
    		<p>No favorites found. Start adding exercises to your favorites!</p>
<%
    		}
%>

<jsp:include page="backToHomeButton.jsp" />


</body>
</html>
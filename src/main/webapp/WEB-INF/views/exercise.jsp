<%@ page import="models.Exercise" %>
<!DOCTYPE html>
<html>
<head>
    <title><%= request.getAttribute("exercise") != null ? ((Exercise) request.getAttribute("exercise")).getName() : "" %></title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
     	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
        $(document).ready(function() {
            $("#searchInput").on("input", function() {
                var query = $(this).val();
                if (query.length > 0) {
                    $.ajax({
                        url: "searchExercise",
                        type: "GET",
                        data: { query: query },
                        success: function(data) {
                            $("#searchResults").html(data);
                        }
                    });
                } else {
                    $("#searchResults").empty();
                }
            });
        });
    </script>
</head>
<body>

 <%@ include file="header.jsp" %>

<div id="searchResults"></div>

<%
    Exercise exercise = (Exercise) request.getAttribute("exercise");
    if (exercise != null) {
%>
    <h2><%= exercise.getName() %></h2>
    <p>Category: <%= exercise.getCategory() %></p>
    <p><%= exercise.getDescription() %></p>
    <img class="exercise-image" src="<%= exercise.getImageUrl() %>" alt="<%= exercise.getName() %>">
    <p><strong>Instructions:</strong></p>
    <pre><%= exercise.getInstructions() %></pre>
<%
    } else {
%>
    <p>No exercise selected.</p>
<%
    }
%>

<jsp:include page="backToHomeButton.jsp" />

</body>

</html>
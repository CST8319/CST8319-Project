<!DOCTYPE html>
<html>
<head>
    <title>Edit Profile</title>
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

<h1>Edit Profile</h1>
<form action="profile" method="post">
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="<%= request.getAttribute("firstName") %>">
    <br>
    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="<%= request.getAttribute("lastName") %>">
    <br>
    <label for="age">Age:</label>
    <input type="number" id="age" name="age" value="<%= request.getAttribute("age") %>">
    <br>
    <label for="gender">Gender:</label>
    <input type="text" id="gender" name="gender" value="<%= request.getAttribute("gender") %>">
    <br>
    <label for="weight">Weight:</label>
    <input type="number" id="weight" name="weight" value="<%= request.getAttribute("weight") %>">
    <br>
    <label for="height">Height:</label>
    <input type="number" id="height" name="height" value="<%= request.getAttribute("height") %>">
    <br>
    <input type="submit" value="Update Profile">
</form>

<form action="homepage" method="get">
    <input type="submit" value="Back to Homepage">
</form>

<form action="logout" method="get">
    <input type="submit" value="Log Out">
</form>

<% String message = (String) request.getAttribute("message");
   if (message != null) { %>
   <p style="color:green;"><%= message %></p>
<% } %>

</body>
</html>
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
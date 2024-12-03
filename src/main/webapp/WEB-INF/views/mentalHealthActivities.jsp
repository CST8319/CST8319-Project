
<!DOCTYPE html>
<html>
<head>
    <title>Mental Health Activities</title>
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

<h2>Select a Category</h2>
<form action="selectCategory" method="get">
    <input type="hidden" name="category" value="Relaxation Techniques">
    <input type="submit" value="Relaxation Techniques">
</form>
<form action="selectCategory" method="get">
    <input type="hidden" name="category" value="Mindfulness Exercises">
    <input type="submit" value="Mindfulness Exercises">
</form>
<form action="selectCategory" method="get">
    <input type="hidden" name="category" value="Anger Management">
    <input type="submit" value="Anger Management">
</form>

<jsp:include page="backToHomeButton.jsp" />

</body>

</html>

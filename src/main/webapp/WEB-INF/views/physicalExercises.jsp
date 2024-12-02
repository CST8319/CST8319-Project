<!DOCTYPE html>
<html>
<head>
    <title>Physical Exercises</title>
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

<h2>Select a Category</h2>
<form action="selectCategory" method="get">
    <input type="hidden" name="category" value="Target Muscle Groups">
    <input type="submit" value="Target Muscle Groups">
</form>
<form action="selectCategory" method="get">
    <input type="hidden" name="category" value="Cardio Exercises">
    <input type="submit" value="Cardio Exercises">
</form>
<form action="selectCategory" method="get">
    <input type="hidden" name="category" value="Yoga Poses">
    <input type="submit" value="Yoga Poses">
</form>

<jsp:include page="backToHomeButton.jsp" />

</body>

</html>
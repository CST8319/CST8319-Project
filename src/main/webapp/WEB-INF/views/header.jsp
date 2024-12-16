<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<style>
    .favorite-link {
        font-size: 20px; /* Size of the icon */
        color: #FFD43B; /* Icon color */
        text-decoration: none; /* Remove underline */
       	border-radius: 5px;
    	border: 1px solid black;
    	margin: 5px;
    	margin-right: 0px;
    	padding: 5px;
		padding-top: 6px;
    	background-color: #2A5952;
    	
    }
    .favorite-link:hover {
        background-color: #f58826;
    }
</style>
<header>
	
	<div class="brand-container"><p>Get Fit Get Well</p></div>
	<div class="search-container">
		<input type="text" id="searchInput" placeholder="Search for an exercise">

	</div>
	<div class="profile-header">
		<a href="favorites?action=view_favorites" title="Favorite" class="favorite-link">
    		<i class="fa-regular fa-star"></i> <!-- Font Awesome Star Icon -->
		</a>
		<form action="profile" method="get">
		    <input type="submit" id="editprofile" value="Edit Profile">
		</form>
		<form action="logout" method="post">
		    <input type="submit" id="logout" value="Log Out">
		</form>
	</div>
</header>
# Fitness and Mental Wellness App

This is a **Java EE** application that focuses on improving both physical fitness and mental well-being. It provides personalized workout plans, mental health activities, progress tracking, and more. Designed for fitness enthusiasts and beginners alike, this app offers a holistic approach to health.

---

## Features

### User Authentication  
- Secure account creation with a 4-digit email verification code.  
- Password reset functionality using the same email-based verification process.

### Physical Activities  
- Personalized workout recommendations based on user preferences.  
- Detailed exercise instructions and video tutorials for correct techniques.

### Mental Activities  
- Activities designed to reduce stress and boost mood.  
- Explanations provided for each activity to highlight its benefits.

### Dashboard  
- Track goals, monitor progress, and view history.  
- Access favorite exercises for convenience.

### Manual Search  
- Search exercises using filters such as difficulty, duration, and type.  

### Favorites  
- Save exercises as favorites and access them from the dashboard.

---

## Technology Stack

- **Frontend:** JSP (Java Server Pages), HTML, CSS, JavaScript  
- **Backend:** Java EE (Servlets, EJB)  
- **Database:** MySQL  
- **Authentication:** JavaMail for email verification  
- **Server:** Apache Tomcat  

---

## Installation

1. **Clone the Repository:**  
   ```bash
   git clone https://github.com/CST8319/CST8319-Project.git

2. **Set Up the Database**:
- Create a MySQL database and import the schema provided in Capstone.sql.
- Update database connection details in WEB-INF/web.xml or the persistence.xml file.

3. **Configure Email**:
- Update email server settings in WEB-INF/classes/email.properties.

4. **Deploy on Tomcat**:
- Build the project using your IDE (e.g., Eclipse or IntelliJ).
- Place the .war file in the webapps directory of your Tomcat server.
- Start the server and access the app at http://localhost:8080/fitness-mental-wellness-app.

---

## Usage
1. Create an Account: Sign up and verify your email with the 4-digit code.
2. Explore Features: Access personalized workouts, mental health activities, and track your progress.
3. Search and Save: Use filters to find specific exercises and save your favorites.
4. Monitor Progress: Use the dashboard to track goals, history, and favorites.

Enjoy!


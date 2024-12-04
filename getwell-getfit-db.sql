-- Create the database
CREATE DATABASE getwell-getfit-db;
USE getwell-getfit-db;

-- Create the registration table
CREATE TABLE registration (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(40) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    isVerified BOOLEAN DEFAULT FALSE
);

-- Create the verification_tokens table
CREATE TABLE verification_tokens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    token VARCHAR(4) NOT NULL,
    type VARCHAR(20) NOT NULL,
    FOREIGN KEY (email) REFERENCES registration(email)
);

-- Create the profile table
CREATE TABLE profile (
    userId INT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    age INT,
    gender VARCHAR(50),
    weight INT,
    height INT,
    FOREIGN KEY (userId) REFERENCES registration(id)
);

-- Create the exercises table
CREATE TABLE exercises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    description TEXT,
    image_url VARCHAR(255),
    instructions TEXT
);

-- Insert admin user into the registration table
INSERT INTO registration (username, password, email, isVerified) VALUES
('admin', 'admin', 'admin@example.com', 1);

-- Insert sample data into the exercises table
INSERT INTO exercises (name, category, description, image_url, instructions) VALUES
('Squats', 'Target Muscle Groups', 'A lower body exercise to strengthen the legs and glutes.', 'https://www.moveofitness.co.za/wp-content/uploads/2018/04/squat-to-stand-1500x1043.jpg', '1. Stand with feet shoulder-width apart.\n2. Lower your hips back and down.\n3. Keep your chest up and knees over toes.\n4. Return to the starting position.'),
('Lunges', 'Target Muscle Groups', 'A lower body exercise that targets the thighs and glutes.', 'https://th.bing.com/th/id/R.3d7ff9834052bbe36315903e67a07830?rik=YW8DCyMjkiWKkw&pid=ImgRaw&r=0', '1. Stand upright, take a step forward with one leg.\n2. Lower your hips until both knees are bent at about a 90-degree angle.\n3. Return to the starting position.\n4. Repeat with the other leg.'),
('Plank', 'Target Muscle Groups', 'A core exercise that strengthens the abdominal muscles.', 'https://thumbs.dreamstime.com/b/plank-digital-illustration-fittness-man-doing-64241315.jpg', '1. Get into a push-up position.\n2. Bend your elbows 90 degrees and rest your weight on your forearms.\n3. Keep your body in a straight line from head to feet.\n4. Hold the position for as long as you can.'),
('Bicep Curls', 'Target Muscle Groups', 'An upper body exercise to strengthen the biceps.', 'https://th.bing.com/th/id/OIP.NnVb1ooWjiWcVBgVfBExEgHaHp?rs=1&pid=ImgDetMain', '1. Stand with a dumbbell in each hand.\n2. Keep your elbows close to your torso.\n3. Curl the weights while contracting your biceps.\n4. Lower the weights back to the starting position.'),
('Tricep Dips', 'Target Muscle Groups', 'An upper body exercise that targets the triceps.', 'https://thumbs.dreamstime.com/z/man-doing-bench-tricep-dips-flat-vector-man-doing-bench-tricep-dips-flat-vector-illustration-isolated-white-background-215404254.jpg', '1. Sit on a bench or chair with hands next to your hips.\n2. Move your hips off the bench, keeping your legs straight.\n3. Lower your body by bending your elbows.\n4. Push back up to the starting position.'),
('Jumping Jacks', 'Cardio Exercises', 'A full body cardiovascular exercise.', 'https://th.bing.com/th/id/R.4735c3c11add49474cc463835e6b2702?rik=t993%2bXGDvkK5gQ&pid=ImgRaw&r=0', '1. Stand upright with your legs together and arms at your sides.\n2. Bend your knees slightly and jump into the air.\n3. As you jump, spread your legs to be about shoulder-width apart.\n4. Stretch your arms out and over your head.\n5. Jump back to the starting position.'),
('Burpees', 'Cardio Exercises', 'A full body exercise that increases heart rate.', 'https://th.bing.com/th/id/OIP._7Dss4kJ1oqnOTqzYLYDGwHaHa?rs=1&pid=ImgDetMain', '1. Start in a standing position.\n2. Drop into a squat position with your hands on the ground.\n3. Kick your feet back into a push-up position.\n4. Lower your chest to do a push-up.\n5. Return to the squat position.\n6. Jump up into the air.'),
('Mountain Climbers', 'Cardio Exercises', 'A full body exercise that targets the core and increases heart rate.', 'https://th.bing.com/th/id/OIP.XZz9Fi1JhD5ng8YIkUSZYAHaHa?rs=1&pid=ImgDetMain', '1. Start in a plank position.\n2. Draw your right knee to your chest.\n3. Switch legs, bringing your left knee to your chest.\n4. Continue alternating legs at a quick pace.'),
('Childs Pose', 'Yoga Poses', 'A yoga pose for relaxation and stretching the back.', 'https://th.bing.com/th/id/OIP.iZJtKS0Kp5Csq9JN903I-AHaEc?rs=1&pid=ImgDetMain', '1. Kneel on the floor.\n2. Sit back on your heels and bend forward.\n3. Extend your arms in front of you, palms down.\n4. Rest your forehead on the ground.'),
('Warrior II', 'Yoga Poses', 'A yoga pose that strengthens the legs and improves balance.', 'https://th.bing.com/th/id/OIP.LD2wKinhk3oV0TDq00b3IwAAAA?rs=1&pid=ImgDetMain', '1. Stand with your feet wide apart.\n2. Turn your right foot out 90 degrees.\n3. Bend your right knee over your right ankle.\n4. Extend your arms out to the sides, palms down.\n5. Hold the pose and then switch sides.'),
('Cat-Cow Pose', 'Yoga Poses', 'A yoga pose that improves flexibility and stretches the spine.', 'https://th.bing.com/th/id/OIP.PzUyakQmhEiek5vXyC4SpgHaE-?rs=1&pid=ImgDetMain', '1. Start on your hands and knees.\n2. Inhale and arch your back (Cow Pose).\n3. Exhale and round your back (Cat Pose).\n4. Repeat the movement.'),
('Progressive Muscle Relaxation', 'Relaxation Techniques', 'A technique to reduce muscle tension through systematic tensing and relaxing of muscle groups.', 'https://th.bing.com/th/id/OIP.rEi_SmgK8_TL_6CHviW7nwHaEK?rs=1&pid=ImgDetMain', '1. Find a quiet place to sit or lie down.\n2. Tense each muscle group for 5-10 seconds.\n3. Release the tension and relax for 10-20 seconds.\n4. Move on to the next muscle group.'),
('Guided Imagery', 'Relaxation Techniques', 'A relaxation technique that involves visualizing peaceful images.', 'https://th.bing.com/th/id/R.c216680317983c1d600d310f55850e42?rik=OU1zKYG4xWuk7w&pid=ImgRaw&r=0', '1. Find a comfortable place to sit or lie down.\n2. Close your eyes and take deep breaths.\n3. Imagine yourself in a peaceful setting.\n4. Focus on the details of the scene and let yourself relax.'),
('Body Scan Meditation', 'Mindfulness Exercises', 'A mindfulness practice that involves focusing attention on different parts of the body.', 'https://th.bing.com/th/id/OIP.4G5UpK1hVV8ovQ5e5fDH9QHaCw?rs=1&pid=ImgDetMain', '1. Find a quiet place to lie down.\n2. Close your eyes and take deep breaths.\n3. Focus your attention on each part of your body, starting from your toes and moving up to your head.\n4. Notice any sensations and let go of any tension.'),
('Mindful Breathing', 'Mindfulness Exercises', 'A mindfulness practice that involves focusing on the breath.', 'https://th.bing.com/th/id/OIP.Iv88pmTFRTCK3MVBYd9OOAHaGC?rs=1&pid=ImgDetMain', '1. Find a comfortable place to sit.\n2. Close your eyes and take deep breaths.\n3. Focus on the sensation of your breath entering and leaving your body.\n4. If your mind wanders, gently bring your attention back to your breath.'),
('Counting to Ten', 'Anger Management', 'A technique to manage anger by counting to ten before reacting.', 'https://th.bing.com/th/id/OIP.5zpS9YgyvsUH4tZ1WflszQHaGL?rs=1&pid=ImgDetMain', '1. When you feel angry, pause and take a deep breath.\n2. Slowly count to ten in your mind.\n3. Allow yourself to calm down before responding.'),
('Physical Exercise', 'Anger Management', 'Using physical activity as a way to manage anger.', 'https://th.bing.com/th/id/OIP.ObR0bAwZKx16ZBegl1hbzgAAAA?rs=1&pid=ImgDetMain', '1. Engage in a physical activity such as running, swimming, or lifting weights.\n2. Channel your anger into the exercise.\n3. Allow the physical exertion to help you release pent-up energy and frustration.');

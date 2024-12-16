package factories;

import models.Exercise;
import models.PhysicalHealth;
import models.MentalHealth;	

public class ExerciseFactory {

	public static Exercise createExercise(int id, String name, String category, String description, String imageUrl, String instructions) {
		switch(category.toLowerCase()) {
			case "target muscle groups":
			case "cardio exercises":
			case "yoga poses":
				return new PhysicalHealth(id, name, category, description, imageUrl, instructions);
			case "relaxation techniques":
			case "mindfulness exercises":
			case "anger management":
					return new MentalHealth(id, name, category, description, imageUrl, instructions);
			default:
				throw new IllegalArgumentException("Unknown category for Activity: " + category);
		}
		
	}
	//This initializes a constructor for storing favorites (without image url or instructions)
	public static Exercise createExercise(int id, String name, String category, String description) {
		switch(category.toLowerCase()) {
			case "target muscle groups":
			case "cardio exercises":
			case "yoga poses":
				return new PhysicalHealth(id, name, category, description, "", "");
			case "relaxation techniques":
			case "mindfulness exercises":
			case "anger management":
					return new MentalHealth(id, name, category, description, "", "");
			default:
				throw new IllegalArgumentException("Unknown category for Activity: " + category);
		}
		
	}
}


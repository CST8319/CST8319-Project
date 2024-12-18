package services;

import beans.Profile;

/**
 * Service interface for managing user profiles.
 * Provides methods to retrieve, save, and update profiles.
 */
public interface ProfileService {

	/**
	 * Retrieves a {@link Profile} based on the given user ID.
	 *
	 * @param userId the unique identifier of the user
	 * @return the {@link Profile} associated with the specified user ID
	 */
	Profile getProfileByUserId(int userId);

	/**
	 * Saves a new profile or updates an existing profile.
	 *
	 * @param profile the {@link Profile} object to save or update
	 */
	void saveOrUpdateProfile(Profile profile);
}

package services;

import beans.Profile;

public interface ProfileService {
	Profile getProfileByUserId(int userId);
	void saveOrUpdateProfile(Profile profile);
}
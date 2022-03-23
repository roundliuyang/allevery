package com.yly.EntityToDtoConversion.service;


import com.yly.EntityToDtoConversion.model.Preference;
import com.yly.EntityToDtoConversion.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Override
    public User getCurrentUser() {
        
        Preference preference = new Preference();
        preference.setId(1L);
        preference.setTimezone("Asia/Calcutta");
        
        User user = new User();
        user.setId(1L);
        user.setName("Micheal");
        user.setPreference(preference);
                
        return user;
    }
}
package com.flood_web.security.service;

import com.flood_web.data.repository.AdministratorsRepository;
import com.flood_web.security.CustomUserDetail;
import com.flood_web.service.crud.AdministratorsCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UpdateProfileService {


    @Autowired
    private AdministratorsCrudService administratorsCrudService;

    @Autowired
    private AdministratorsRepository administratorsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Method to change the password of the logged-in user.
     *
     * @param oldPassword The current password of the user.
     * @param newPassword The new password to be set.
     * @return true if the password was changed successfully, false otherwise.
     */
    public boolean changePassword(String oldPassword, String newPassword) {

        CustomUserDetail loggedUser = (CustomUserDetail) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        AtomicBoolean changed = new AtomicBoolean(false);

        administratorsRepository.findByCedula(loggedUser.getCedula()).ifPresent(administrator -> {
            if (passwordEncoder.matches(oldPassword, administrator.getPassword())) {
                administrator.setPassword(passwordEncoder.encode(newPassword));
                administratorsRepository.save(administrator);
                changed.set(true);
            }
        });

        return changed.get();
    }
}

package com.mashangshouche.car.common;

import com.mashangshouche.car.entity.User;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

//@Configuration
public class UserIdAuditorBean implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        User user = AuthHold.currentUser();
        if(null != user)
            return Optional.ofNullable(user.getId());
        return Optional.empty();
    }
}

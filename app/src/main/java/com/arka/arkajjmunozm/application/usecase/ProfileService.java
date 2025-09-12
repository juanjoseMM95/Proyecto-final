package com.arka.arkajjmunozm.application.usecase;

import com.arka.arkajjmunozm.domain.model.Profile;
import com.arka.arkajjmunozm.domain.port.in.IProfileService;
import com.arka.arkajjmunozm.infraestructure.adapter.out.persistence.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {

    private final ProfileRepository profileRepository;

    public Profile getUserById(int id){
        return profileRepository.findById(id).orElse(null);
    }
}

package com.arka.arkajjmunozm.domain.port.in;

import com.arka.arkajjmunozm.domain.model.Profile;

public interface IProfileService {
    Profile getUserById(int id);
}

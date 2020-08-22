package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.repository.RolAccountRepository;
import com.schedulepilot.core.service.RolAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class RolAccountServiceImp implements RolAccountService {

    @Autowired
    private RolAccountRepository rolAccountRepository;

    @Override
    @Transactional
    public List<RolAccountDto> getAll() {
        List<RolAccountDto> list = new ArrayList<>();
        this.rolAccountRepository.findAll().forEach(e -> list.add(RolAccountService.convertEntityToDTO(e)));
        return list;
    }
}

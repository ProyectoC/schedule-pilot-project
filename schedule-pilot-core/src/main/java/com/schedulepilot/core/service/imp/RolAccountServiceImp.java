package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.RolAccountDto;
import com.schedulepilot.core.entities.model.RolAccountEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.RolAccountRepository;
import com.schedulepilot.core.service.RolAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RolAccountServiceImp implements RolAccountService {

    @Autowired
    private RolAccountRepository rolAccountRepository;

    @Override
    public List<RolAccountDto> getAll() {
        List<RolAccountDto> list = new ArrayList<>();
        this.rolAccountRepository.findAll().forEach(e -> list.add(RolAccountService.convertEntityToDTO(e)));
        return list;
    }

    @Override
    public List<RolAccountDto> getAllWithoutSuperAdmin() {
        List<RolAccountDto> list = new ArrayList<>();
        this.rolAccountRepository.findAllWithoutSuperAdmin().forEach(e -> list.add(RolAccountService.convertEntityToDTO(e)));
        return list;
    }

    @Override
    public RolAccountDto getByIdOrException(Long id) throws SchedulePilotException {
        Optional<RolAccountEntity> rolAccountEntityOptional = this.rolAccountRepository.findById(id);
        return rolAccountEntityOptional.map(RolAccountService::convertEntityToDTO).orElseThrow(() -> new SchedulePilotException("Rol Account Not Found"));
    }

    @Override
    public RolAccountEntity getByIdEntityOrException(Long id) throws SchedulePilotException {
        Optional<RolAccountEntity> rolAccountEntityOptional = this.rolAccountRepository.findById(id);
        if (!rolAccountEntityOptional.isPresent()) {
            throw new SchedulePilotException("Rol Account Not Found");
        }
        return rolAccountEntityOptional.get();
    }

}

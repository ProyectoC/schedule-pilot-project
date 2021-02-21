package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.UserAccountDto;
import com.schedulepilot.core.entities.model.UserAccountEntity;
import com.schedulepilot.core.exception.ExceptionCode;
import com.schedulepilot.core.exception.ManageUserException;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.AccountUserRepository;
import com.schedulepilot.core.service.UserAccountService;
import com.schedulepilot.core.util.dto.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;


@Component
public class UserAccountServiceImp implements UserAccountService {

    @Autowired
    private AccountUserRepository accountUserRepository;

    @Override
    @Transactional
    public UserAccountDto getByIdNull(Long id) {
        Optional<UserAccountEntity> entity = accountUserRepository.findById(id);
        return entity.map(UserAccountService::convertEntityToDTO).orElse(null);
    }

    @Override
    @Transactional
    public UserAccountDto getByIdThrow(Long id) throws SchedulePilotException {
        Optional<UserAccountEntity> entity = accountUserRepository.findById(id);
        return entity.map(UserAccountService::convertEntityToDTO).orElseThrow(() -> new SchedulePilotException("User Account Not Found"));
    }

    @Override
    @Transactional
    public UserAccountEntity getByIdOrException(Long id) throws SchedulePilotException {
        Optional<UserAccountEntity> entity = accountUserRepository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new SchedulePilotException("User Account Not Found");
    }

    @Override
    @Transactional
    public UserAccountDto getByUsername(String username) {
        Optional<UserAccountEntity> entity = accountUserRepository.findByUsername(username);
        return entity.map(UserAccountService::convertEntityToDTO).orElse(null);
    }

    @Override
    @Transactional
    public UserAccountDto getByUsernameOrException(String username) throws SchedulePilotException {
        Optional<UserAccountEntity> entity = accountUserRepository.findByUsername(username);
        return entity.map(UserAccountService::convertEntityToDTO).orElseThrow(() -> new ManageUserException(ExceptionCode.ERROR_MANAGE_USER_AUTH_FAILED, "User Account Not Found"));
    }

    @Override
    @Transactional
    public UserAccountDto getByUsernameOrNull(String username) {
        Optional<UserAccountEntity> entity = accountUserRepository.findByUsername(username);
        return entity.map(UserAccountService::convertEntityToDTO).orElse(null);
    }

    @Override
    @Transactional
    public UserAccountDto getByIdentification(String identification) {
        Optional<UserAccountEntity> entity = accountUserRepository.findByIdentification(identification);
        return entity.map(UserAccountService::convertEntityToDTO).orElse(null);
    }

    @Override
    @Transactional
    public UserAccountDto getByIdentificationCode(String identificationCode) {
        Optional<UserAccountEntity> entity = accountUserRepository.findByIdentificationCode(identificationCode);
        return entity.map(UserAccountService::convertEntityToDTO).orElse(null);
    }

    @Override
    @Transactional
    public UserAccountDto getByEmailOrEmailBackup(String email) {
        Optional<UserAccountEntity> entity = accountUserRepository.findByEmailOrEmailBackup(email, email);
        return entity.map(UserAccountService::convertEntityToDTO).orElse(null);
    }

    @Override
    @Transactional
    public UserDetails loadUserById(Long id) {
        Optional<UserAccountEntity> entity = accountUserRepository.findById(id);
        if (entity.isPresent()) {
            UserAccountDto user = UserAccountService.convertEntityToDTO(entity.get());
            return UserAccountDto.create(user);
        } else {
            throw new UsernameNotFoundException("User not found with id : " + id);
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<UserAccountEntity> entity = accountUserRepository.findByUsername(userName);
        if (entity.isPresent()) {
            UserAccountDto user = UserAccountService.convertEntityToDTO(entity.get());
            return UserAccountDto.create(user);
        } else {
            throw new UsernameNotFoundException("User not found with userName : " + userName);
        }
    }

    @Override
    @Transactional
    public UserAccountDto save(UserAccountDto userAccountDto) {
        userAccountDto.setBlock(false);
        userAccountDto.setFailedAttempts(0);
        userAccountDto.setIsActive(false);
        return UserAccountService.convertEntityToDTO(this.accountUserRepository.saveAndFlush(UserAccountService.convertDTOToEntity(userAccountDto)));
    }
    
    @Override
    @Transactional
    public UserAccountDto update(UserAccountDto userAccountDto) {
        return UserAccountService.convertEntityToDTO(this.accountUserRepository.saveAndFlush(UserAccountService.convertDTOToEntity(userAccountDto)));
    }

    @Override
    @Transactional
    public Validator validationBeforeSave(UserAccountDto userAccountDto) {
        Validator validator = new Validator();

        Optional<UserAccountEntity> optional = this.accountUserRepository.findByUsername(userAccountDto.getUsername());
        if (optional.isPresent()) {
            validator.setValid(false);
            validator.addError("username not valid, try to use another one.");
            return validator;
        }

        optional = this.accountUserRepository.findByIdentification(userAccountDto.getIdentification());
        if (optional.isPresent()) {
            validator.setValid(false);
            validator.addError("identification not valid, the user is already register.");
            return validator;
        }

        optional = this.accountUserRepository.findByIdentificationCode(userAccountDto.getIdentificationCode());
        if (optional.isPresent()) {
            validator.setValid(false);
            validator.addError("identification code not valid, the user is already register.");
            return validator;
        }

        optional = this.accountUserRepository.findByEmailOrEmailBackup(userAccountDto.getEmail(), userAccountDto.getEmail());
        if (optional.isPresent()) {
            validator.setValid(false);
            validator.addError("email not valid, this email is already register.");
            return validator;
        }

        optional = this.accountUserRepository.findByEmailOrEmailBackup(userAccountDto.getEmailBackup(), userAccountDto.getEmailBackup());
        if (optional.isPresent()) {
            validator.setValid(false);
            validator.addError("email backup not valid, this email is already register.");
            return validator;
        }
        validator.setValid(true);
        return validator;
    }

    @Override
    public boolean isAdminUser(Long userAccountId) {
        return this.accountUserRepository.isAdminUser(userAccountId);
    }

    @Override
    public boolean isCommonUser(Long userAccountId) {
        return this.accountUserRepository.isCommonUser(userAccountId);
    }
}

package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.dto.model.TokenDto;
import com.schedulepilot.core.entities.model.TokenEntity;
import com.schedulepilot.core.exception.SchedulePilotException;
import com.schedulepilot.core.repository.TokenRepository;
import com.schedulepilot.core.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class TokenServiceImp implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    @Transactional
    public TokenDto getByIdNull(Long id) {
        Optional<TokenEntity> entity = this.tokenRepository.findById(id);
        return entity.map(TokenService::convertEntityToDTO).orElse(null);
    }

    @Override
    @Transactional
    public TokenDto getByIdAndActivateUserThrow(Long id) throws SchedulePilotException {
        Optional<TokenEntity> entity = this.tokenRepository.findByIdAndActivateUser(id);
        return entity.map(TokenService::convertEntityToDTO).orElseThrow(() -> new SchedulePilotException("Token Activate User Not Found"));
    }

    @Override
    @Transactional
    public TokenDto save(TokenDto tokenDto) {
        TokenEntity tokenEntity = TokenService.convertDTOToEntity(tokenDto);
        return TokenService.convertEntityToDTO(this.tokenRepository.save(tokenEntity));
    }

    @Override
    @Transactional
    public TokenDto update(TokenDto tokenDto) {
        TokenEntity tokenEntity = TokenService.convertDTOToEntity(tokenDto);
        return TokenService.convertEntityToDTO(this.tokenRepository.save(tokenEntity));
    }
}

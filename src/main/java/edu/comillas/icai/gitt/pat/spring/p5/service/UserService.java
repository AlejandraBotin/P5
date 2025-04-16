package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * TODO#6
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public Token login(String email, String password) {
        Optional<AppUser> userOpt = appUserRepository.findByEmail(email);
        if (userOpt.isEmpty()) return null;

        AppUser appUser = userOpt.get();
        if (!Hashing.match(password, appUser.getPassword()))
            return null;

        Optional<Token> existingToken = tokenRepository.findByAppUser(appUser);
        if (existingToken.isPresent()) return existingToken.get();

        Token token = new Token();
        token.setAppUser(appUser);
        return tokenRepository.save(token);

    }

    public AppUser authentication(String tokenId) {

        Optional<Token> tokenOpt = tokenRepository.findById(tokenId);
        return tokenOpt.map(Token::getAppUser).orElse(null);
    }

    public ProfileResponse profile(AppUser appUser) {

        return new ProfileResponse(appUser.getEmail(), appUser.getName(), appUser.getRole());
    }
    public ProfileResponse profile(AppUser appUser, ProfileRequest profile) {

        if (StringUtils.hasText(profile.getName())) {
            appUser.setName(profile.getName());
        }
        appUserRepository.save(appUser);
        return profile(appUser);
    }
    public ProfileResponse profile(RegisterRequest register) {

        AppUser appUser = new AppUser();
        appUser.setEmail(register.getEmail());
        appUser.setPassword(Hashing.hash(register.getPassword()));
        appUser.setName(register.getName());
        appUser.setRole(register.getRole());

        appUser = appUserRepository.save(appUser);
        return profile(appUser);
    }

    public void logout(String tokenId) {
        tokenRepository.deleteById(tokenId);
    }

    public void delete(AppUser appUser) {
        appUserRepository.delete(appUser);
    }

}

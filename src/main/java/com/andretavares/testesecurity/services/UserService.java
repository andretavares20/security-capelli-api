package com.andretavares.testesecurity.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.andretavares.testesecurity.dto.FBUser;
import com.andretavares.testesecurity.dto.FBUserInfo;
import com.andretavares.testesecurity.dto.SingleUserDto;
import com.andretavares.testesecurity.dto.UserDto;
import com.andretavares.testesecurity.dto.UserGoogleProviderDto;
import com.andretavares.testesecurity.entities.Endereco;
import com.andretavares.testesecurity.entities.User;
import com.andretavares.testesecurity.enums.UserRole;
import com.andretavares.testesecurity.exceptions.BadRequestException;
import com.andretavares.testesecurity.exceptions.ResourceNotFoundException;
import com.andretavares.testesecurity.repositories.UserRepository;
import com.andretavares.testesecurity.source.RegistrationSource;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Value("${facebook.client-id}")
    private String FACEBOOK_CLIENT_ID;

    @Value("${facebook.secret-key}")
    private String FACEBOOK_SECRET_KEY;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario com id " + id + " não encontrado"));
    }

    public User create(UserDto userDto) {

        if (!StringUtils.hasText(userDto.getEmail())) {
            throw new BadRequestException("Por favor insira um email");
        }

        List<User> userList = userRepository.findAllByEmail(userDto.getEmail());

        for (User user : userList) {
            if (user.getEmail().equals(userDto.getEmail())) {
                throw new BadRequestException(
                        "Já existe um usuário CUSTOM com email " + userDto.getEmail() + " criado");
            }
        }

        User user = new User(userDto.getId(), userDto.getEmail(),
                new BCryptPasswordEncoder().encode(userDto.getPassword()), userDto.getName(), userDto.getRole(),
                userDto.getCelular(), userDto.getIsActive(), userDto.getSource(), userDto.getDataNascimento(),
                userDto.getGenero());
        user.setRole(UserRole.USER);

        User userSaved = userRepository.save(user);
        List<Endereco> listEndereco = new ArrayList<>();

        List<Endereco> enderecos = userDto.getEnderecos();
        for (Endereco endereco : enderecos) {
            endereco.setUser(userSaved);
            listEndereco.add(endereco);
        }

        userSaved.setEnderecos(listEndereco);

        return userRepository.save(userSaved);
    }

    public User edit(UserDto userDto) {

        if (userDto.getId() == null) {
            throw new BadRequestException("Id do usuário não foi informado");
        }

        User existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        BeanUtils.copyProperties(userDto, existingUser, getNullPropertyNames(userDto));

        return userRepository.save(existingUser);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public SingleUserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        SingleUserDto singleUserDto = new SingleUserDto();
        optionalUser.ifPresent(user -> singleUserDto.setUserDto(user.getUserDto()));
        return singleUserDto;
    }

    public UserDto postUserGoogle(UserGoogleProviderDto userDto) {
        List<User> optionalUser = userRepository.findAllByEmail(userDto.getEmail());

        Optional<User> userComSourceGoogle = optionalUser.stream()
                .filter(user -> user.getSource() == RegistrationSource.GOOGLE)
                .findFirst();

        String fakePassword = "fakePassword";

        if (!userComSourceGoogle.isPresent()) {
            String senhaCriptografada = new BCryptPasswordEncoder().encode(fakePassword);

            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            user.setRole(UserRole.USER);
            user.setSource(RegistrationSource.GOOGLE);
            user.setIsActive(true);
            user.setPassword(senhaCriptografada);
            User createdUser = userRepository.save(user);
            UserDto createdUserDto = new UserDto();
            createdUserDto.setId(createdUser.getId());
            createdUserDto.setEmail(createdUser.getEmail());
            createdUserDto.setRole(UserRole.USER);
            createdUserDto.setPassword(senhaCriptografada);
            return createdUserDto;
        } else {

            UserDto createdUserDto = new UserDto();
            createdUserDto.setId(userComSourceGoogle.get().getId());
            createdUserDto.setEmail(userComSourceGoogle.get().getEmail());
            createdUserDto.setRole(UserRole.USER);
            createdUserDto.setPassword(userComSourceGoogle.get().getPassword());
            return createdUserDto;

        }

    }

    public User postUserFacebook(String credential) {

        try {
            credential = credential.replaceAll("\"", "");
        } catch (Exception e) {
            System.out.println("ERRO - credential = credential.replaceAll(\"\\\"\", \"\");");
        }
        RestTemplate restTemplate = new RestTemplate();

        // Fazer a chamada para verificar o token do Facebook
        String debugTokenUrl = "https://graph.facebook.com/debug_token?input_token=" + credential +
                "&access_token=" + FACEBOOK_CLIENT_ID + "|" + FACEBOOK_SECRET_KEY;

        FBUser userOBJK = restTemplate.getForObject(debugTokenUrl, FBUser.class);

        if (userOBJK != null && !userOBJK.getData().isValid()) {
            return null;
        }

        // Fazer a chamada para obter informações do usuário do Facebook
        String meUrl = "https://graph.facebook.com/me?fields=first_name,last_name,email,id&access_token="
                + credential;
        FBUserInfo userContentObj = restTemplate.getForObject(meUrl, FBUserInfo.class);

        List<User> listUser = userRepository.findAllByEmail(userContentObj.getEmail());

        User userExist = listUser.stream()
                .filter(u -> u.getSource().equals(RegistrationSource.FACEBOOK))
                .findFirst()
                .orElse(null);

        if (userExist == null) {

            User user = facebookUserInfoToUser(userContentObj);
            String fakePassword = "fakePassword";
            user.setPassword(new BCryptPasswordEncoder().encode(fakePassword));
            userRepository.save(user);
            return user;

        } else {
            return userExist;
        }

    }

    private User facebookUserInfoToUser(FBUserInfo fbUserInfo) {
        User user = new User();
        user.setEmail(fbUserInfo.getEmail());
        user.setName(fbUserInfo.getFirstName() + ' ' + fbUserInfo.getLastName());
        user.setRole(UserRole.USER);
        user.setSource(RegistrationSource.FACEBOOK);
        return user;
    }

}

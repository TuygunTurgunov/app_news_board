package uz.pdp.uz.app_news_board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.uz.app_news_board.entity.User;
import uz.pdp.uz.app_news_board.entity.enums.RoleName;
import uz.pdp.uz.app_news_board.payload.ApiResponse;
import uz.pdp.uz.app_news_board.payload.LoginDto;
import uz.pdp.uz.app_news_board.payload.RegisterDto;
import uz.pdp.uz.app_news_board.repository.RoleRepository;
import uz.pdp.uz.app_news_board.repository.UserRepository;
import uz.pdp.uz.app_news_board.security.JwtProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;


    public ApiResponse registerUser(RegisterDto registerDto) {

    //1
            boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
            if (existsByEmail)
                return new ApiResponse("Such kind of email already exists", false);

            boolean existsByLogin = userRepository.existsByLogin(registerDto.getLogin());
            if (existsByLogin)
                 return new ApiResponse("Such kind of login already exists", false);


        //3
        User user =new User();
        user.setEmail(registerDto.getEmail());
        user.setLogin(registerDto.getLogin());
        user.setAge(registerDto.getAge());
        user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_USER)));
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));


        userRepository.save(user);

        return new ApiResponse("Muvafaqiyatli registratsiya qilindingiz",true);
    }

//    public User getOneUser(UUID userId){
//        Optional<User> optionalUser = userRepository.findById(userId);
//        return optionalUser.orElse(null);
//    }
//
//    public List<User> getAllUsers(){
//        return userRepository.findAll();
//}




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username + "topilmadi"));
    }



    public ApiResponse login(LoginDto loginDto) {

        try {

            //loadUserByUsername() => shu method ni avtomat o'zi qidiradi
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getLogin(),
                    loginDto.getPassword()));

            //4ta boolean li fieldlarini ham tekshiradi user ni agar 1 tasi false bosa catch ga tushadi
            // Set toifasidagi role larni yasab olish uchun Authentication interface dan foydalandik
            //User entity dan role larini ovilduk shu orqali
            User user = (User) authentication.getPrincipal();

            String token = jwtProvider.generateToken(user.getEmail(), user.getRoles());
            return new ApiResponse("Token", true, token);


        } catch (BadCredentialsException badCredentialsException) {

            return new ApiResponse("parol yoki login hato", false);
        }
}


    public UserDetails loadUserByLogin(String login) {
            if (userRepository.existsByLogin(login))
            return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login + " topilmadi"));

        return null;

    }
}

package dashboard.service;

import dashboard.dto.LoginDTO;
import dashboard.dto.UserDTO;
import dashboard.entity.User;
import dashboard.config.SecurityConfig;
import dashboard.repo.UserRepository;
import dashboard.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public String addUser(UserDTO userDTO) {

        User user =new User(

                userDTO.getUserid(),
                userDTO.getEmail(),

                this.passwordEncoder.encode(userDTO.getPassword())
        );

        userRepository.save(user);

        return user.getEmail();
    }

    @Override
    public LoginResponse loginUser(LoginDTO loginDTO) {
        String msg = "";
        User user1 = userRepository.findByEmail(loginDTO.getEmail());
        if(user1!= null){
            String password = loginDTO.getPassword();
            String encodedPassword = user1.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password,encodedPassword);
            if(isPwdRight){
                Optional<User> user = userRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if(user.isPresent()){
                    return new LoginResponse("Login Sucess", true);
                }
                else{
                    return new LoginResponse("Login Failed", false);
                }
            }
            else{
                return  new LoginResponse("Password Not Match",false);
            }
        }
        else {
            return new LoginResponse("Email not exits",false);
        }


    }


}

package dashboard.service;

import dashboard.dto.LoginDTO;
import dashboard.dto.UserDTO;
import dashboard.entity.User;
import dashboard.response.LoginResponse;

public interface UserService {

    String addUser(UserDTO userDTO);

    LoginResponse loginUser(LoginDTO loginDTO);
}

package dashboard.rest;

import dashboard.response.LoginResponse;
import dashboard.dto.LoginDTO;
import dashboard.dto.UserDTO;
import dashboard.entity.User;
import dashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String saveUser(@RequestBody UserDTO userDTO) {
       String id= userService.addUser(userDTO);
       return id;
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){

        LoginResponse loginResponse = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

}

package hello.controller;

import hello.dbStructure.User;
import hello.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    class UserResponse{
        String opResult;

        public String getOpResult() {
            return opResult;
        }

        public void setOpResult(String opResult) {
            this.opResult = opResult;
        }
    }

    @Autowired    private UserRepository userRepository;

    @GetMapping("/getUser")    @ResponseBody
    public Iterable<User> getUser(){
        return userRepository.findAll();
    }

    @PostMapping("/newUser")    @ResponseBody
    public UserResponse newUser(@RequestBody User payload){

        userRepository.save(payload);// userRepository.save(us);
        UserResponse up = new UserResponse();
        up.setOpResult("ok");
        return up ;
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public UserResponse updateUser(@RequestBody User payload){

        User oldUser = userRepository.getUserFromId(payload.getId());
        if (payload.getEmail() != null) {
            oldUser.setEmail(payload.getEmail());
        }
        if (payload.getPassword() != null) {
            oldUser.setPassword(payload.getPassword());
        }
        if (payload.getUsername() != null) {
            oldUser.setUsername(payload.getUsername());
        }
        userRepository.save(oldUser);// userRepository.save(us);
        UserResponse up = new UserResponse();
        up.setOpResult("ok");
        return up ;
    }

    @PostMapping("/delUser")    @ResponseBody
    public UserResponse delUser(@RequestBody HashMap<String,String> payload){

        User is = userRepository.getUserFromId(Integer.valueOf(payload.get("id")));
        userRepository.delete(is);

        UserResponse up = new UserResponse();
        up.setOpResult("ok");
        return up ;
    }
}

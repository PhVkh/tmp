package fi.lipp.greatheart.directory.web;
import fi.lipp.greatheart.directory.dto.UserDto;
import fi.lipp.greatheart.directory.service.UserService;
import fi.lipp.greatheart.directory.service.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> addUser(@RequestBody UserDto user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

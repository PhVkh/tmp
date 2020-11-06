package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserDto> findAll(Pageable pageable);
    void save(UserDto userDto);
}

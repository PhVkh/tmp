package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory._old.service_old.dto.EmployeeDto;
import fi.lipp.greatheart.directory._old.service_old.exceptions.EntityNotFoundException;
import fi.lipp.greatheart.directory.domain.UserEntity;
import fi.lipp.greatheart.directory.dto.UserDto;
import fi.lipp.greatheart.directory.repository.UserRepository;
import fi.lipp.greatheart.directory.service.UserService;
import fi.lipp.greatheart.directory.service.mappers.CycleAvoidingMappingContext;
import fi.lipp.greatheart.directory.service.mappers.UserMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CycleAvoidingMappingContext context;

    @Override
    public List<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .stream().map(x -> userMapper.convert(x, context))
                .collect(Collectors.toList());
    }

    public void save(UserDto employee) {
        UserEntity userEntity = userMapper.convert(employee, context);
        userRepository.save(userEntity);

        List<UserEntity> users = userRepository.findAll();
        Hibernate.initialize(users);
        System.out.println("hello");
    }

}

package fi.lipp.greatheart.directory;

import fi.lipp.greatheart.directory.domain.EmailEntity;
import fi.lipp.greatheart.directory.domain.PhoneEntity;
import fi.lipp.greatheart.directory.domain.Role;
import fi.lipp.greatheart.directory.domain.UserEntity;
import fi.lipp.greatheart.directory.dto.EmailDto;
import fi.lipp.greatheart.directory.dto.PhoneDto;
import fi.lipp.greatheart.directory.dto.UserDto;
import fi.lipp.greatheart.directory.repository.UserRepository;
import fi.lipp.greatheart.directory.service.mappers.CycleAvoidingMappingContext;
import fi.lipp.greatheart.directory.service.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class MapperTest {

    @Autowired
    UserRepository repository;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

    @Test
    public void test() {
        UserEntity userEntity = new UserEntity();
        EmailEntity emailEntity = new EmailEntity();
        PhoneEntity phoneEntity = new PhoneEntity();

        userEntity.setFirstName("Lilia");
        userEntity.setLastName("Isk");
        userEntity.setEmails(Collections.singletonList(emailEntity));
        userEntity.setPhones(Collections.singletonList(phoneEntity));
        userEntity.setCatalog_roles(Collections.singleton(Role.MANAGER));

        repository.save(userEntity);



        emailEntity.setId(1L);
        emailEntity.setEmail("blah@blah.com");
        emailEntity.setOwner(userEntity);

        phoneEntity.setId(1L);
        phoneEntity.setPhone("888888888888");
        phoneEntity.setOwner(userEntity);


        EmailDto emailDto = userMapper.convert(emailEntity, context);
        PhoneDto phoneDto = userMapper.convert(phoneEntity, context);
        UserDto userDto = userMapper.convert(userEntity, context);

        UserEntity userEntity1 = userMapper.convert(userDto, context);
        PhoneEntity phoneEntity1 = userMapper.convert(phoneDto, context);
        EmailEntity emailEntity1 = userMapper.convert(emailDto, context);
    }

}

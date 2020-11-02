package fi.lipp.greatheart.directory;

import fi.lipp.greatheart.directory.domain.EmailEntity;
import fi.lipp.greatheart.directory.domain.PhoneEntity;
import fi.lipp.greatheart.directory.domain.UserEntity;
import fi.lipp.greatheart.directory.dto.EmailDto;
import fi.lipp.greatheart.directory.dto.PhoneDto;
import fi.lipp.greatheart.directory.dto.UserDto;
import fi.lipp.greatheart.directory.service.mappers.CycleAvoidingMappingContext;
import fi.lipp.greatheart.directory.service.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;


public class MapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();

    @Test
    public void test() {
        UserEntity userEntity = new UserEntity();
        EmailEntity emailEntity = new EmailEntity();
        PhoneEntity phoneEntity = new PhoneEntity();

        userEntity.setId(1L);
        userEntity.setFirstName("Lilia");
        userEntity.setLastName("Isk");
        userEntity.setPassword("111111");
        userEntity.setEmails(Collections.singletonList(emailEntity));
        userEntity.setPhones(Collections.singletonList(phoneEntity));

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

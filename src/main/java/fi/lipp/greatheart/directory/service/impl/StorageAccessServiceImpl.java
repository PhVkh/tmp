package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import fi.lipp.greatheart.directory.dto.StorageAccess;
import fi.lipp.greatheart.directory.repository.StorageAccessRepository;
import fi.lipp.greatheart.directory.service.services.EntityTypeService;
import fi.lipp.greatheart.directory.service.services.StorageAccessService;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StorageAccessServiceImpl implements StorageAccessService {

    @Autowired
    StorageAccessRepository storageAccessRepository;

    @Autowired
    EntityTypeService entityTypeService;

    @Override
    public Response<List<Integer>> getAccessEntitiesByLogin(String login) {
        if (!checkLoginExistence(login))
            return Response.BAD(String.format("У пользователя с логином %s нет прав на просмотр и редактирование сущностей", login));

        Optional<StorageAccess> storageAccess = storageAccessRepository.findByLogin(login);

        if (storageAccess.isPresent())
            return Response.EXECUTE(() -> storageAccess.get().getEntityListAll());

        return Response.BAD(String.format("У пользователя с логином %s нет прав на просмотр и редактирование сущностей", login));

    }

    @Override
    public Response<StorageAccess> addAccessEntitiesToLogin(String login, List<Integer> entityTypeIds) {
        if (!checkEntityTypeExistence(entityTypeIds))
            return Response.BAD("Неверный список типов сущностей.");

        Optional<StorageAccess> storageAccessOptional = storageAccessRepository.findByLogin(login);

        if (storageAccessOptional.isPresent()) {
            StorageAccess storageAccess = storageAccessOptional.get();
            storageAccess.getEntityListAll().addAll(entityTypeIds);
            storageAccess.setEntityListAll(storageAccess.getEntityListAll().stream()
                    .distinct()
                    .collect(Collectors.toList()));
            return Response.EXECUTE(() -> storageAccessRepository.save(storageAccess));
        }

        //Если у такого логина нет прав - добавим
        StorageAccess storageAccess = new StorageAccess();
        storageAccess.setLogin(login);
        storageAccess.setEntityListAll(entityTypeIds);

        return Response.EXECUTE(() -> storageAccessRepository.save(storageAccess));

    }

    @Override
    public Response<StorageAccess> removeAccessEntitiesToLogin(String login, List<Integer> entityTypeIds) {

        if (!checkLoginExistence(login))
            return Response.BAD(String.format("У пользователя с логином %s нет прав на просмотр и редактирование сущностей.Удалять нечего", login));

        if (!checkEntityTypeExistence(entityTypeIds))
            return Response.BAD("Неверный список типов сущностей.");

        Optional<StorageAccess> storageAccessOptional = storageAccessRepository.findByLogin(login);

        if (storageAccessOptional.isPresent()) {
            StorageAccess storageAccess = storageAccessOptional.get();
            storageAccess.getEntityListAll().removeAll(entityTypeIds);

            return Response.EXECUTE(() -> storageAccessRepository.save(storageAccess));
        }
        return Response.BAD(String.format("У пользователя с логином %s нет прав на просмотр и редактирование сущностей", login));
    }

    public Boolean checkEntityTypeExistence(List<Integer> idsToCheck) {
        List<Integer> entityTypeIds = entityTypeService.findMainEntities()
                .stream().map(EntityTypeDto::getId).
                        mapToInt(Long::intValue).boxed().collect(Collectors.toList());

        return entityTypeIds.containsAll(idsToCheck);
    }


    public Boolean checkLoginExistence(String loginToCheck) {
        List<String> logins = storageAccessRepository.findAll()
                .stream().map(StorageAccess::getLogin).collect(Collectors.toList());

        return logins.contains(loginToCheck);
    }


}

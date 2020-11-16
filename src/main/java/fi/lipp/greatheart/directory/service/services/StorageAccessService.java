package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.StorageAccess;
import fi.lipp.greatheart.directory.web.Response;

import java.util.List;

public interface StorageAccessService {
    Response<List<Integer>> getAccessEntitiesByLogin(String login);

    Response<StorageAccess> addAccessEntitiesToLogin(String login, List<Integer> entityTypeIds);

    Response<StorageAccess> removeAccessEntitiesToLogin(String login, List<Integer> entityTypeIds);

}

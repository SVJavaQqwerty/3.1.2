package ru.svlid.pp_312.repos;

import org.springframework.data.repository.CrudRepository;
import ru.svlid.pp_312.models.User;

public interface UserRepo extends CrudRepository<User, Long> {
}

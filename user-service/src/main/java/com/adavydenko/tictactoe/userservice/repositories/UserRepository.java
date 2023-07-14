package com.adavydenko.tictactoe.userservice.repositories;

import com.adavydenko.tictactoe.userservice.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}

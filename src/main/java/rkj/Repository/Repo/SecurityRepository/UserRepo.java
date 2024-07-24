package rkj.Repository.Repo.SecurityRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import rkj.objLib.objLib.SecurityObject.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByUserNameOrEmail(String userName, String email);
}

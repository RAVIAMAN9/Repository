package rkj.Repository.Repo.SecurityRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import rkj.objLib.objLib.SecurityObject.User;

public interface UserRepo extends JpaRepository<User,Integer> {
}

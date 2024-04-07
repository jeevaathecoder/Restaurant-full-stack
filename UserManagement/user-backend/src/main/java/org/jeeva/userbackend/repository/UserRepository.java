package org.jeeva.userbackend.repository;

import org.jeeva.userbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

package com.svalero.music.rights.repository;

import com.svalero.music.rights.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}

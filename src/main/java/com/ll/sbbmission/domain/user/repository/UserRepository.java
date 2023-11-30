package com.ll.sbbmission.domain.user.repository;

import com.ll.sbbmission.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
}

package com.ll.sbbmission.domain.answer.repository;

import com.ll.sbbmission.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}

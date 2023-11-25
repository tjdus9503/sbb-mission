package com.ll.sbbmission.domain.answer.service;

import com.ll.sbbmission.domain.answer.entity.Answer;
import com.ll.sbbmission.domain.answer.repository.AnswerRepository;
import com.ll.sbbmission.domain.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    public void create(String content, Question question) {
        Answer answer = new Answer();

        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);

        answerRepository.save(answer);
    }
}

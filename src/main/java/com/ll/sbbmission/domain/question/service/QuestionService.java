package com.ll.sbbmission.domain.question.service;

import com.ll.sbbmission.domain.question.entity.Question;
import com.ll.sbbmission.domain.question.repository.QuestionRepository;
import com.ll.sbbmission.global.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
    }

    public Question getQuestion(int id) {

        Optional<Question> optQuestion = questionRepository.findById(id);

        if (optQuestion.isPresent()) {
            return optQuestion.get();
        }
        else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void createQuestion(String subject, String content) {
        Question question = new Question();

        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());

        questionRepository.save(question);
    }
}

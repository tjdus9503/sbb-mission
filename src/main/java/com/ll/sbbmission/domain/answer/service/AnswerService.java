package com.ll.sbbmission.domain.answer.service;

import com.ll.sbbmission.domain.answer.entity.Answer;
import com.ll.sbbmission.domain.answer.repository.AnswerRepository;
import com.ll.sbbmission.domain.question.entity.Question;
import com.ll.sbbmission.domain.user.entity.SiteUser;
import com.ll.sbbmission.global.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(String content, Question question, SiteUser author) {
        Answer answer = new Answer();

        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);

        answerRepository.save(answer);

        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);

        if (answer.isPresent()) {
            return answer.get();
        }
        else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());

        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);

        answerRepository.save(answer);
    }
}

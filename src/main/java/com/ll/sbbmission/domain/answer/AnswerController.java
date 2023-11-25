package com.ll.sbbmission.domain.answer;

import com.ll.sbbmission.domain.question.Question;
import com.ll.sbbmission.domain.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Integer id, @RequestParam String content) {
        Question question = questionService.getQuestion(id);

        answerService.create(content, question);

        return "redirect:/question/detail/%d".formatted(id);
    }
}

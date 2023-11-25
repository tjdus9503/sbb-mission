package com.ll.sbbmission.domain.question.controller;

import com.ll.sbbmission.domain.question.service.QuestionService;
import com.ll.sbbmission.domain.question.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);

        return "question/question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable int id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question/question_detail";
    }

    @GetMapping("/create")
    public String create() {
        return "question/question_form";
    }
}

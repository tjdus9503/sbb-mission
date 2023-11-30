package com.ll.sbbmission.domain.question.controller;

import com.ll.sbbmission.domain.answer.controller.AnswerForm;
import com.ll.sbbmission.domain.question.entity.Question;
import com.ll.sbbmission.domain.question.service.QuestionService;
import com.ll.sbbmission.domain.user.entity.SiteUser;
import com.ll.sbbmission.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue="0") int page) {

        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);

        return "question/question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable int id, AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question/question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm) {
        return "question/question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }
        SiteUser siteUser = userService.getUser(principal.getName());

        questionService.createQuestion(questionForm.getSubject(), questionForm.getContent(), siteUser);

        return "redirect:/question/list";
    }
}

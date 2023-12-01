package com.ll.sbbmission.domain.question.controller;

import com.ll.sbbmission.domain.answer.controller.AnswerForm;
import com.ll.sbbmission.domain.question.entity.Question;
import com.ll.sbbmission.domain.question.service.QuestionService;
import com.ll.sbbmission.domain.user.entity.SiteUser;
import com.ll.sbbmission.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue = "") String kw) {

        Page<Question> paging = questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());

        return "/question/question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@Valid QuestionForm questionForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "/question/question_form";
        }

        Question question = this.questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        this.questionService.modifyQuestion(question, questionForm.getSubject(), questionForm.getContent());

        return "redirect:/question/detail/%s".formatted(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.questionService.delete(question);

        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String vote(Principal principal, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        SiteUser siteUser = userService.getUser(principal.getName());

        questionService.vote(question, siteUser);

        return "redirect:/question/detail/%s".formatted(id);
    }
}

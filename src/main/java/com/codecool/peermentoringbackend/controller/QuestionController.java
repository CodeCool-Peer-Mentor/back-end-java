package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import com.codecool.peermentoringbackend.entity.UserEntity;
import com.codecool.peermentoringbackend.model.*;
import com.codecool.peermentoringbackend.repository.UserRepository;
import com.codecool.peermentoringbackend.security.JwtTokenServices;
import com.codecool.peermentoringbackend.service.QuestionService;
import com.codecool.peermentoringbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private JwtTokenServices jwtTokenServices;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<QuestionEntity> getAllQuestion() {
        return questionService.getAll();
    }


    @PostMapping("")
    public void addQuestion(HttpServletRequest request, HttpServletResponse response, @RequestBody QuestionModel questionModel) throws IOException {

        String username = jwtTokenServices.getUsernameFromToken(request);

        boolean success = questionService.addNewQuestion(questionModel, username);
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("user id can't be null");
        }

    }


    @GetMapping("/{questionId}")
    public QAndAsModel getQuestionByIdAndAnswers(@PathVariable Long questionId) {
        return questionService.getQuestionByIdAndAnswers(questionId);
    }


    @PostMapping("/edit/{questionId}")
    public void editQuestion(HttpServletRequest request, HttpServletResponse response, @RequestBody QModelWithId questionModel, @PathVariable String questionId) throws IOException {
        String usernameFromToken = jwtTokenServices.getUsernameFromToken(request);
        System.out.println(questionId);
        UserEntity userEntity = userRepository.findDistinctByUsername(usernameFromToken);
        boolean success = questionService.editQuestion(questionModel, userEntity, Long.parseLong(questionId));
        if (success) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
            response.getWriter().println("users can only edit their own questions");
        }
    }
}

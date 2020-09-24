package com.codecool.peermentoringbackend.repository;

import com.codecool.peermentoringbackend.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    List<QuestionEntity> findAll();

    QuestionEntity findQuestionEntityById(Long id);
}
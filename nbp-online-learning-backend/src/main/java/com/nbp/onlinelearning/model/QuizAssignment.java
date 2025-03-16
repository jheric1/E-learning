package com.nbp.onlinelearning.model;

import com.nbp.onlinelearning.constants.QuizAssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizAssignment {

    private long id;
    private CreateQuizDTO quiz;
    private long studentId;
    private QuizAssignmentStatus quizAssignmentStatus;
    private double totalPoints;
    private List<Answer> answerList = new ArrayList<>();
}

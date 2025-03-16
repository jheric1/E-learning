package com.nbp.onlinelearning.model;

import com.nbp.onlinelearning.constants.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private long id;
    private String text;
    private long quizId;
    private double maxPoints;
    private List<AnswerOption> answerOptions = new ArrayList<>();
}

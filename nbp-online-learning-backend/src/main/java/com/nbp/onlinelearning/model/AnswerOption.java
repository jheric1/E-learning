package com.nbp.onlinelearning.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerOption {
    private long id;
    private long questionId;
    private String answerData;

    @JsonProperty
    private boolean isCorrect;
}

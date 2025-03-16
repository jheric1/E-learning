package com.nbp.onlinelearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Answer {

    private long id;
    private String answerData;
    private long questionId;
}

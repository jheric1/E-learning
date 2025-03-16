package com.nbp.onlinelearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateQuizDTO {
    private Quiz quiz;
    private List<Question> questions = new ArrayList<>();
}

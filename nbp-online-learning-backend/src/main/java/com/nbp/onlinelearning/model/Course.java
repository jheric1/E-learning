package com.nbp.onlinelearning.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Course {
    private long id;
    private String title;
    private String description;
    private User instructor;
    private Date startDate;
    private Date endDate;
    private List<Quiz> quizList = new ArrayList<>();
    private List<Lesson> lessonList = new ArrayList<>();
    private List<Homework> homeworkList = new ArrayList<>();
    @JsonCreator
    public Course(@JsonProperty("id") long id) {
        this.id = id;
    }
}

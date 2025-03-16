package com.nbp.onlinelearning.model;

import com.nbp.onlinelearning.constants.LessonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private long  id;
    private String lessonData;
    private String tittle;
    private String url;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    private LessonType lessonType;
    private long courseId;

}

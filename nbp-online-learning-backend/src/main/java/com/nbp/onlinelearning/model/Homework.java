package com.nbp.onlinelearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Homework {
    private long id;
    private long courseId;
    private long maxPoints;
    private Date deadlineDate;
    private Date startDate;
    private String title;
    private String description;
}

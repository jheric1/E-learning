package com.nbp.onlinelearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateCourseDTO {
    private long id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private long instructorId;
}

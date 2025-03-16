package com.nbp.onlinelearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private long id;
    private long value;
    private String description;
    private CourseStudent courseStudent;
}

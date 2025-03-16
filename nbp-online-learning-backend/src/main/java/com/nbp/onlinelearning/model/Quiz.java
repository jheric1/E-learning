package com.nbp.onlinelearning.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    private long id;
    private long courseId;
    private Date startDate;
    private Date deadlineDate;
    private String title;
    private String description;
    private boolean isActive;
    private int maxPoints;

}

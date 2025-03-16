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
public class HomeworkAssignmentDto {

    private String firstName;
    private String lastName;
    private String email;
    private Date submitDate;
    private String homeworkDataUrl;
    private double points;
    private long homeworkAssignmentId;
}

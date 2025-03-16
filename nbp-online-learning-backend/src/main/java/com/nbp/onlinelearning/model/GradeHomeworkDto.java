package com.nbp.onlinelearning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GradeHomeworkDto {
    private long homeworkAssignmentId;
    private double points;
}

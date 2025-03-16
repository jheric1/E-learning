package com.nbp.onlinelearning.model;

import com.nbp.onlinelearning.constants.HomeworkAssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class HomeworkAssignment {
    private long id;
    private long homeworkId;
    private long studentId;
    private HomeworkAssignmentStatus homeworkAssignmentStatus;
    private long points;
    private Date submitDate;
    private String homeworkDataUrl;
}

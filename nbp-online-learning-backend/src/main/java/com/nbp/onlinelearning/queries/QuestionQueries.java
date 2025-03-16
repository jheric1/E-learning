package com.nbp.onlinelearning.queries;

public enum QuestionQueries {

    SELECT_ALL_QUESTIONS_FOR_QUIZ("SELECT * FROM nbp24t2.nbp_question WHERE quiz_id = :quizId"),
    DELETE_QUESTION("DELETE FROM nbp24t2.nbp_question WHERE id = :id"),
    INSERT_QUESTION("INSERT INTO nbp24t2.nbp_question (quiz_id, question_type_id, text, max_points) VALUES (:quiz_id, 3, :text, :max_points)"),
    SELECT_QUESTION_BY_ID("SELECT q.*, t.name FROM nbp24t2.nbp_question q, nbp24t2.nbp_question_type t WHERE q.id = :id and q.question_type_id=t.id"),
    INSERT_ANSWER_OPTION("INSERT INTO nbp24t2.nbp_answer_option (question_id, answer_data, is_correct) VALUES (:questionId, :answerData, :isCorrect)"),
    SELECT_ALL_QUESTION_ANSWER_OPTIONS("SELECT * FROM nbp24t2.nbp_answer_option WHERE question_id = :questionId"),
    SELECT_ALL_QUIZ_ASSIGNMENT_ANSWERS("SELECT * FROM nbp24t2.nbp_answer WHERE quiz_assignment_id = :quizAssignmentId"),
    INSERT_ANSWER("INSERT INTO nbp24t2.nbp_answer (question_id, answer_data, quiz_assignment_id) VALUES (:questionId, :answerData, :quizAssignmentId)");


    private final String query;
    QuestionQueries(String query){
        this.query=query;
    }
    public String getQuery() {
        return this.query;
    }
}

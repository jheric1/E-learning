package com.nbp.onlinelearning.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nbp.onlinelearning.model.*;
import com.nbp.onlinelearning.sevice.QuizService;
import com.nbp.onlinelearning.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> findAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Quiz> findQuizById(@PathVariable long id) {
        Quiz quiz = quizService.getQuizById(id);
        if (quiz != null) {
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-quiz")
    public Quiz createQuiz(@RequestBody CreateQuizDTO quiz) {
        return quizService.createQuiz(quiz);
    }

    @DeleteMapping("/delete-quiz/{id}")
    public boolean deleteQuiz(@PathVariable long id) {
        return quizService.deleteQuiz(id);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/quiz-assignment/{quizId}")
    public QuizAssignment getQuizAssignmenet(@PathVariable long quizId) {
        User student = userService.getUserFromToken();
        return quizService.getQuizAssignment(student.getId(), quizId);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/quiz-assignment/submit-quiz/{quizAssignmentId}")
    public void submitQuizAssignment(@RequestBody List<Answer> answerList, @PathVariable long quizAssignmentId) {
        User student = userService.getUserFromToken();
        quizService.submitQuizAssignment(quizAssignmentId, answerList, student.getId());
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping("/quiz-results/{quizId}")
    public List<QuizResultDto> getQuizResults(@PathVariable long quizId) {
        return quizService.getQuizResults(quizId);
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR')")
    @GetMapping("/generate-pdf/{quizId}")
    public ResponseEntity<ByteArrayResource> generatePdf(@PathVariable long quizId) {

        List<QuizResultDto> quizResults = quizService.getQuizResults(quizId);
        Quiz quiz = quizService.getQuizById(quizId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk(quiz.getTitle(), font);
            Paragraph titleParagraph = new Paragraph(chunk);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            Stream.of("First name", "Last name", "Email", "Total points")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });
            for (QuizResultDto quizResult : quizResults) {
                table.addCell(quizResult.getFirstName());
                table.addCell(quizResult.getLastName());
                table.addCell(quizResult.getEmail());
                table.addCell(String.valueOf(quizResult.getTotalPoints()));
            }
            document.add(table);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=quiz_results.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .body(resource);

    }


}

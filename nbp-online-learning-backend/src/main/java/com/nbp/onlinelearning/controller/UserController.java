package com.nbp.onlinelearning.controller;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nbp.onlinelearning.model.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.nbp.onlinelearning.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/username/{username}")
    public User findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @GetMapping("/email/{email}")
    public User findUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/id/{id}")
    public User findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/update-user")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete-user/{id}")
    public boolean deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/students")
    public List<User> findAllStudents()  {
        return userService.findAllStudents();
    }
    @GetMapping("/instructors")
    public List<User> findAllInstructors()  {
        return userService.findAllInstructors();
    }

    @PostMapping("/reset-password/{id}")
    public String resetPassword(@PathVariable long id,@RequestBody String password){
        userService.resetPassword(id, password);
        return "Successfully updated the password";
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUserService(loginRequest);
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/generate-pdf")
    public ResponseEntity<ByteArrayResource> generatePdf() {

        List<UserActivityDto> userActivity = userService.getUserActivity();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("User activity", font);
            Paragraph titleParagraph = new Paragraph(chunk);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            Stream.of("Action name", "Table name", "Action timestamp", "Database user")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });
            for (UserActivityDto userActivityDto : userActivity) {
                table.addCell(userActivityDto.getActionName());
                table.addCell(userActivityDto.getTableName());
                table.addCell(userActivityDto.getDateTime().toString());
                table.addCell(userActivityDto.getDbUser());
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

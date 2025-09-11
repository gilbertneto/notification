package com.project.notification.controller;

import com.project.notification.business.EmailService;
import com.project.notification.business.dto.TasksDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> eviarEmail(@RequestBody TasksDTO dto){
        emailService.enviaEmail(dto);
        return ResponseEntity.ok().build();
    }

}

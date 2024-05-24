package org.example.bulletinboardproject.domain;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Board {
    @Id
    private Long id;
    private String name;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String password;

}

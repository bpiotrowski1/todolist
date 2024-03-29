package pl.bpiotrowski.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDateTime finishDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Override
    public String toString() {
        return "Description: " + description + (finishDate == null ? "" : ("; Finish date: " + finishDate)) + (priority == null ? "" : ("; Priority:" + priority));
    }
}

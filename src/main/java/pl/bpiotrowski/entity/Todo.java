package pl.bpiotrowski.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Todo {
    private String uuid;
    private String description;
    private LocalDateTime finishDate;
    private String priority;

    public Todo() {
        UUID ui = UUID.randomUUID();
        this.uuid = ui.toString();
    }

    @Override
    public String toString() {
        return "Description: " + description + (finishDate == null ? "" : ("; Finish date: " + finishDate)) + (priority == null ? "" : ("; Priority:" + priority));
    }
}

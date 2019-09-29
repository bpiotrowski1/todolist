package pl.bpiotrowski;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Todo {
    private String description;
    private LocalDateTime finishDate;
    private String priority;

    @Override
    public String toString() {
        return "Description: " + description + (finishDate == null ? "" : ("; Finish date: " + finishDate)) + (priority == null ? "" : ("; Priority:" + priority));
    }
}

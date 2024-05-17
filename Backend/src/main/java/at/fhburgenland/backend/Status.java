package at.fhburgenland.backend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Status {
    private String username;
    private String statusText;
    private LocalDateTime time;
}

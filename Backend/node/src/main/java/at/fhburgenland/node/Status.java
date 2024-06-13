package at.fhburgenland.node;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Status class. It is used in the StatusMessage class to represent a complete status message.
 */
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class Status {
    private String username;

    private String statusText;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time;
}

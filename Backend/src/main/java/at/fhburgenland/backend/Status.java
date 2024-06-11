package at.fhburgenland.backend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor // Füge einen parameterlosen Konstruktor hinzu
public class Status implements Serializable {
    private String username;
    private String statusText;
    private LocalDateTime time;
}

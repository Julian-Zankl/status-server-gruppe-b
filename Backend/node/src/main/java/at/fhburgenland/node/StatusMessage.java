package at.fhburgenland.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class StatusMessage {
    private String nodeId;
    private String operation;
    private Status status;
}

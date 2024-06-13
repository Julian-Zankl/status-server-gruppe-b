package at.fhburgenland.node;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * StatusMessage class.
 * This class is used to represent a status message. It also contains the status of the message.
 */
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class StatusMessage {
    private String nodeId;
    private String operation;
    private Status status;
}

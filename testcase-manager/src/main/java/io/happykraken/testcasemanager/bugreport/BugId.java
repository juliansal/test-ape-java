package io.happykraken.testcasemanager.bugreport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugId implements Serializable {
    private Long bugNumber;
    private Long testCaseNumber;

}

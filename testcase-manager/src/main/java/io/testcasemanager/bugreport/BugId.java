package io.testcasemanager.bugreport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugId implements Serializable {
    private Long id;
    private Long testcaseId;

}

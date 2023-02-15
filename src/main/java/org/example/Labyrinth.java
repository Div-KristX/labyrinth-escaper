package org.example;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Labyrinth {

    private final int startY;
    private final int startX;
    private final int exitY;
    private final int exitX;
    private List<String> labyrinthMap;
}

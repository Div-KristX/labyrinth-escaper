package org.example.validation;

import java.util.List;

public interface LabyrinthValidator {

    boolean labyrinthIsCorrect(List<String> labyrinth);

    boolean startPointIsCorrect(List<String> labyrinth, int startY, int startX);

    boolean exitPointIsCorrect(List<String> labyrinth, int exitY, int exitX);

}

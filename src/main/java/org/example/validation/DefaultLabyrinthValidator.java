package org.example.validation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.IncorrectMeasurementsException;
import org.example.exceptions.IncorrectPositionException;

@RequiredArgsConstructor
public class DefaultLabyrinthValidator implements LabyrinthValidator {

    private final char wall;
    private final char floor;

    @Override
    public boolean labyrinthIsCorrect(List<String> labyrinth) {
        if (linesIncorrect(labyrinth)) {
            throw new IncorrectMeasurementsException("Incorrect lines length");
        } else if (columnsIncorrect(labyrinth)) {
               throw  new IncorrectMeasurementsException("Incorrect lines format");
        } else {
            return true;
        }
    }

    @Override
    public boolean startPointIsCorrect(List<String> labyrinth, int startY, int startX) {
        return checkPoint(labyrinth, startY, startX);
    }

    @Override
    public boolean exitPointIsCorrect(List<String> labyrinth, int exitY, int exitX) {
        return checkPoint(labyrinth, exitY, exitX);
    }

    private boolean checkPoint(List<String> labyrinth, int pointY, int pointX) {
        if (pointX >= labyrinth.size() || pointX < 0) {
            throw new IllegalArgumentException("Incorrect position");
        } else if (pointY >= labyrinth.get(0).length() || pointY < 0) {
            throw new IllegalArgumentException("Incorrect position");
        } else {
            if (labyrinth.get(pointY).charAt(pointX) == floor) {
                return true;
            } else {
                throw new IncorrectPositionException("Is not point to start or exit", pointY,
                    pointX);
            }
        }
    }

    private boolean linesIncorrect(List<String> labyrinth) {
        final int LINE_LENGTH = labyrinth.get(0).length();
        return labyrinth.size() != labyrinth.stream()
            .filter(line -> line.length() == LINE_LENGTH)
            .count();
    }

    private boolean columnsIncorrect(List<String> labyrinth) {
        return labyrinth.size() != labyrinth.stream()
            .filter(line -> {
                char[] chars = line.toCharArray();
                boolean plusOrDash = true;
                for (char c : chars) {
                    if (c != floor && c != wall) {
                        if (c != '\n') {
                            plusOrDash = false;
                            break;
                        }
                    }
                }
                return plusOrDash;
            }).count();
    }
}

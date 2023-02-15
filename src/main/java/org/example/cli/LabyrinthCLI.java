package org.example.cli;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.example.Labyrinth;
import org.example.sourceprovider.LabyrinthProvider;
import org.example.validation.LabyrinthValidator;

@RequiredArgsConstructor
public class LabyrinthCLI implements InteractiveCLI<Labyrinth> {


    private final LabyrinthProvider<Path> labyrinthProvider;
    private final LabyrinthValidator labyrinthValidator;
    private Path path;
    private int startY;
    private int startX;
    private int exitY;
    private int exitX;

    @Override
    public Labyrinth run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Print path ");
        path = Path.of(scanner.nextLine());
        System.out.print("Print start pos(Y) ");
        startY = scanner.nextInt();
        System.out.print("Print start pos(X) ");
        startX = scanner.nextInt();
        System.out.print("Print exit pos(Y) ");
        exitY = scanner.nextInt();
        System.out.print("Print exit pos(X) ");
        exitX = scanner.nextInt();
        System.out.println();

        List<String> labyrinthLayers = labyrinthProvider.getLabyrinthLayers(path);

        labyrinthValidator.labyrinthIsCorrect(labyrinthLayers);
        labyrinthValidator.exitPointIsCorrect(labyrinthLayers, exitY, exitX);
        labyrinthValidator.startPointIsCorrect(labyrinthLayers, startY, startX);

        return new Labyrinth(startY, startX, exitY, exitX, labyrinthLayers);
    }
}

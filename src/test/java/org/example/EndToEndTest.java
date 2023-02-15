package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import org.example.cli.InteractiveCLI;
import org.example.cli.LabyrinthCLI;
import org.example.resolver.LabyrinthEscaper;
import org.example.resolver.OneWayLabyrinthEscaper;
import org.example.sourceprovider.FileLabyrinthProvider;
import org.example.sourceprovider.LabyrinthProvider;
import org.example.validation.LabyrinthValidator;
import org.example.validation.DefaultLabyrinthValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndToEndTest {

    private final String labAnswer =
        """
            --+----+--------
            --++++++----+---
            -------+--+++---
            -------+--+-----
            -------+--+-----
            -------++++-----
            -------+-----+++
            -------+++++++--
            #######+--+--+++
            ------#---+-----
            ------#---+++---
            ------#---+-----
            ------#####-----
            ----------#-----
            ----------#-----
            ----------+-----
             """;
    private final String resourcesPath =
        "src" + File.separator + "test" + File.separator + "resources";
    private FileInputStream input = null;
    private PrintStream captureSystemOut = null;
    private InteractiveCLI<Labyrinth> labyrinthCLI;
    private LabyrinthProvider<Path> labyrinthProvider;

    private LabyrinthValidator labyrinthValidator;

    private LabyrinthEscaper<String> escaper;

    @BeforeEach
    public void setUp() throws IOException {
        input = new FileInputStream(resourcesPath + File.separator + "End-To-EndTest.txt");
        captureSystemOut = new PrintStream(
            new FileOutputStream(resourcesPath + File.separator + "EscapeTest_ParserTest.txt"));
        System.setIn(input);
        System.setOut(captureSystemOut);

        labyrinthProvider = new FileLabyrinthProvider();

        labyrinthValidator = new DefaultLabyrinthValidator('-', '+');

        labyrinthCLI = new LabyrinthCLI(labyrinthProvider, labyrinthValidator);


    }

    @AfterEach
    public void tearDown() {
        input = null;
    }


    @Test
    public void FullTest() {
        Labyrinth labyrinth = labyrinthCLI.run();

        escaper = new OneWayLabyrinthEscaper(labyrinth, '-', '+', '#');

        System.out.println(escaper.getExit());

        StringBuilder concat = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
            new FileReader(resourcesPath + File.separator + "EscapeTest_ParserTest.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                concat.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        concat.delete(0, 86);
        concat.delete(concat.length() - 1, concat.length());
        assertEquals(concat.toString(), labAnswer);
    }
}
package org.example.sourceprovider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLabyrinthProvider implements LabyrinthProvider<Path>{

    @Override
    public List<String> getLabyrinthLayers(Path source) {
        List<String> labyrinthLayers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(source.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                labyrinthLayers.add(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return labyrinthLayers;
    }
}

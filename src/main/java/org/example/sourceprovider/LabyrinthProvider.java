package org.example.sourceprovider;

import java.util.List;

public interface LabyrinthProvider<T> {

    List<String> getLabyrinthLayers(T source);
}

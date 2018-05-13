package ru.asmi;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface GetOccurencies {
    /**
     * This method writes sentences from resources with certain words to file.
     * @param sources - array of resources.
     * @param words - array of certain words.
     * @param res - name of file.
     * @throws IOException- exception with input/output.
     */
    void getOccurencies(String[] sources, String[] words, String res) throws IOException, InterruptedException, ExecutionException, NullPointerException;
}

package ru.asmi;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SentenceHandler implements Runnable {

    private final static Logger errorLogger = Logger.getLogger("errorLogger");
    private final static Logger debugLogger = Logger.getLogger("debugLogger");
    private static final int numSentences = 20;
    private static final Object mutex = new Object();
    private String path;
    private List<String> words;
    private FileWriter writer;


    public SentenceHandler(String path, List<String> words, FileWriter writer) {
        this.path = path;
        this.words = words;
        this.writer = writer;
    }

    /**
     * This method writes sentences to file.
     * @param sentences - array of sentences to write to the file.
     * @throws IOException - exception with input/output.
     */
    private void writeToFile(List<String> sentences) throws IOException {
        for(String sentence : sentences) writer.append(sentence + "\n");
        writer.flush();
    }

    /**
     * This method reads resource by line and tries to find sentences with needful words, if the method finds number of
     * sentence, which equal numSentences or resource is finished, it calls function writeToFile.
     * @param scanner - link to resource.
     * @throws IOException - exception with input/output.
     */
    private void getSentences(Scanner scanner) throws IOException {
        scanner.useDelimiter("(?<=[.!?;\n\r])");
        List<String> sentences = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.next();
            for (String word: words) {
                if(line.contains(word)) {
                    sentences.add(line);
                    break;
                }
            }
            if (sentences.size() >= numSentences) {
                synchronized (mutex) {
                    writeToFile(sentences);
                }
                sentences.clear();
            }
        }
        if (!sentences.isEmpty()) {
             synchronized (mutex) {
                 writeToFile(sentences);
             }
        }
    }

    @Override
    public void run() {
        debugLogger.debug("resource in work: " + path);
        try {
            if (path.startsWith("http") || path.startsWith("ftp")) {
                URL url = new URL(path);
                try (Scanner scanner = new Scanner(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"))) {
                    getSentences(scanner);
                }
            } else {
                try (Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream(path)))) {
                    getSentences(scanner);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            errorLogger.error("IOException");
        }
    }
}

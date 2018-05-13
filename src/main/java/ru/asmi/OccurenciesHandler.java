package ru.asmi;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OccurenciesHandler implements GetOccurencies {

    private final static Logger infoLogger = Logger.getLogger("infoLogger");

    /**
     * This method deletes previous file.
     * @param res - name of file for deleting.
     */
    private void delPreviousFile(String res) {
        File file = new File(res);
        if(file.exists()) file.delete();
    }

    @Override
    public void getOccurencies(String[] sources, String[] words, String res) throws IOException, InterruptedException, ExecutionException, NullPointerException {
        List<Future<?>> tasks = new ArrayList<>();

        infoLogger.info("Parser start working.");
        delPreviousFile(res);
        List<String> filteredWords = Stream.of(words).filter(Objects::nonNull).collect(Collectors.toList());
        List<String> filteredSources = Stream.of(sources).filter(Objects::nonNull).collect(Collectors.toList());
        if(filteredWords.isEmpty() || filteredSources.isEmpty()) {
            throw new NullPointerException("we don`t have any sources or words");
        }


        try(FileWriter writer = new FileWriter(res)) {
            ExecutorService service = Executors.newCachedThreadPool();
            for (String source : filteredSources) tasks.add(service.submit(new SentenceHandler(source, filteredWords, writer)));
            for (Future<?> task : tasks) task.get();
            service.shutdown();
        }

    }
}

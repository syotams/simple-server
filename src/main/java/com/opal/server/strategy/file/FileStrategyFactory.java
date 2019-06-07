package com.opal.server.strategy.file;

public class FileStrategyFactory {

    public FileStrategy create(String extension) {
        return new BinaryFileStrategy();
        /*if(Pattern.matches("(\\.html|\\.txt||\\.css|\\.js)", extension)) {
            return new TextFileStrategy();
        }
        else {
            return new BinaryFileStrategy();
        }*/
    }

}

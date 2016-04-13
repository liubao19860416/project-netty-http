package com.saic.ebiz.test;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;


public class FileTest {

    public static void main(String[] args) throws IOException {
        Path path = null;
        FileLock lock=FileChannel.open(path, null).lock();
        FileLock lock2=FileChannel.open(path, null).tryLock();
    }
    
    
}
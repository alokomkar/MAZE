package mtechproject.filesysmonitor;

import java.io.*;

public class RedirectOutputtoFile {
        public static void main(String[] argv) throws Exception {
                System.setOut(new PrintStream(new FileOutputStream("E:/DirMonitorLog.txt")));
                System.out.println("Hello India");
        }
}
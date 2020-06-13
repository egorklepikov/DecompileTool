package decompiletool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class CmdProcessor {
  protected static boolean processCmdCommand(String command, boolean isResolverRequired) {
    AtomicBoolean result = new AtomicBoolean(false);
    Process process;
    try {
      Runtime runtime = Runtime.getRuntime();
      if (isResolverRequired) {
        process = runtime.exec(new ResourceResolver().resolve(command));
      } else {
        process = runtime.exec(command);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
      return false;
    }

    if (process == null) throw new NullPointerException();

    Process finalProcess = process;
    Thread inputStreamThread = new Thread(() -> result.set(outputThread("input", finalProcess)));
    Thread errorStreamThread = new Thread(() -> result.set(outputThread("error", finalProcess)));

    inputStreamThread.start();
    errorStreamThread.start();

    try {
      inputStreamThread.join();
      errorStreamThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return false;
    }
    return result.get();
  }

  private static boolean outputThread(String outputType, Process process) {
    InputStream stream;
    if (outputType.equals("input")) {
      stream = process.getInputStream();
    } else if (outputType.equals("error")) {
      stream = process.getErrorStream();
    } else {
      return false;
    }

    InputStreamReader streamReader = new InputStreamReader(stream);
    BufferedReader bufferedReader = new BufferedReader(streamReader);

    try {
      String error = bufferedReader.readLine();
      while (error != null) {
        System.out.println(error);
        error = bufferedReader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return true;
  }
}

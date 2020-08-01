package decompiletool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

public class CmdProcessor {
  private static Process process;
  public static volatile boolean forceProcessInterrupt = false;

  protected static boolean processCmdCommand(String command, boolean isResolverRequired) {
    AtomicBoolean result = new AtomicBoolean(false);
    try {
      Runtime runtime = Runtime.getRuntime();
      if (isResolverRequired) {
        process = runtime.exec(ResourceResolver.getInstance().resolve(command));
      } else {
        process = runtime.exec(command);
      }
    } catch (IOException ex) {
      ex.printStackTrace();
      return false;
    }

    if (process == null) throw new NullPointerException();

    Thread inputStreamThread = new Thread(() -> result.set(getThread("input")));
    Thread errorStreamThread = new Thread(() -> result.set(getThread("error")));
    Thread interruptProcessThread = new InterruptProcessListener();

    inputStreamThread.start();
    errorStreamThread.start();
    interruptProcessThread.start();

    try {
      inputStreamThread.join();
      errorStreamThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return false;
    }
    return result.get();
  }

  private static boolean getThread(String outputType) {
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
      String message = bufferedReader.readLine();
      while (message != null) {
        System.out.println(message);
        message = bufferedReader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return true;
  }

  private static class InterruptProcessListener extends Thread {
    @Override
    public void run() {
      while (process.isAlive()) {
       if (forceProcessInterrupt) {
         process.destroy();
         forceProcessInterrupt = false;
         System.out.println("The process was successfully killed");
       }
      }
    }
  }
}

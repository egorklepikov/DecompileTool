package easytool.predictor;

import easytool.cmd.Command;
import java.util.List;

public class PredictProcess implements Command {
    private Predictor predictor;

    public PredictProcess() {
        predictor = new Predictor();
    }

    @Override
    public boolean execute() {
        return predictor.predict();
    }

    @Override
    public void startNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Поиск проблем внутри АПК");
        System.out.println("----------------------------------------------------");
    }

    @Override
    public void endNotification() {
        System.out.println("----------------------------------------------------");
        System.out.println("Поиск окончен. Информация для отладки: ");
        System.out.println("----------------------------------------------------");
        List<String> notificationList = predictor.getNotificationList();
        if (!notificationList.isEmpty()) {
            for (String notification : notificationList) {
                System.out.println(notification);
            }
        } else {
            System.out.println("----------------------------------------------------");
            System.out.println("Информации для отладки нет");
            System.out.println("----------------------------------------------------");
        }
    }

    @Override
    public void errorNotification() {
        List<String> problemList = predictor.getProblemList();
        if (!problemList.isEmpty()) {
            for (String problem : problemList) {
                System.out.println(problem);
            }
        } else {
            System.out.println("----------------------------------------------------");
            System.out.println("Проблем не выявлено");
            System.out.println("----------------------------------------------------");
        }
    }
}

package ru.sbtqa.tag.allurehelper;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper to add actions in case exceptions are fired
 *
 * @author Viktor Sidochenko <viktor.sidochenko@gmail.com>
 */
public class OnFailureScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(OnFailureScheduler.class);

    private static final List<Runnable> runnables = new ArrayList<>();

    public static void addAction(Runnable task) {
        runnables.add(task);
    }

    public void processPendings() {
        for (int i = 0; i < runnables.size(); i++) {
            try {
                Runnable r = runnables.remove(0);
                r.run();
            } catch (Exception | AssertionError t) {
                LOG.warn("Cannot execute failure action", t);
            }
        }
    }

}

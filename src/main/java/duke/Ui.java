package duke;

import java.util.Scanner;

/**
 * A duke.Ui object deals with interactions with the user.
 *
 * @author amelia
 * @version 1.0
 * @since 2020-08-26
 */
public class Ui {

    /**
     * Obtain user inputs and edit the duke.TaskList currList accordingly.
     *
     * @param currList Current list of tasks.
     */
    public void start(TaskList currList) {
        Scanner sc = new Scanner(System.in);
        Parser parser = new Parser(currList);
        while (sc.hasNext()) {
            String inputMsg = sc.nextLine();
            if (inputMsg.equals("bye")) {
                // ends the bot
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            parser.processMsg(inputMsg);
        }
        sc.close();
    }
}

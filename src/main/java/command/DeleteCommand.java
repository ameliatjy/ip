package command;

import duke.TaskList;
import duke.Ui;
import exception.DukeException;
import task.Task;

public class DeleteCommand extends Command {
    /**
     * Deletes an existing task from the list.
     *
     * @param inputMsg User input which contains the task number to be deleted.
     * @throws DukeException If task number indicated does not exist.
     */
    @Override
    public String execute(String inputMsg, TaskList currList, Ui ui) throws DukeException {
        // gets the deleted task number
        int taskNumber = Integer.valueOf(inputMsg.split(" ")[1]);

        if (currList.getNumOfTasks() < taskNumber || taskNumber <= 0) {
            throw new DukeException("There is no such task number!");
        } else {
            Task currTask = currList.get(taskNumber - 1);
            return ui.deleteTask(taskNumber, currTask, currList);
        }
    }
}
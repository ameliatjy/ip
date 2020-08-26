import java.time.LocalDateTime;

public class Parser {
    private TaskList currList;

    Parser(TaskList currList) {
        this.currList = currList;
    }

    public void viewTasks() throws DukeException {
        if (currList.getNumOfTasks() == 0) { // user has not added any task
            throw new DukeException("Nothing has been added to the list yet!");
        } else {
            for (int i = 0; i < currList.getNumOfTasks(); i++) {
                String output = (i + 1) + ". " + currList.get(i).toString();
                System.out.println(output);
            }
        }
    }

    public void markDone(String inputMsg) throws DukeException {
        int taskNumber = Integer.valueOf(inputMsg.split(" ")[1]); // gets the done task number
        if (currList.getNumOfTasks() < taskNumber || taskNumber <= 0) {
            throw new DukeException("There is no such task number!");
        } else {
            Task currTask = currList.get(taskNumber - 1);
            if (currTask.getStatus()) { // task has already marked done before
                throw new DukeException("Task has already been completed earlier on!");
            } else {
                currTask.markAsComplete();
                System.out.println("Nice! I've marked this task as done:\n" + currTask.toString());
            }
        }
    }

    public void deleteFromList(String inputMsg) throws DukeException {
        int taskNumber = Integer.valueOf(inputMsg.split(" ")[1]); // gets the deleted task number
        if (currList.getNumOfTasks() < taskNumber || taskNumber <= 0) {
            throw new DukeException("There is no such task number!");
        } else {
            Task currTask = currList.get(taskNumber - 1);
            currList.remove(taskNumber - 1);
            String output = "Noted. I've removed this task:\n"
                    + currTask.toString()
                    + "\nNow you have " + currList.getNumOfTasks() + " tasks in the list.";
            System.out.println(output);
        }
    }

    public LocalDateTime processDate(String inputDeadline) throws DukeException {
        String dateFormat;
        if (inputDeadline.contains("/")) {
            dateFormat = "/";
        } else if (inputDeadline.contains(".")) {
            dateFormat = "\\.";
        } else if (inputDeadline.contains("-")) {
            dateFormat = "-";
        } else {
            throw new DukeException("Please input valid date format!");
        }
        String[] inputData = inputDeadline.split(dateFormat);
        int date = Integer.parseInt(inputData[0]);
        int month = Integer.parseInt(inputData[1]);
        int year = Integer.parseInt(inputData[2].substring(0, 4));
        int hour = Integer.parseInt(inputData[2]. substring(5, 7));
        int min = Integer.parseInt(inputData[2]. substring(7));
        return LocalDateTime.of(year, month, date, hour, min);
    }

    public void addToList(String inputMsg, String actionType) throws DukeException {
        Task newTask;
        int numOfWords = inputMsg.split(" ").length;
        switch (actionType) {
            case "todo": {
                if (numOfWords <= 1) {
                    throw new DukeException("Description of task cannot be empty!");
                }
                String taskName = inputMsg.substring(5);
                newTask = new Todo(taskName, false);
                break;
            }
            case "deadline": {
                if (numOfWords <= 1) {
                    throw new DukeException("Description of task cannot be empty!");
                }
                String task = inputMsg.split("/")[0];
                String taskName = task.substring(9, task.length() - 1);
                String inputDeadline = inputMsg.split("/", 2)[1].substring(3);
                LocalDateTime deadline = processDate(inputDeadline);
                newTask = new Deadline(taskName, false, deadline);
                break;
            }
            case "event": {
                if (numOfWords <= 1) {
                    throw new DukeException("Description of task cannot be empty!");
                }
                String task = inputMsg.split("/")[0];
                String taskName = task.substring(6, task.length() - 1);
                String inputDeadline = inputMsg.split("/", 2)[1].substring(3);
                LocalDateTime deadline = processDate(inputDeadline);
                newTask = new Event(taskName, false, deadline);
                break;
            }
            default:  // when user keys in unregistered action
                throw new DukeException("Specified action is not recognised.");
        }
        currList.add(newTask);
        String outputMsg = "Got it. I've added this task:\n"
                + newTask.toString()
                + "\nYou have " + currList.getNumOfTasks() + " tasks in the list.";
        System.out.println(outputMsg);
    }

    public void processMsg(String inputMsg) {
        String actionType = inputMsg.split(" ")[0]; // user specified action, to identify type of action

        if (inputMsg.equals("list")) { // sees all tasks
            try {
                viewTasks();
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        } else if (actionType.equals("done")) { // mark task as done
            try {
                markDone(inputMsg);
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        } else if (actionType.equals("delete")) {
            try {
                deleteFromList(inputMsg);
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
        } else { // add task to list
            try {
                addToList(inputMsg, actionType);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

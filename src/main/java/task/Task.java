package task;

public class Task {
    protected String taskName;
    protected boolean isCompleted;

    /**
     * @param taskName Name of task.
     * @param isCompleted Whether task has been completed.
     */
    public Task(String taskName, boolean isCompleted) {
        this.taskName = taskName;
        this.isCompleted = isCompleted;
    }

    /**
     * @return Current task name.
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * @return Current task's state of completion.
     */
    public boolean getStatus() {
        return this.isCompleted;
    }

    /**
     * Marks the current task as complete by changing its boolean attribute isCompleted.
     */
    public void markAsComplete() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        String task = "[";
        if (isCompleted) {
            task += "done";
            // task += "\u2713";
        } else {
            task += "not done";
            // task += "\u274C";
        }
        task += "] " + taskName;
        return task;
    }
}

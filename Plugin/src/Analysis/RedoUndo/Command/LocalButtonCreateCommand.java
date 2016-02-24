package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.LocalButton;

/**
 * Created by woong on 2016-02-15.
 */
public class LocalButtonCreateCommand implements Command{
    private LocalButton localButton;

    public LocalButtonCreateCommand(LocalButton localButton) {
        this.localButton = localButton;
    }

    @Override
    public void execute() {
        localButton.create();
    }

    @Override
    public void undo() {
        localButton.remove();
    }
}

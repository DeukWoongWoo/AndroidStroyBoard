package GUI.StoryBoard;

import GUI.StoryBoard.UI.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by 우철 on 2016-02-17.
 */
public class StoryBoard_PlugIn implements ToolWindowFactory {
    private JPanel totalPanel;
    private JPanel centerPanel;
    private JPanel eastPanel;
    private JPanel northPanel;

    private ToolWindow mainViewWindow;

    public StoryBoard_PlugIn() throws IOException {
        System.out.println("StoryBoard_Plugin...");
        totalPanel = new JPanel();
        centerPanel = new storyBoard();
        northPanel = new menuPanel();
        eastPanel = new componetTreePanel();
        totalPanel.setLayout(new BorderLayout());

        totalPanel.add(centerPanel,"Center");
        totalPanel.add(northPanel,"North");
        totalPanel.add(eastPanel,"East");

    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        System.out.println("createToolWindowContent...");
        mainViewWindow=toolWindow;

        System.out.println("Create Tool Window : " + project.getBasePath());

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(totalPanel, "", false);
        toolWindow.getContentManager().addContent(content);

    }

}

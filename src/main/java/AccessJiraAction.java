import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.jetbrains.annotations.NotNull;
import utils.PluginUtils;

import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;

public class AccessJiraAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        String commitMessage = PluginUtils.getCommitMessage(project, virtualFile);
        if(commitMessage.contains("Story:")) {
            String[] messages = commitMessage.split(",");
            for (String msg : messages) {
                if (msg.contains("Story:")) {
                    commitMessage = msg.substring("Story:".length());
                    break;
                }
            }
            String username = "a.kambian-veettil2@newcastle.ac.uk";
            String password = "4j8zGGIDkaCtxlDWf1GyF4B7";
            try {
                URL url = new URL("https://aswinkv.atlassian.net/rest/api/3/issue/"+commitMessage);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization",
                        "Basic " + Base64.getEncoder().encodeToString(
                                (username + ":" + password).getBytes()
                        )
                );
                conn.connect();
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline.toString());
                JSONObject fields = (JSONObject) data_obj.get("fields");
                JSONObject assignee = (JSONObject) fields.get("assignee");
                String assigneeEmail = assignee.getAsString("emailAddress");
                URI mailto = new URI("mailto:" + assigneeEmail);
                Desktop.getDesktop().mail(mailto);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

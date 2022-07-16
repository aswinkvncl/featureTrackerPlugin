import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.diff.ItemLatestState;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.vcsUtil.VcsUtil;
import git4idea.GitCommit;
import git4idea.commands.Git;
import git4idea.history.GitHistoryUtils;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;
import utils.PluginUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AccessDocumentationAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        //String selectedText = e.getData(PlatformDataKeys.EDITOR).getSelectionModel().getSelectedText();
        //AbstractVcs vcs = ProjectLevelVcsManager.getInstance(project).getVcsFor(virtualFile);
        Project project = e.getData(PlatformDataKeys.PROJECT);
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        String commitMessage = PluginUtils.getCommitMessage(project, virtualFile);
        if (commitMessage.contains("Documentation:")) {
            BrowserUtil.browse(commitMessage.substring("Documentation:".length()));
        }
    }
}

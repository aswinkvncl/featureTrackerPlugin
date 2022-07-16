package utils;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.diff.ItemLatestState;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcsUtil.VcsUtil;
import git4idea.GitCommit;
import git4idea.history.GitHistoryProvider;
import git4idea.history.GitHistoryUtils;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;

import java.util.List;
import java.util.stream.Collectors;

public class PluginUtils {
    public static String getCommitMessage(Project project, VirtualFile virtualFile) {
        if(project != null && virtualFile != null) {
            try {
                FilePath filePath = VcsUtil.getFilePath(virtualFile.getPath());
                ItemLatestState commitDetails = null;
                commitDetails = GitHistoryUtils.getLastRevision(project, filePath);
                if (commitDetails != null) {
                    String changeCommitHash = commitDetails.getNumber().asString();
                    GitRepository repository = GitRepositoryManager.getInstance(project).getRepositories().get(0);
                    List<GitCommit> history = GitHistoryUtils.history(project, repository.getRoot());
                    return history.stream().filter((x) -> x.getId().toString().equals(changeCommitHash)).collect(Collectors.toList()).get(0).getFullMessage();
                }
            } catch (VcsException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}

package nz.hailwood.inertiajs

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ReadAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.util.concurrency.AppExecutorUtil
import nz.hailwood.inertiajs.settings.InertiaSettingsService
import nz.hailwood.inertiajs.settings.ShowSettingsAction
import java.io.File

class InertiaStartupActivity : StartupActivity.DumbAware {

    override fun runActivity(project: Project) {

        ReadAction.nonBlocking {
            if (project.isInertia) {
                val pagesRoot = VfsUtil.findFileByIoFile(File(InertiaSettingsService.inertiaPagesRoot(project)), true)

                if (pagesRoot == null) {
                    Notification(
                        "Inertia Plugin",
                        "Default Inertia.js pages root not found",
                        "Please configure the path appropriately in settings.",
                        NotificationType.WARNING
                    )
                        .addAction(ShowSettingsAction())
                        .notify(project)
                }
            }
        }.inSmartMode(project).submit(AppExecutorUtil.getAppExecutorService())
    }

}
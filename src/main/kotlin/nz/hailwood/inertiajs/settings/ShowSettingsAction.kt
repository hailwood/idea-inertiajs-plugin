package nz.hailwood.inertiajs.settings

import com.intellij.CommonBundle
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.options.ShowSettingsUtil

class ShowSettingsAction : AnAction(
    @Suppress("DialogTitleCapitalization")
    "Open Inertia.js settings",
    CommonBundle.settingsActionDescription(), AllIcons.General.Settings
) {
    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = true
    }

    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance().showSettingsDialog(e.project, InertiaSettingsInterface::class.java)
    }
}
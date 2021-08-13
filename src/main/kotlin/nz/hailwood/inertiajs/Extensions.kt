package nz.hailwood.inertiajs

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.php.PhpIndex
import nz.hailwood.inertiajs.settings.InertiaSettingsService

val Project.isInertia: Boolean
    get() = PhpIndex.getInstance(this).getClassesByFQN("\\Inertia\\Inertia").isNotEmpty()

val Project.isNotInertia: Boolean
    get() = !this.isInertia

fun VirtualFile.displayPath(project: Project): String {
    val pagesRoot = InertiaSettingsService.inertiaPagesRoot(project)
    return this.path.removePrefix("${pagesRoot}/").removeSuffix(".${this.extension}")
}
package nz.hailwood.inertiajs

import com.intellij.json.psi.JsonFile
import com.intellij.lang.javascript.buildTools.npm.PackageJsonUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.jetbrains.php.composer.ComposerConfigUtils
import com.jetbrains.php.composer.ComposerUtils
import nz.hailwood.inertiajs.settings.InertiaSettingsService
import java.io.File

val Project.isInertia: Boolean
    get() = this.isInertiaFromComposer || this.isInertiaFromPackageJson

val Project.isInertiaFromComposer: Boolean
    get() {
        val composerJsonVirtualFile = ComposerUtils.findFileInProject(this, "composer.json") ?: return false
        return ComposerConfigUtils.getInstalledPackagesFromConfig(composerJsonVirtualFile)
            .any { it.name == "inertiajs/inertia-laravel" }
    }

val Project.isInertiaFromPackageJson: Boolean
    get() {
        val packageJsonVirtualFile = PackageJsonUtil.findChildPackageJsonFile(this.guessProjectDir()) ?: return false
        val packageJson = PsiManager.getInstance(this).findFile(packageJsonVirtualFile) ?: return false
        return PackageJsonUtil.findDependencyByName(packageJson as JsonFile, "@inertiajs/inertia") != null
    }

val Project.isNotInertia: Boolean
    get() = !this.isInertia

fun VirtualFile.displayPath(project: Project): String {
    val pagesRoot = FileUtil.toSystemIndependentName(InertiaSettingsService.inertiaPagesRoot(project).plus("/"))

    return FileUtil.toSystemIndependentName(this.path).removePrefix(pagesRoot).removeSuffix(".${this.extension}")
}
package nz.hailwood.inertiajs.navigation

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentIterator
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiReferenceBase
import nz.hailwood.inertiajs.displayPath
import nz.hailwood.inertiajs.settings.InertiaSettingsService
import java.io.File

class InertiaPageReference(element: PsiElement, textRange: TextRange) : PsiReferenceBase<PsiElement?>(element, textRange) {

    private val key: String = element.text.substring(textRange.startOffset, textRange.endOffset)

    override fun resolve(): PsiElement? = locateReferences(element.project, key).firstOrNull()

    override fun getVariants(): Array<LookupElement> = locateReferences(element.project, null).map {
        LookupElementBuilder
            .create(it.virtualFile.displayPath(element.project))
            .withIcon(it.fileType.icon)
    }.toTypedArray()

    private fun locateReferences(project: Project, name: String?): List<PsiFile> {
        val pagesDir =  VfsUtil.findFileByIoFile(File(InertiaSettingsService.inertiaPagesRoot(project)), true) ?: return emptyList()

        val references = mutableListOf<PsiFile>()

        ProjectRootManager.getInstance(project).fileIndex.iterateContentUnderDirectory(pagesDir, object : ContentIterator {
            override fun processFile(fileOrDir: VirtualFile): Boolean {
                if (fileOrDir.isDirectory) {
                    return true
                }

                if (name == null || name == fileOrDir.displayPath(project)) {
                    PsiManager.getInstance(project).findFile(fileOrDir)?.let { file -> references.add(file) }
                }

                return true
            }
        })

        return references
    }

}
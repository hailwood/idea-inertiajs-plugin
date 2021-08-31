package nz.hailwood.inertiajs

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.find.actions.ShowUsagesAction
import com.intellij.lang.javascript.psi.JSFile
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.PsiEditorUtil
import com.intellij.ui.awt.RelativePoint
import nz.hailwood.inertiajs.messages.InertiaBundle
import nz.hailwood.inertiajs.navigation.InertiaPageReference
import java.awt.event.MouseEvent

class InertiaRenderLineMarkerProvider : LineMarkerProviderDescriptor(), GutterIconNavigationHandler<PsiElement> {
    override fun getName(): String = "InertiaRenderLineMarkerProvider"

    override fun collectSlowLineMarkers(elements: MutableList<out PsiElement>, result: MutableCollection<in LineMarkerInfo<*>>) {
        val psiFile = elements.firstOrNull { it is JSFile } ?: return

        val hasReferences = ReferencesSearch.search(psiFile, psiFile.useScope).anyMatch { it is InertiaPageReference }

        if (!hasReferences) return

        result.add(LineMarkerInfo(
            psiFile,
            TextRange(0, 0),
            InertiaIcons.Reference,
            { InertiaBundle.message("line.marker.render.description") },
            this,
            GutterIconRenderer.Alignment.RIGHT,
            { InertiaBundle.message("line.marker.render.description") }
        ))
    }

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? = null

    override fun navigate(e: MouseEvent, psiFile: PsiElement) {
        if (DumbService.getInstance(psiFile.project).isDumb) {
            return
        }

        ShowUsagesAction.startFindUsages(
            psiFile,
            RelativePoint(e),
            PsiEditorUtil.findEditor(psiFile),
        )
    }

}
package nz.hailwood.inertiajs.linemarkerprovider

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.icons.AllIcons.RunConfigurations.TestState.Run
import com.intellij.openapi.editor.markup.GutterIconRenderer.Alignment.RIGHT
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import java.awt.event.MouseEvent

class InertiaRenderLineMarkerProvider : LineMarkerProvider {
    companion object {
        private const val PHP_OPEN_TAG = "php opening tag"
    }

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element.elementType.toString() == PHP_OPEN_TAG) {
            return LineMarkerInfo(
                element,
                element.textRange,
                Run,
                null,
                { _: MouseEvent, _: PsiElement ->  },
                RIGHT,
                {"Run"}
            )
        }

        return null
    }
}
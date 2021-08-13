package nz.hailwood.inertiajs.navigation

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceProvider
import com.intellij.util.ProcessingContext
import com.jetbrains.php.PhpIndex
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression
import com.jetbrains.php.lang.psi.elements.impl.FunctionReferenceImpl
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl
import com.jetbrains.php.lang.psi.resolve.types.PhpType

class InertiaPageReferenceProvider : PsiReferenceProvider() {
    private val inertiaPhpType = PhpType().add("\\Inertia\\Inertia")

    private fun isInertiaClassReference(methodRef: MethodReferenceImpl): Boolean {
        return methodRef.name == "render" && inertiaPhpType.isConvertibleFrom(PhpType().add(methodRef.classReference), PhpIndex.getInstance(methodRef.project))
    }

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        val literalExpression: StringLiteralExpression = element as StringLiteralExpression

        val variableContext = literalExpression.context as ParameterList

        val functionCallContext = variableContext.context

        val isInertiaCall = when {
            variableContext.parameters[0] != literalExpression -> false
            functionCallContext is MethodReferenceImpl         -> isInertiaClassReference(functionCallContext)
            functionCallContext is FunctionReferenceImpl       -> functionCallContext.name == "inertia"
            else                                               -> false
        }

        if (!isInertiaCall) {
            return PsiReference.EMPTY_ARRAY
        }

        val textRange = TextRange(1, literalExpression.contents.length + 1)
        return arrayOf(InertiaPageReference(element, textRange))
    }
}
package nz.hailwood.inertiajs.navigation

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
    private val inertiaPhpTypes = listOf(
        PhpType().add("\\Inertia\\Inertia")
    )

    private val laravelRoutePhpTypes = listOf(
        PhpType().add("\\Route"),
        PhpType().add("\\Illuminate\\Support\\Facades\\Route")
    )

    private fun isInertiaClassReference(methodRef: MethodReferenceImpl): Boolean {
        return methodRef.name == "render" && inertiaPhpTypes.any {
            it.isConvertibleFrom(PhpType().add(methodRef.classReference), PhpIndex.getInstance(methodRef.project))
        }
    }

    private fun isLaravelRouteClassReference(methodRef: MethodReferenceImpl): Boolean {
        return methodRef.name == "inertia" && laravelRoutePhpTypes.any {
            it.isConvertibleFrom(PhpType().add(methodRef.classReference), PhpIndex.getInstance(methodRef.project))
        }
    }

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        val literalExpression: StringLiteralExpression = element as StringLiteralExpression

        val variableContext = literalExpression.context as ParameterList

        val functionCallContext = variableContext.context

        var isInertiaCall = false
        if (functionCallContext is FunctionReferenceImpl) {
            isInertiaCall = functionCallContext.name == "inertia"
        } else if (functionCallContext is MethodReferenceImpl && isInertiaClassReference(functionCallContext)) {
            isInertiaCall = variableContext.parameters[0] == literalExpression
        } else if (functionCallContext is MethodReferenceImpl && isLaravelRouteClassReference(functionCallContext)) {
            isInertiaCall = variableContext.parameters[1] == literalExpression
        }

        if (!isInertiaCall) {
            return PsiReference.EMPTY_ARRAY
        }

        return arrayOf(InertiaPageReference(element, element.valueRange))
    }
}
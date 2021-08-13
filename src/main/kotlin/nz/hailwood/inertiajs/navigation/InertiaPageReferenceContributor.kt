package nz.hailwood.inertiajs.navigation

import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar
import com.jetbrains.php.lang.psi.elements.ParameterList
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression

class InertiaPageReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            psiElement(StringLiteralExpression::class.java).withParent(
                psiElement(ParameterList::class.java)
            ),
            InertiaPageReferenceProvider()
        )
    }

}
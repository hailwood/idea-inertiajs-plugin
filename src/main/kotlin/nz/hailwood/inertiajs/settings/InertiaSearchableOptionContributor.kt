package nz.hailwood.inertiajs.settings

import com.intellij.ide.ui.search.SearchableOptionContributor
import com.intellij.ide.ui.search.SearchableOptionProcessor
import nz.hailwood.inertiajs.messages.InertiaBundle

class InertiaSearchableOptionContributor : SearchableOptionContributor() {

    override fun processOptions(processor: SearchableOptionProcessor) {

        processor.addOptions(
            InertiaBundle.message("settings.pages.root.label"),
            null,
            "Inertia.js: ".plus(InertiaBundle.message("settings.pages.root.label")).dropLast(1),
            "nz.hailwood.inertiajs.settings.InertiaSettingsInterface",
            null, false
        )
    }
}
package nz.hailwood.inertiajs.dictionary

import com.intellij.spellchecker.BundledDictionaryProvider

class InertiaBundledDictionaryProvider : BundledDictionaryProvider {

    override fun getBundledDictionaries(): Array<String> = arrayOf("/dictionary/inertia.dic")

}
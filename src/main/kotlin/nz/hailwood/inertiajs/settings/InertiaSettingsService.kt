package nz.hailwood.inertiajs.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "Inertia.Settings", storages = [Storage("inertia.xml")])
class InertiaSettingsService : PersistentStateComponent<InertiaSettingsService> {

    var customInertiaPagesRoot: String = ""

    override fun getState(): InertiaSettingsService = this

    override fun loadState(state: InertiaSettingsService) {
        XmlSerializerUtil.copyBean(state, this)
    }

    fun defaultInertiaPagesRoot(project: Project): String {
        return project.guessProjectDir()?.path.plus("/resources/js/Pages")
    }

    companion object {
        private fun getInstance(project: Project): InertiaSettingsService {
            return project.getService(InertiaSettingsService::class.java)
        }

        fun inertiaPagesRoot(project: Project): String {
            return getInstance(project).let {
                it.customInertiaPagesRoot.ifEmpty { it.defaultInertiaPagesRoot(project) }
            }
        }
    }

}
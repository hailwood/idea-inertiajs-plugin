package nz.hailwood.inertiajs.services

import nz.hailwood.inertiajs.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

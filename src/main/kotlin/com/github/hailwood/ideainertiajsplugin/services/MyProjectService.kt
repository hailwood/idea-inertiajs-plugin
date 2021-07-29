package com.github.hailwood.ideainertiajsplugin.services

import com.github.hailwood.ideainertiajsplugin.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

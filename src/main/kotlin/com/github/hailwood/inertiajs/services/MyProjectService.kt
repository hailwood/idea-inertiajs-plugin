package com.github.hailwood.inertiajs.services

import com.github.hailwood.inertiajs.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

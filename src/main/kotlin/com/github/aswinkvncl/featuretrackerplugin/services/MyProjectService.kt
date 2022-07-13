package com.github.aswinkvncl.featuretrackerplugin.services

import com.intellij.openapi.project.Project
import com.github.aswinkvncl.featuretrackerplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

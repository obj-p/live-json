package com.objp.livejson.toolwindow

import com.intellij.openapi.fileChooser.FileSystemTree
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.ScrollPaneFactory
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.content.ContentFactory

class LiveJsonToolWindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val fileSystemTree = LiveJsonTree.createFileSystemTree(project)
        val liveJsonToolWindow = LiveJsonToolWindow(fileSystemTree)
        val content = ContentFactory.getInstance().createContent(liveJsonToolWindow.createContent(), null, false)
        content.setDisposer(fileSystemTree)

        toolWindow.contentManager.addContent(content)
    }

    class LiveJsonToolWindow(private val fileSystemTree: FileSystemTree) {
        fun createContent() = LiveJsonToolWindowPanel(fileSystemTree).also {
            val tabbedPane = JBTabbedPane()
            tabbedPane.add("Sources", ScrollPaneFactory.createScrollPane(fileSystemTree.tree))
//            tabbedPane.add("Environment", ScrollPaneFactory.createScrollPane(fileSystemTree.tree))
            it.add(tabbedPane)
        }
    }
}

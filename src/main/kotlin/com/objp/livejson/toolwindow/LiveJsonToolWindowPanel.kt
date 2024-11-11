package com.objp.livejson.toolwindow

import com.intellij.openapi.actionSystem.DataSink
import com.intellij.openapi.fileChooser.FileSystemTree
import com.intellij.openapi.ui.SimpleToolWindowPanel

class LiveJsonToolWindowPanel(private val fileSystemTree: FileSystemTree) : SimpleToolWindowPanel(true) {
    override fun uiDataSnapshot(sink: DataSink) {
        super.uiDataSnapshot(sink)
    }
}
package com.objp.livejson.toolwindow

import com.intellij.openapi.actionSystem.DataProvider
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.application.PathManager
import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileSystemTree
import com.intellij.openapi.fileChooser.ex.FileSystemTreeImpl
import com.intellij.openapi.fileChooser.tree.FileNode
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtilRt
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.treeStructure.Tree
import com.intellij.util.ui.tree.TreeUtil

class LiveJsonTree(private val project: Project) : Tree(), DataProvider {
    init {
        isRootVisible = false
    }

    override fun getData(dataId: String): Any? {
        return if (dataId == PlatformDataKeys.NAVIGATABLE_ARRAY.name) {
            TreeUtil.collectSelectedObjectsOfType(this, FileNode::class.java)
                .mapNotNull { node ->
                    if (node.file.isDirectory) null // Exclude directories from being navigatable
                    else OpenFileDescriptor(project, node.file)
                }
                .toTypedArray()
        } else null
    }

    companion object {
        fun createFileSystemTree(project: Project): FileSystemTree {
            val tree = LiveJsonTree(project)
            val descriptor = createFileChooserDescriptor()
            val impl = FileSystemTreeImpl(project, descriptor, tree, null, null, null)
            return impl
        }

        private fun createFileChooserDescriptor(): FileChooserDescriptor {
            val descriptor = object : FileChooserDescriptor(true, true, true, false, true, true) {
                override fun getName(virtualFile: VirtualFile) = virtualFile.name
            }
            descriptor.withShowFileSystemRoots(false)
            descriptor.withTreeRootVisible(false)

            val scratchPath = FileUtilRt.toSystemIndependentName(PathManager.getScratchPath()) + "/live-json"
            runWriteAction {
                descriptor.setRoots(VfsUtil.createDirectoryIfMissing(scratchPath))
            }

            return descriptor
        }
    }
}
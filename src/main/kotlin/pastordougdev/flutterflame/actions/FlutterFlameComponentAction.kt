package pastordougdev.flutterflame.actions

import com.intellij.codeInsight.actions.ReformatCodeProcessor
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.CommandProcessor
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileTypes.FileTypeManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import pastordougdev.dartbarrelfile.misc.getDirName
import pastordougdev.flutterflame.dialog.NewFlameComponentTabbedDialog
import pastordougdev.flutterflame.models.FlameComponentBuilder

class FlutterFlameComponentAction : AnAction() {

    private lateinit var dataContext: DataContext

    override fun update(event: AnActionEvent) {
        //Shows on the New group only if a directory is selected.
        val psiFile = event.getData(CommonDataKeys.PSI_FILE);
        event.presentation.isEnabledAndVisible = psiFile == null
    }

    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project;
        this.dataContext = e.dataContext;

        if(project == null) return

        val dirName = getDirName(this.dataContext)

        val dialog = NewFlameComponentTabbedDialog()
        dialog.show()

        if(!dialog.isOK) return

        val selectedComponent = dialog.getSelectedFlameComponent()

        println(selectedComponent.description)
        println(selectedComponent.getClassDeclaration(dialog.getClassName()))

        for(mixin in dialog.getSelectedMixins()) {
            println("Mixin ${mixin.getMixin()}")
        }

        val classBuilder = FlameComponentBuilder(selectedComponent,
            dialog.getClassName(),
            dialog.getClassGenerics(),
            dialog.getSelectedMixins(),
            dialog.getSelectedOverrides(),
            dialog.getSuperProperties(),
        );
//        println(classBuilder.getClassDeclaration())
//
//        println(classBuilder.onLoad())

        val view = LangDataKeys.IDE_VIEW.getData(this.dataContext);
        val dir = view?.orChooseDirectory;

        ApplicationManager.getApplication().runWriteAction {
            CommandProcessor.getInstance().executeCommand(
                project,
                {
                    createDartFile(project, dir!!, classBuilder.fileName(), classBuilder.getFlameComponent())

                },
                "Create Flame Component File",
                null
            )
        }

        WriteCommandAction.runWriteCommandAction(
            project
        ) {
            val styleManager = CodeStyleManager.getInstance(project);
            val cFile = dir!!.findFile("${classBuilder.fileName()}.dart")
            if (cFile != null) {
                val document = PsiDocumentManager.getInstance(project).getDocument(cFile)
                println("lets reformat")
                //TODO: For some reason, this call to the formatter whacks the constructor if it has anything more than ()
                styleManager.reformat(cFile)
            }
        }



        println(classBuilder.classBody())

    }

    fun createDartFile(project: Project, dir: PsiDirectory, fileName: String, contents: String) {
        val dartFileName = "$fileName.dart"
        val fileType = FileTypeManager.getInstance().getFileTypeByFileName(dartFileName)

        val existingFile = dir.findFile(dartFileName)

        if(existingFile != null) {
            val document = PsiDocumentManager.getInstance(project).getDocument(existingFile)
            val docLength = document?.textLength ?: 1
            document?.replaceString(0, docLength - 1, contents)
            ReformatCodeProcessor(existingFile, false).run()
            return
        }

        val newFile = PsiFileFactory.getInstance(project)
            .createFileFromText(dartFileName, fileType, contents)
        dir.add(newFile)
        //val document = PsiDocumentManager.getInstance(project).getDocument(newFile)

//        ReformatCodeProcessor(newFile, false).run()
//        PsiDocumentManager.getInstance(project).commitAllDocuments()
    }
}
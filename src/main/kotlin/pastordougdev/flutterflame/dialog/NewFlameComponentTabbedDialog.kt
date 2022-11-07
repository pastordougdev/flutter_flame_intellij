package pastordougdev.flutterflame.dialog

import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.components.JBTextArea
import com.intellij.util.ui.GridBag
import pastordougdev.flutterflame.data.flameComponentSuperProperties
import pastordougdev.flutterflame.data.flameComponents
import pastordougdev.flutterflame.data.flameMixins
import pastordougdev.flutterflame.models.ChosenFlameMixin
import pastordougdev.flutterflame.models.FlameComponent
import pastordougdev.flutterflame.models.FlameMixin
import pastordougdev.flutterflame.models.FlameMixinType
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class NewFlameComponentTabbedDialog : DialogWrapper(true) {

    private val flameComponentBox = ComboBox<String>(200)
    private val flameClassNameField: JTextField
    private val flameClassGenerics: JTextField
    private val flameMixinRows: List<MixinDialogRow>
    private val onLoadCheckBox = JBCheckBox("onLoad")
    private val onMountCheckBox = JBCheckBox("onMount")
    private val updateCheckBox = JBCheckBox("update")
    private val renderCheckBox = JBCheckBox("render")
    private val onRemoveCheckBox = JBCheckBox("onRemove")
    private val superPropertyCheckBoxes: List<JBCheckBox>

    init {
        flameComponentBox.maximumSize = Dimension(500, 40)
        flameComponentBox.preferredSize = Dimension(500, 40)
        flameComponentBox.isEditable = false
        for (fc in flameComponents) {
            flameComponentBox.addItem(fc.description)
        }

        flameClassNameField = JTextField(50)
        flameClassGenerics = JTextField(50)

        flameMixinRows = flameMixins.map {fm -> MixinDialogRow(fm)}

        onLoadCheckBox.isSelected = true
        onMountCheckBox.isSelected = true
        updateCheckBox.isSelected = true
        renderCheckBox.isSelected = true
        onRemoveCheckBox.isSelected = true

        superPropertyCheckBoxes = flameComponentSuperProperties.map { prop -> JBCheckBox(prop) }

        init()
        title = "New Flutter Flame Component"
    }

    override fun createCenterPanel(): JComponent? {
        val panel = JPanel()
        panel.layout = GridBagLayout()
        val c = GridBagConstraints()

        var panelRow = 0
        val selLabel = JLabel("Select Flame Component")
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 10
        c.gridx = 0
        c.gridy = panelRow
        panel.add(selLabel, c)

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 0
        c.gridx = 1
        c.gridy = panelRow
        panel.add(flameComponentBox, c)

        panelRow++
        val nameLabel = JLabel("Class Name")
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 10
        c.gridx = 0
        c.gridy = panelRow
        panel.add(nameLabel, c)

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 0
        c.gridx = 1
        c.gridy = panelRow
        panel.add(flameClassNameField, c)

        panelRow++
        val genLabel = JTextArea("Class Generics\nComma Separated")
        genLabel.isEditable = false
        genLabel.font = nameLabel.font
        genLabel.background = nameLabel.background
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 10
        c.gridx = 0
        c.gridy = panelRow
        panel.add(genLabel, c)

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 0
        c.gridx = 1
        c.gridy = panelRow
        panel.add(flameClassGenerics, c)

        panelRow++
        val superLabel = JLabel("Super Class Properties")
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 10
        c.gridx = 0
        c.gridy = panelRow
        panel.add(superLabel, c)

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 0
        c.gridx = 1
        c.gridy = panelRow
        panel.add(createSuperPanel(), c)

        panelRow++
        val overridesLabel = JLabel("Overrides")
        val mFont = overridesLabel.font
        val newMFont = Font(mFont.name, Font.BOLD, mFont.size)
        overridesLabel.font = newMFont
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipady = 20
        c.gridx = 0
        c.gridy = panelRow
        panel.add(overridesLabel, c)

        panelRow++
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipady = 20
        c.gridx = 0
        c.gridy = panelRow
        c.gridwidth = 2
        panel.add(createOverrides(), c)


        panelRow++
        val selMixinLabel = JLabel("Select Mixins")
        selMixinLabel.font = newMFont
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipady = 20
        c.gridx = 0
        c.gridy = panelRow
        panel.add(selMixinLabel, c)

        panelRow++
        c.fill = GridBagConstraints.HORIZONTAL
        c.ipady = 20
        c.gridx = 0
        c.gridy = panelRow
        c.gridwidth = 2
        panel.add(createTabs(), c)





        return panel
    }

    fun getSelectedFlameComponent(): FlameComponent {
        val idx = flameComponentBox.selectedIndex
        return flameComponents[idx]
        //return flameComponentBox.getItemAt(idx)
    }

    fun getClassName(): String {
        return flameClassNameField.text
    }

    fun getClassGenerics(): String {
        return flameClassGenerics.text
    }

    fun getSelectedMixins(): List<ChosenFlameMixin> {
        var selectedMixins = flameMixinRows.filter { fmRow -> fmRow.useMixin() }
        return selectedMixins.map { sm -> ChosenFlameMixin(sm.mixin.description, sm.genericTextField.text, sm.mixin.flamePackage)}
    }

    fun getSelectedOverrides(): Map<String, Boolean> {
        val o = mutableMapOf<String, Boolean>()
        o["onLoad"] = onLoadCheckBox.isSelected
        o["onMount"] = onMountCheckBox.isSelected
        o["update"] = updateCheckBox.isSelected
        o["render"] = renderCheckBox.isSelected
        o["onRemove"] = onRemoveCheckBox.isSelected
        return o
    }

    fun getSuperProperties(): List<String> {
        return superPropertyCheckBoxes.filter{ box -> box.isSelected}.map { selBox -> selBox.text}
    }

    private fun createOverrides() : JPanel {
        val oPanel = JPanel()
        oPanel.layout = GridBagLayout()
        val c = GridBagConstraints()

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 20
        c.gridx = 0
        c.gridy = 0
        oPanel.add(onLoadCheckBox, c)

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 20
        c.gridx = 1
        c.gridy = 0
        oPanel.add(onMountCheckBox, c)

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 20
        c.gridx = 2
        c.gridy = 0
        oPanel.add(updateCheckBox, c)

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 20
        c.gridx = 3
        c.gridy = 0
        oPanel.add(renderCheckBox, c)

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 20
        c.gridx = 4
        c.gridy = 0
        oPanel.add(onRemoveCheckBox, c)

        return oPanel
    }

    private fun createTabs() : JBTabbedPane {
        val pane = JBTabbedPane()

        for(mixinType in FlameMixinType.values()) {
            val filteredMixins = flameMixinRows.filter { fmr -> fmr.mixin.type == mixinType }
            if(filteredMixins.isNotEmpty()) {
                val mixinPanel = createMixinPanel(filteredMixins)
                pane.addTab(mixinType.description, mixinPanel)
            }
        }

        return pane
    }

    private fun createMixinPanel(mixins: List<MixinDialogRow>) : JPanel {
        val mixinPanel = JPanel()
        mixinPanel.layout = GridBagLayout()
        val c = GridBagConstraints()

        val mLabel = JLabel("Mixin:")
        val mFont = mLabel.font
        val mmFont = Font(mFont.name, Font.BOLD, mFont.size)
        mLabel.font = mmFont

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 10
        c.gridx = 0
        c.gridy = 0
        mixinPanel.add(mLabel, c)

        val gLabel = JLabel("Mixin Generic Types (Enter Comma Separated)")
        gLabel.font = mmFont

        c.fill = GridBagConstraints.HORIZONTAL
        c.ipadx = 10
        c.gridx = 1
        c.gridy = 0
        mixinPanel.add(gLabel, c)

        var mRow = 0

        for(mixinRow in mixins) {
            mRow++
            c.fill = GridBagConstraints.HORIZONTAL
            c.ipadx = 10
            c.gridx = 0
            c.gridy = mRow
            mixinPanel.add(mixinRow.mixinCheckBox, c)
            c.fill = GridBagConstraints.HORIZONTAL
            c.ipadx = 10
            c.gridx = 1
            c.gridy = mRow
            mixinPanel.add(mixinRow.genericTextField, c)
        }

        return mixinPanel
    }

    fun createSuperPanel() : JPanel {
        val superPanel = JPanel()
        superPanel.layout = GridBagLayout()
        val c = GridBagConstraints()

        var sRows = superPropertyCheckBoxes.size / 4
        if(superPropertyCheckBoxes.size % 4 > 0) sRows++
        var superIdx = 0
        for(sRow in 1..sRows) {
            var sCol = 1
            while(superIdx < superPropertyCheckBoxes.size && sCol < 5) {
                c.fill = GridBagConstraints.HORIZONTAL
                c.ipadx = 20
                c.gridx = sCol
                c.gridy = sRow
                superPanel.add(superPropertyCheckBoxes[superIdx], c)
                superIdx++
                sCol++
            }
        }

        return superPanel
    }
}
package pastordougdev.flutterflame.dialog

import com.intellij.ui.components.JBCheckBox
import pastordougdev.flutterflame.models.FlameMixin
import java.awt.Component
import java.awt.Dimension
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.JTextField

class MixinDialogRow (val mixin : FlameMixin) {

    val mixinCheckBox: JBCheckBox
    val genericFields = mutableListOf<JTextField>()
    val genericTextField = JTextField(30)

    init {
        mixinCheckBox = JBCheckBox()
        mixinCheckBox.text = mixin.description
        if(mixin.generics > 0) {
            for (i in 1..mixin.generics) {
                val tf = JTextField(30)
                tf.maximumSize = Dimension(150, 40)
                genericFields.add(tf)
            }
        }
    }

    fun useMixin() : Boolean {
        return mixinCheckBox.isSelected
    }

    fun getComponent() : JPanel {
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.LINE_AXIS)
        panel.alignmentX = Component.LEFT_ALIGNMENT
        panel.minimumSize = Dimension(500, 40)
        panel.maximumSize = Dimension(500, 40)
        panel.preferredSize = Dimension(500, 40)
        panel.add(mixinCheckBox)
        for(gf in genericFields) {
            panel.add(gf)
        }
        panel.add(Box.createHorizontalGlue())

        return panel
    }
}
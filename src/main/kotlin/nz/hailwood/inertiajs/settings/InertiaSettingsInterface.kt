package nz.hailwood.inertiajs.settings

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.text.StringUtil
import com.intellij.ui.DocumentAdapter
import com.intellij.ui.HyperlinkLabel
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.uiDesigner.core.Spacer
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.Color
import java.io.File
import javax.swing.JComponent
import javax.swing.JTextField
import javax.swing.UIManager
import javax.swing.event.DocumentEvent

class InertiaSettingsInterface(project: Project) : SearchableConfigurable {

    private val settings = project.getService(InertiaSettingsService::class.java)

    private val inertiaPagesRootField = TextFieldWithBrowseButton()

    override fun getPreferredFocusedComponent(): JComponent = inertiaPagesRootField

    init {
        inertiaPagesRootField.addBrowseFolderListener(
            "Path to Pages Root",
            null,
            project,
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
        )

        setupTextFieldDefaultValue(
            inertiaPagesRootField.textField,
            settings.defaultInertiaPagesRoot(project).replace("/", File.separator)
        )
    }

    @Suppress("DialogTitleCapitalization")
    override fun getDisplayName(): String = "Inertia.js"

    override fun isModified(): Boolean {
        return settings?.customInertiaPagesRoot != inertiaPagesRootField.text
    }

    override fun apply() {
        settings?.customInertiaPagesRoot = inertiaPagesRootField.text
    }

    fun getDefaultValueColor(): Color {
        return UIManager.getColor("TextField.inactiveForeground") ?: UIManager.getColor("nimbusDisabledText")
    }

    fun getChangedValueColor(): Color {
        return UIManager.getColor("TextField.foreground")
    }

    private fun setupTextFieldDefaultValue(textField: JTextField, defaultValue: String) {
        if (StringUtil.isEmptyOrSpaces(defaultValue)) return
        textField.document.addDocumentListener(object : DocumentAdapter() {
            override fun textChanged(e: DocumentEvent) {
                textField.foreground =
                    if (defaultValue == textField.text) getDefaultValueColor() else getChangedValueColor()
            }
        })
        if (textField is JBTextField) {
            textField.emptyText.text = defaultValue
        }
    }

    override fun createComponent(): JComponent {
        return FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Path to pages root:"), inertiaPagesRootField, 1, false)
            .addComponentToRightColumn(HyperlinkLabel().apply {
                @Suppress("UnstableApiUsage")
                setTextWithHyperlink("This should be set to the same location Inertia.js <hyperlink>resolves your pages from</hyperlink>.")
                JBUI.CurrentTheme.ContextHelp.FOREGROUND
                UIUtil.applyStyle(UIUtil.ComponentStyle.SMALL, this)
                addHyperlinkListener { BrowserUtil.browse("https://inertiajs.com/client-side-setup#initialize-app") }
            }, 1)
            .addComponentFillVertically(Spacer(), 0)
            .panel
    }

    override fun reset() {
        inertiaPagesRootField.text = settings?.customInertiaPagesRoot ?: ""
    }

    override fun getHelpTopic(): String? = null

    override fun getId(): String = "inertia-js"
}
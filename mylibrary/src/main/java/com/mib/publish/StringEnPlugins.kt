package com.mib.publish

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.StringWriter
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 *  author : cengyimou
 *  date : 2025/7/18 11:53
 *  description :
 */
class StringEnPlugins: Plugin<Project> {

    override fun apply(project: Project) {
        project.afterEvaluate {
            val androidExtension = project.extensions.findByName("android")

            if (androidExtension is BaseExtension) {
                androidExtension.buildTypes.forEach { buildType ->
                    val buildTypeName = buildType.name // "debug" / "release"
                    project.logger.lifecycle("StringEnPlugins Encrypting strings for $buildTypeName")
                    val sourceStringsFile = project.file("src/main/res/values/strings.xml")
                    if (!sourceStringsFile.exists()) return@forEach

                    val targetDir = project.file("src/$buildTypeName/res/values/")
                    val targetFile = File(targetDir, "strings_encrypted.xml")

                    if (!targetDir.exists()) {
                        targetDir.mkdirs()
                    }

                    val secretKey = "1234567890123456"
                    val encryptedContent = encryptStringsXml(sourceStringsFile, secretKey)

                    targetFile.writeText(encryptedContent)
                    project.logger.lifecycle("StringEnPlugins Encrypted strings written to ${targetFile.path}")
                }
            }
        }
    }

    private fun encryptStringsXml(file: File, key: String): String {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc = builder.parse(file)

        val encryptedDoc = builder.newDocument()
        val resources = encryptedDoc.createElement("resources")
        encryptedDoc.appendChild(resources)

        val nodes = doc.getElementsByTagName("string")
        for (i in 0 until nodes.length) {
            val node = nodes.item(i)
            val name = node.attributes.getNamedItem("name").nodeValue
            val value = node.textContent
            val encryptedValue = encryptAes(value, key)

            val stringNode = encryptedDoc.createElement("string")
            stringNode.setAttribute("name", name)
            stringNode.textContent = encryptedValue
            resources.appendChild(stringNode)
        }

        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        val output = StringWriter()
        transformer.transform(DOMSource(encryptedDoc), StreamResult(output))
        return output.toString()
    }

    private fun encryptAes(input: String, key: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key.toByteArray(), "AES"))
        return cipher.doFinal(input.toByteArray()).joinToString("") { "%02x".format(it) }
    }


}
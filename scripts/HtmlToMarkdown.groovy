/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import griffon.plugins.markdown.MarkdownProcessor

import static griffon.util.ConfigUtils.createConfigReader
import static griffon.util.ConfigUtils.getConfigValue
import static griffon.util.ConfigUtils.loadConfig
import static griffon.util.ConfigUtils.loadConfigWithI18n
import static griffon.util.ConfigUtils.stripFilenameExtension

/**
 * Gant script that converts Markdown to HTML.<p>
 *
 * @author Andres Almiray
 */

includeTargets << griffonScript('_GriffonCompile')

target(name: 'htmlToMarkdown', description: 'Converts HTML sources to Markdown',
    prehook: null, posthook: null) {
    depends(compile)
    if (!argsMap.params) {
        event 'StatusError', ['No files were given as parameters']
        exit 1
    }

    def isMarkdown = { File f ->
        f.name.endsWith('.html')
    }

    File destinationDir = new File("${projectTargetDir}/gen-markdown")
    ant.mkdir(dir: destinationDir)

    for (String param : argsMap.params){
        File source = new File(param)
        if (source.directory) {
            source.eachFileRecurse { File f ->
                if (isMarkdown(f)) {
                    processFile(f, source, destinationDir)
                }
            }
            ant.copy(todir: destinationDir) {
                fileset(dir: source, excludes: '**/*.html')
            }
        } else if (isMarkdown(source)) {
            processFile(source, source.parentFile, destinationDir)
        } else {
            event 'StatusError', ["$source does not appear to be a Markdown file"]
        }
    }
}

private processFile(File source, File baseDir, File destinationDir) {
    MarkdownProcessor markdownProcessor = new MarkdownProcessor()
    ConfigObject appConfig = loadConfig('Application' as Class)
    Locale locale = getConfigValue(appConfig, "application.locale", Locale.default)
    appConfig = loadConfigWithI18n(locale, createConfigReader(), 'Config' as Class, 'Config')
    String md = markdownProcessor.htmlToMarkdown(source.text, '', appConfig)
    String relativeFilePath = source.parentFile.absolutePath - baseDir.absolutePath
    File destinationParentDir = new File("${destinationDir}/${relativeFilePath}")
    ant.mkdir(dir: destinationParentDir)
    File target = new File("${destinationParentDir}/${stripFilenameExtension(source.name)}.md")
    target.text = md
    event 'StatusFinal', ["Converted ${source} to ${target}"]
}

setDefaultTarget('htmlToMarkdown')

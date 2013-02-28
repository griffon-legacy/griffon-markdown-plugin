griffon.project.dependency.resolution = {
    inherits "global"
    log "warn"
    repositories {
        griffonHome()
        mavenCentral()
        String basePath = pluginDirPath? "${pluginDirPath}/" : ''
        flatDir name: "markdownLibDir", dirs: ["${basePath}lib"]
    }
    dependencies {
        build   'com.overzealous:remark:0.9.3',
                'org.jsoup:jsoup:1.7.2',
                'org.apache.commons:commons-lang3:3.0.1',
                'org.pegdown:pegdown:1.1.0'

        compile 'com.overzealous:remark:0.9.3',
                'org.jsoup:jsoup:1.7.2',
                'org.apache.commons:commons-lang3:3.0.1',
                'org.pegdown:pegdown:1.1.0'
    }
}

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p %c - %m%n')
    }

    error 'org.codehaus.griffon',
          'org.springframework',
          'org.apache.karaf',
          'groovyx.net'
    warn  'griffon'
}
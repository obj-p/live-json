plugins {
    alias(libs.plugins.intellijPlatform)
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}


dependencies {
    intellijPlatform {
        intellijIdeaCommunity(libs.versions.idea)
    }
}

intellijPlatform {
    buildSearchableOptions = false
    instrumentCode = false

    pluginConfiguration {
        name = "Live JSON"

        ideaVersion {
            /* Disables patching the plugin.xml to avoid the restriction on "until" versions of IDEA. See live-plugin
               for an example:
               https://github.com/dkandalov/live-plugin/blob/c7e372d53d8984e6459e9b9eca189abffcd3abac/build.gradle#L40
               */
            untilBuild = provider {
                null
            }
        }
    }
}

tasks.withType<Wrapper> {
    gradleVersion = properties["gradleVersion"].toString()
}

pluginManagement {
    repositories {
        maven {
            setUrl("https://zdrepo.jfrog.io/artifactory/java-starter-virtual/")
            credentials {
                username = System.getenv()["ARTIFACTORY_USERNAME"]
                password = System.getenv()["ARTIFACTORY_API_KEY"]
            }
        }
        mavenCentral()
        mavenLocal()
    }

    val javastarterPluginsVersion: String by settings

    plugins {
        id("zendesk.java17") version javastarterPluginsVersion
        id("zendesk.app") version javastarterPluginsVersion
        id("zendesk.testing") version javastarterPluginsVersion
        id("zendesk.container.ecr") version javastarterPluginsVersion
    }
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.6.0"
}

rootProject.name = "java-starter-template"

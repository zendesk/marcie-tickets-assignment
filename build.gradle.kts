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

plugins {
    application
    jacoco
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.starterJavaDevelopment)
    alias(libs.plugins.starterApp)
    alias(libs.plugins.starterTesting)
    alias(libs.plugins.starterContainer)
}

dependencies {
    compileOnly(libs.osgi)

    implementation(platform(libs.starterDependencies))
    implementation(libs.bundles.javaStarter)

    testImplementation(libs.bundles.javaStarter)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("com.zendesk.template.ServiceStarter")
}

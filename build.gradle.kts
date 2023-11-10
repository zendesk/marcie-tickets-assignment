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
    id("zendesk.java17")
    id("zendesk.app")
    id("zendesk.container.ecr")
    id("zendesk.testing")
    id("io.freefair.lombok") version "6.2.0"
    //id("zendesk.nitpick")
    id("jacoco")
    id("org.sonarqube") version "4.0.0.2929"
}

sourceSets {
    create("client") {}
}

dependencies {
    val jacksonBomVersion: String by project
    val junitVersion: String by project
    val javastarterVersion: String by project

    constraints {
        implementation("com.fasterxml.jackson:jackson-bom:$jacksonBomVersion")
        testImplementation("org.junit:junit-bom:$junitVersion")
    }

    compileOnly("org.osgi:org.osgi.annotation.bundle:2.0.0")

    implementation(platform("com.zendesk.java-starter:starter-dependencies:$javastarterVersion"))
    implementation("com.zendesk.java-starter:starter-framework:$javastarterVersion")
    implementation("com.zendesk.java-starter:starter-resteasy:$javastarterVersion")
    implementation("com.zendesk.java-starter:starter-http-server:$javastarterVersion")
    implementation("com.zendesk.java-starter:starter-caching-espresso:$javastarterVersion")
    implementation("com.zendesk.java-starter:starter-webclient:$javastarterVersion")

    "clientImplementation"("com.zendesk.java-starter:starter-inject-weld:$javastarterVersion")
    "clientImplementation"(sourceSets["main"].output)

    "testImplementation"(sourceSets["main"].output)
    "testImplementation"(sourceSets["client"].output)

}

tasks.named<Checkstyle>("checkstyleMain") {
    source = fileTree("src/main/java") + fileTree("src/test/java") + fileTree("client/java")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("com.zendesk.marcie.ServiceStarter")
}

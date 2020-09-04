import org.gradle.crypto.checksum.Checksum

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java Library project to get you started.
 * For more details take a look at the Java Libraries chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.5.1/userguide/java_library_plugin.html
 */

plugins {
    // Apply the java-library plugin to add support for Java Library
    `java-library`
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("org.gradle.crypto.checksum") version "1.2.0"
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}
dependencies {
    api(project(":models"))
    implementation("com.google.code.gson:gson:2.8.6")
    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api("org.apache.commons:commons-math3:3.6.1")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:29.0-jre")

    // Use JUnit test framework
    testImplementation("junit:junit:4.13")
}

tasks {
    named<Jar>("jar") {
        manifest {
            attributes(
                    "Package-Title" to "Gradle intro",
                    "Package-Version" to project.version.toString(),
                    "Main-Class" to "com.bendaniel10.gradleintro.Library",
                    "Created-By" to "${System.getProperty("java.version")} (${System.getProperty("java.vendor")} ${System.getProperty("java.vm.version")})"
            )
        }
    }

    named<Jar>("shadowJar") {
        archiveBaseName.set("UberJar")
        archiveClassifier.set("")

        dependencies {
            exclude("**/*META-INF/**")
        }
    }

    register<Checksum>("uberJarChecksum") {
        group = "checksum"
        files = project.files("$buildDir/libs/UberJar.jar")
        algorithm = Checksum.Algorithm.SHA256
        dependsOn(named("shadowJar"))
    }
}
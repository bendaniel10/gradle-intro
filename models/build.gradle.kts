plugins {
    java
}

group = "com.bendaniel10.gradleintro"
version = "1.00"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit", "junit", "4.12")
}

tasks {

    val downloadZipFile = register<Exec>("downloadZipFile") {
        group = "generate"
        executable = "curl"
        args("-s", "-o", "$projectDir/models.zip", "https://nexus3.internal.numberfour.eu/repository/numberfour-snapshots/com/enfore/gradleintro/models-zip/feature-META-16113-update-gradle-wrapper-version-SNAPSHOT/models-zip-feature-META-16113-update-gradle-wrapper-version-20200819.101314-1.zip")
    }

    val unzipModelToBuildDir = register<Copy>("unzipModelToBuildDir") {
        group = "generate"
        from(zipTree(file("$projectDir/models.zip")))
        into("$buildDir/models")
        dependsOn(downloadZipFile)
    }

    val copyModelsIntoResourceFolder = register<Copy>("copyModelsIntoResourceFolder") {
        group = "generate"
        from("$buildDir/models")
        into("$projectDir/src/main/resources")
        dependsOn(unzipModelToBuildDir)
    }

    val removeModelsIntoResourceFolder = register<Delete>("removeModelsIntoResourceFolder") {
        group = "generate"
        delete("$projectDir/src/main/resources/models", "$projectDir/models.zip")
    }

    clean.get().dependsOn(removeModelsIntoResourceFolder)
    processResources.get().dependsOn(copyModelsIntoResourceFolder)
}
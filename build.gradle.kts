var groupId = "com.moskuza";

plugins {
    id("application");
}

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    annotationProcessor("systems.manifold:manifold-preprocessor:2024.1.33")
    testAnnotationProcessor("systems.manifold:manifold-preprocessor:2024.1.33")
}


application {
    mainClass = "com.moskuza.Main";
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "$groupId.Main";
    }
}

if (JavaVersion.current() != JavaVersion.VERSION_1_8 &&
    sourceSets["main"].allJava.files.any { it.name == "module-info.java" }) {
    tasks.withType<JavaCompile> {
        // if you DO define a module-info.java file:
        options.compilerArgs.addAll(listOf("-Xplugin:Manifold", "--module-path", classpath.asPath))
    }
} else {
    tasks.withType<JavaCompile> {
        // If you DO NOT define a module-info.java file:
        options.compilerArgs.add("-Xplugin:Manifold")
    }
}


group = groupId;
var groupId = "com.moskuza";

plugins {
    id("application");
}

application {
    mainClass = "com.moskuza.Main";
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "$groupId.Main";
    }
}

group = groupId;
version = "1.0";
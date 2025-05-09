plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.19.1")
    testImplementation("io.github.bonigarcia:webdrivermanager:6.1.0")
    testImplementation("io.rest-assured:rest-assured:5.2.0")
    testImplementation("io.rest-assured:json-path:5.4.0")

}


tasks.test {
    useJUnitPlatform()
    
    testLogging {
        showStandardStreams = true
    }
}
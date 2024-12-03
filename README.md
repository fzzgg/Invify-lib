Welcome to the INVIFY GUI API! 🚀
‼️ IMPORTANT
🛠️ The library is still in development. If you encounter any issues or have feature requests, feel free to contact me on Discord: fzzgg.

👷 Developer API
How to Integrate INVIFY GUI API into Your Project
Maven Integration
If you're using Maven, add the following to your pom.xml:
xmlCopy<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.fzzgg</groupId>
        <artifactId>Invify-lib</artifactId>
        <version>1.0</version>
    </dependency>
</dependencies>
Gradle Integration
For Gradle projects, include the following in your build.gradle file:
groovyCopy// Add Jitpack repository
repositories {
    maven { url 'https://jitpack.io' }
}

// Add dependency
dependencies {
    implementation 'com.github.fzzgg:Invify-lib:1.0'
}
If you're using Kotlin DSL (build.gradle.kts), use this configuration:
kotlinCopy// Add Jitpack repository
repositories {
    maven("https://jitpack.io")
}

// Add dependency
dependencies {
    implementation("com.github.fzzgg:Invify-lib:1.0")
}
Quick Start Guide

Add the Dependency: Follow the Maven or Gradle instructions above.
Import the Library: In your Java/Kotlin code, import the necessary classes.
Start Using INVIFY GUI API: Begin implementing your project features.

🤝 Contributing

Report issues on the GitHub repository
Contact developer on Discord: fzzgg

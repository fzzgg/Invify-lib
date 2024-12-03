
# Welcome to the INVIFY INVENTORY API! 🚀

InvifyLib is a powerful and feature-rich library designed for developers to create custom GUIs with ease. (In the future :D)

## ⚠️ Important Compatibility Information

The library is only compatible with Minecraft versions ```1.17.1``` to ```1.21```.

It requires ```Java 17``` to run.

### 🛠️ ```The library is still in development. If you encounter any issues or have feature requests, feel free to contact me on Discord: fzzgg.```

## 👷 Developer API

How to Integrate INVIFY INVENTORY API into Your Project

## 🔴 Maven Integration

If you're using Maven, add the following to your pom.xml:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

```

```xml
<dependencies>
    <dependency>
        <groupId>com.github.fzzgg</groupId>
        <artifactId>Invify-lib</artifactId>
        <version>{VERSION}</version>
    </dependency>
</dependencies>

```

## 🟢 Gradle Integration

For Gradle projects, include the following in your build.gradle file:

```
repositories {
    maven { url 'https://jitpack.io' }
}

```
```
dependencies {
    implementation 'com.github.fzzgg:Invify-lib:{VERSION}'
}

```

## 🟣 If you're using Kotlin DSL (build.gradle.kts), use this configuration:

```
repositories {
    maven("https://jitpack.io")
}
```

```
dependencies {
    implementation("com.github.fzzgg:Invify-lib:{VERSION}")
}

```
## 💻 Quick Start Guide

Add the Dependency: Follow the Maven or Gradle instructions above.
Import the Library: In your Java/Kotlin code, import the necessary classes.
Start Using INVIFY INVENTORY API: Begin implementing your project features.

## 🤝 Contributing

Report issues on the GitHub repository
Contact developer on Discord: fzzgg

## 📜 License

This project is licensed under the MIT License. This means that you are free to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the software, as well as to permit persons to whom the software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an action of contract, tort or otherwise, arising from, out of or in connection with the software or the use or other dealings in the software.

Feel free to use this license text in the README file or replace it with a different open-source license that you prefer, such as the Apache License or the GNU General Public License (GPL). The key points are:

Clearly state the license being used (e.g., MIT License)
Explain that users are free to use, copy, modify, and distribute the software
Include the standard disclaimer about the software being provided "as is" without warranty

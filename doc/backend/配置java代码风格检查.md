# 配置 Java 代码风格检查和格式化插件

选用一款自动化的代码风格检查工具可以帮助我们写出规范且风格一致的代码。最好搭配使用自动化的格式化插件，在保存代码的同时自动格式化代码。

我们选用 [CheckStyle](https://checkstyle.org/) 工具，使用 [谷歌代码风格](https://checkstyle.org/google_style.html) 。

## 安装代码风格检查插件

虽然 CheckStyle 提供 [命令行工具](https://checkstyle.org/cmdline.html) ，但还是使用插件更为方便。CheckStyle 官方提供多款常用 IDE 插件：

- [Eclipse IDE](https://checkstyle.org/eclipse.html)

- [NetBeans IDE](https://checkstyle.org/netbeans.html)

- [IntelliJ IDE](https://checkstyle.org/idea.html)

如果你使用 `vscode`，可以在 `vscode `插件市场中找到 [Checkstyle for Java](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle) 插件，安装后再在 vscode 中按 `Ctrl + Shift + P` 打开命令面板执行 `Checkstyle: Set Checkstyle Configuration File` 命令，在随后出现的列表中选择 `/google_checks.xml` 。

## 安装谷歌代码格式化工具

既然选择了谷歌代码风格，自然要搭配使用 [谷歌代码格式化工具](https://github.com/google/google-java-format)。常用IDE都有相应的插件，安装即可使用。

vscode 比较麻烦，因为插件市场中的2款谷歌代码格式化插件都不好用。我们可以使用一款名为 [Run on Save](https://marketplace.visualstudio.com/items?itemName=emeraldwalk.RunOnSave) 的插件在保存代码后直接执行谷歌代码格式化工具的命令行程序格式化代码。具体操作步骤为：

1. 下载 [谷歌代码格式化工具命令行程序](https://github.com/google/google-java-format/releases)。该程序是 jar 包的形式，例如最新版本为 1.8 的 jar 包名为 [google-java-format-1.8-all-deps.jar](https://github.com/google/google-java-format/releases/download/google-java-format-1.8/google-java-format-1.8-all-deps.jar)。假设下载到本地后保存到 `c:\google-java-format\google-java-format-1.8-all-deps.jar`

2. 在 vscode 中安装 [Run on Save](https://marketplace.visualstudio.com/items?itemName=emeraldwalk.RunOnSave) 插件。安装成功后在 vscode 的`设置`中搜索 `runonsave`，点击 `在 settings.config 中编辑`，并添加配置如下:
   
   ```json
   "emeraldwalk.runonsave": {
       "commands": [
         {
           "match": "\\.java$",
           "cmd": "java -jar C:\\google-java-format\\google-java-format-1.8-all-deps.jar --replace ${file}"
         }
       ]
     },
   ```

3. 在 vscode 的`设置`中搜索 `java format`，将 `Java > Format: Enabled` 和 `Java > Format > OnType: Enabled` 选项禁用。



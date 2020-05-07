# 使用vscode调试docker中的java代码

## 安装 docker

见 【win10 下安装 docker】

## 安装、配置 VSCode

## 安装 vscode （略）

### 在 vscode 中安装 Remote Development 插件

在 vscode 扩展商店中搜索 `Remote Development` 并安装。

## 在 vscode 中调试 backend java项目

1. 停止 `atjob`下的所有容器。

2. 打开 vscode，按 `F1` 或 `Ctrl + Shift + P`，搜索并选择 `Remote-Containers: Open Folder in Container...`，然后选择选择文件夹 `atjob`，接着选择 `from docker-compose.deploy.yml`，再选择 `backend` 服务。vscode将会重新加载并显示容器启动进度条，启动完成后 vscode 将加载 atjob 目录。因为要调试后端代码，所以要在 vscode 中使用菜单 `文件 | 打开...`，然后选择 `app`，打开 `app` 文件夹。

3. 点击 vscode 左侧活动栏的 运行(Ctrl + Shift + D) 图标，点击 `创建 launch.json 文件` 链接，然后选择 `java`。如果没有 `java `选项，在插件商店的 `JU-已安装` 栏内找到 `Debugger for Java` 和 `Language support for Java`插件，点击 `Install in Dev Container:Existing` 按钮安装。安装完毕后点击 `需重新加载` 按钮重新加载。之后就可以像在本地一样设置断点然后调试程序了。
   
   **注意**：atjob 目录的整个路径不要出现中文和空格，否则会报 找不到 mainclass 的错误。

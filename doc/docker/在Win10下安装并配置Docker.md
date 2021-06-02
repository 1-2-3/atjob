# 在 Win10 下安装并配置 Docker

1. 下载 [Docker Desktop for Windows](https://hub.docker.com/editions/community/docker-ce-desktop-windows/) 安装程序，双击安装。

2. 配置镜像源，否则下载镜像会非常慢。

   - **获取阿里镜像源地址**。登录 [阿里云开发平台](https://promotion.aliyun.com/ntms/act/kubernetes.html#industry) ，点击“镜像搜索”按钮，再点击左侧菜单中的“镜像加速器”，复制加速器地址。

   - **添加镜像源地址**。在 docker 任务栏图标上右击，选“settings”，再点击“Docker Engine”菜单，将上一步得到的镜像源地址添加到 "registry-mirrors" 配置中，例如：

     ```json
     {
       "registry-mirrors": ["https://g3wyj8mq.mirror.aliyuncs.com"],
       "insecure-registries": [],
       "debug": true,
       "experimental": false
     }
     ```

     **\* 之后一定要重启 docker！！**

3. (可选) 启用 `Docker settings|General|Use the WSL 2 based engine`，容器启动会更快。
4. (可选) 限制 WSL2 的资源使用。创建配置文件 `C:\Users\<yourUserName>\.wslconfig`，内容如下：
   ```config
   [wsl2]
   memory=4GB # Limits VM memory in WSL 2 to 4 GB
   processors=2 # Makes the WSL 2 VM use two virtual processors
   ```
   详细参数说明参考：https://docs.microsoft.com/en-us/windows/wsl/wsl-config#configure-global-options-with-wslconfig

#
# 输出项目 classpath 并启动 jshell
#

# 将项目依赖的库输出到 classpath.txt 中
mvn dependency:build-classpath -DincludeTypes=jar -D"mdep.outputFile=classpath.txt"

# 启动 jshell
jshell --class-path $(cat .\classpath.txt)
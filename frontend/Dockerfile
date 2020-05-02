# Stage 0, "build-stage", based on Node.js, to build and compile the frontend
FROM node:current-alpine as build-stage

WORKDIR /app

COPY package*.json /app/

# 如果已存在 node_modules 则可以注释下面的安装依赖包的命令以节省时间和流量
# RUN npm install --registry https://registry.npm.taobao.org

COPY ./ /app/

RUN npm run build

# Stage 1, based on Nginx, to have only the compiled app, ready for production with Nginx
FROM nginx:1.18.0

COPY --from=build-stage /app/dist/ /usr/share/nginx/html

COPY ./_nginx/nginx.conf /etc/nginx/nginx.conf
COPY ./_nginx/default.conf /etc/nginx/conf.d/default.conf
# 苍穹外卖开发笔记
## Day 1
### 前后端联调部分
nginx需要在全英文目录运行。  
如果自行下载nginx，务必将conf文件更换为项目提供的文件，否则会出现前后端无法联动的情况。并且将后端的端口（见application.yml）与conf文件中`upstream webservers`的一致。
### nginx优点
+ 提高访问速度
+ 负载均衡：大量请求均衡分配给集群中每台后端服务器
+ 保证后端服务安全（nginx走内网）
### nginx配置方式
```
# 反向代理,处理管理端发送的请求
        location /api/ {
			proxy_pass   http://localhost:8080/admin/;
            #proxy_pass   http://webservers/admin/;
        }
```
### 负载均衡配置
```aiignore
upstream webservers{
	  server 192.168.1.1:8080
	  server 192.168.1.2:8080
	  # 代表此处两台后端服务器
	}

    server {
        listen       80;
        server_name  localhost;
        
        location /api/{
            proxy_pass http://webservers/admin
        }
```
## Day 2
### 项目约定
+ 管理端发出的请求，统一使用`/admin`作为前缀
+ 用户端发出的请求，统一使用`/user`作为前缀
### 

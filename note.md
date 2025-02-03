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
### Redis
redis不是替代MySQL而是补充，用于缓存数据，提高访问速度。key-value存储，支持多种数据结构。   
五种数据类型：String、List、Set、Hash、ZSet：
+ String：字符串，最简单的类型
+ List：列表，按照插入顺序排序，可以重复，类似Java中的List
+ Set：集合，无序，不重复，类似Java中的Set，可进行交集、并集、差集等操作
+ Hash：哈希，散列，类似Java中的HashMap
+ ZSet：有序不重复集合，每个元素都会关联一个double类型的分数，根据分数排序（存排行榜）
### Redis常用命令
+ 字符串命令
  + set key value  设置key-value
  + get key  获取key对应的value
  + SETNX key value  若key不存在则设置key-value
  + SETEX key seconds value  设置key-value并设置过期时间

# 库存管理系统
## 1.项目说明：
这只是一个本人java的小作业（包论文），纯java脚本，运行后是在控制台显示信息，是一个库存管理系统，能对物品进行增删改查操作，有管理员和普通用户两种身份。
登录的用户名和密码（也可以在配置好的数据库里面进行修改）：
![image](https://github.com/user-attachments/assets/f69cc0ca-b1e9-489b-b4ac-6c663c721b62)

## 2.运行项目：
1.项目根目录的mysql.sql脚本拿去mysql或者navicat里跑一下。
2.整个项目放在idea里面配置好java环境打开InventoryManagement.java。
3.第七行：private static final String JDBC_USERNAME = "root"; 引号里面改成自己数据库的用户名。
4.第八行：private static final String JDBC_PASSWORD = "root"; 引号里面改成自己数据库的密码。
如下：
![image](https://github.com/user-attachments/assets/9d966ec2-f01d-4af9-844f-ba3e87dc7a24)
5.鼠标右键点击运行该脚本即可在控制台看到效果。

## 3.效果展示（登录）：（增删改查功能都有，代码有注释，逻辑都能看得懂）
管理员登录显示：

![image](https://github.com/user-attachments/assets/040255a7-8474-4036-967b-0c65d05ae05d)

普通用户登录显示：

![image](https://github.com/user-attachments/assets/fddee567-4074-455d-9615-723911b7ada8)

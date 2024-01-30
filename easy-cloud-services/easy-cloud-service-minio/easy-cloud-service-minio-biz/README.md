# minio
## 安装部署
[Minio安装文档](https://www.minio.org.cn/docs/minio/linux/operations/install-deploy-manage/deploy-minio-single-node-single-drive.html)

* 下载MinIO可执行文件：
```
wget https://dl.min.io/server/minio/release/linux-amd64/minio
```
* 添加执行权限给MinIO文件：
```
chmod +x minio
```
* 创建一个目录用于存储MinIO的数据和配置：
```
mkdir -p /data/minio/{data,config,client,logs}
```
* 运行MinIO服务器：
```
nohup ./minio server /data/minio/data --config-dir /data/minio/config --address ":9000" --console-address ":9001" > /opt/minio/logs/minio.log 2>&1 &
```
在上面的命令中，--address ":9000"指定MinIO服务器监听的地址和端口，--console-address ":9001"指定MinIO管理控制台监听的地址和端口。请根据实际需求调整端口号。

注意：确保选择的端口没有被其他服务占用。

如果您希望MinIO作为后台服务运行，可以使用如systemd等工具进行管理。以下是一个基本的systemd服务文件示例：
```
[Unit]
Description=MinIO
Documentation=https://min.io
Wants=network-online.target
After=network-online.target
 
[Service]
WorkingDirectory=/data/minio
ExecStart=/path/to/minio server /data/minio/data --config-dir /data/minio/config --address ":9000" --console-address ":9001" > /opt/minio/logs/minio.log 2>&1 &
Restart=on-failure
RestartSec=5
 
[Install]
WantedBy=multi-user.target
```
将上面的内容保存为/etc/systemd/system/minio.service，然后使用以下命令启动并使MinIO服务随系统启动：

sudo systemctl daemon-reload
sudo systemctl enable minio
sudo systemctl start minio

现在MinIO已经在Linux上部署并运行了。您可以通过浏览器访问http://<your-server-ip>:9001来使用MinIO控制台。


## yml 配置
```yml
minio:
  endpoint: http://xxxx:9000
  accessKey: xxxx
  secretKey: xxxxxxx
```
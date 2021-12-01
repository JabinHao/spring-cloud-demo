# -*- coding: utf-8 -*-
"""
Created on Sat Nov 27 15:10:53 2021

@author: hjp
"""

from collections import namedtuple
import json
import re

import pymysql
import requests
from requests.api import patch

from generate_info import ComplexEncoder, FakeUsers


class MysqlMybatis:

    def __init__(self, host='localhost', port = 3306, username='root', password='123456', db='test'):
        self.host = host
        self.username = username
        self.password = password
        self.db = db
        self.port = port
        self.connection = pymysql.connect(
            host = host,
            port = port,
            user=username,
            password=password,
            db=db)

        self.faker = FakeUsers()

    def insert_users(self, n = 1):

        cursor = self.connection.cursor()
        sql = "INSERT INTO `User` (`name`, `uid`, `auth_level`, `game_platform`, `ctime`, `mtime`)" + \
                "VALUES (%s, %s, %s, %s, %s, %s)"
        val = self.faker.get_users(n = n, ctime=True, mtime=True)

        try:
            cursor.executemany(sql, val)
            self.connection.commit()
            print("insert successfully")
        except:
            print("failed")
            self.connection.rollback()
        finally:
            self.connection.close()
            
    def insert_user_info(self, n = 1):
        
        cursor = self.connection.cursor()
        sql = "INSERT INTO `user_info` (`uid`, `gender`, `name`, `ctime`, `mtime`)" + \
                "VALUES (%s, %s, %s, %s, %s)"
        val = self.faker.get_user_info(n)

        try:
            cursor.executemany(sql, val)
            self.connection.commit()
            print("insert successfully")
        except:
            print("failed")
            self.connection.rollback()
        finally:
            self.connection.close()


class HttpMybatis:

    def __init__(self, host, port, base_path):
        self.host = host
        self.port = port
        self.base_path = base_path
        self.base_url = 'http://{}:{}{}'.format(host, port, base_path)
        self.faker = FakeUsers()


    def getAll(self, path = "/", params={}):

        url = self.base_url + path
        return requests.get(url = url, params = params)

    def save_users(self, path = "/"):
        users = self.faker.get_users(2)
        url = self.base_url + path
        headers = {'Content-Type': 'application/json'}

        for user in users:
            resp = requests.post(url = url, data = json.dumps(user._asdict()), headers=headers)
            print(resp.status_code)
    
    def save_custom_info(self, path, info):

        url = self.base_url + path
        headers = {'Content-Type': 'application/json'}

        if type(info) != list:
            info_dict = info._asdict()
            address = [addr._asdict() for addr in info_dict['address']]
            orders = [order._asdict() for order in info_dict['orders']]
            info_dict['address'] = address
            info_dict['orders'] = orders

            data = json.dumps(info_dict, cls=ComplexEncoder, ensure_ascii=False)
            resp = requests.post(url=url, data=data.encode('utf8'), headers=headers)
            print(resp.status_code, " data = ", data)
            return resp.status_code

        for data in info:
            data_dict = data._asdict()
            address = [addr._asdict() for addr in data_dict['address']]
            data_dict['address'] = address
            data = json.dumps(data_dict, cls=ComplexEncoder, ensure_ascii=False)
            resp = requests.post(url=url, data=data.encode('utf8'), headers=headers)
            print(resp.status_code, " data = ", data) 


def save_orders():
    host = 'localhost'
    port = 8081
    base_path = '/order'
    path = '/save'
    base_url = 'http://{}:{}{}'.format(host, port, base_path)
    headers = {'Content-Type': 'application/json'}
    fake = FakeUsers()

    # 获取 address 信息
    addresses = []
    resp = requests.get("http://localhost:8082/user/get")
    if resp.status_code == 200:
        users = resp.json()['data']
        for user in users:
            addresses.extend(user['address'])
    else:
        print(resp.status_code)
        return

    Info = namedtuple('info', ['uid', 'address'])
    Address = namedtuple(
        'address', ['uid', 'name', 'phone', 'region', 'detail', 'ctime', 'mtime'])
    
    # 生成对应的 order
    orders = [fake.get_order_with_uid_address_info(Info(address['uid'], Address(**address))) for address in addresses]


    # 保存订单信息
    for order in orders:
        data = json.dumps(order._asdict(), cls=ComplexEncoder, ensure_ascii=False).encode("utf8")
        resp = requests.post(url=base_url+path, data=data, headers=headers)
        print(resp.status_code, "order: ", order)
    return



fake = FakeUsers()
# http_client = HttpMybatis('localhost', 8082, '/user')

# http_client.save_users("/save")


# resp = http_client.getAll("/get")
# # print(resp.json())
# for user in resp.json():
#     print(user)

''' 
spring-cloud-user-service
'''
# 创建客户端
# sql_client_user = MysqlMybatis('114.214.219.193', 3309, 'root', 'pvkYQN2yzVWZwGqcTzkb', 'spring_cloud')
# 插入数据
# sql_client_user.insert_user_info(2)

# http_client_user = HttpMybatis('localhost', 8082, '/user')
# resp = http_client_user.getAll('/get/201805060444380000175200')
# print("code:", resp.status_code)
# for user in resp.json():
#     print(user)
    
# print(json.loads(resp.text)['data']['uid'])

# resp = http_client_user.getAll('/get')
# print("code:", resp.status_code)
# users = resp.json()['data']
# for user in users:
#     print(user)
    
# print(json.loads(resp.text)['data']['uid'])

http_client = HttpMybatis('localhost', 8082, '/user')

# 插入数据
# info = fake.get_user_and_address_info()
infos = fake.get_user_and_address_and_order_info(3, 1, 2)
for info in infos:
    code = http_client.save_custom_info("/save", info)
    print('\n\n')
    # print(code,'\n\n')

# 查询所有
# infos = http_client.getAll("/get")
# # print(*infos.json()['data'], sep='\n')
# if infos.status_code == 200:
#     users = infos.json()['data']
#     for user in users:
#         print(*user['address'], sep='\n')
#         print(type(user['address'][0]))
# else:
#     print(infos.status_code)

# save_orders()

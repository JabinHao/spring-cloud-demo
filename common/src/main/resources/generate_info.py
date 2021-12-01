# -*- coding: utf-8 -*-
"""
Created on Sat Nov 27 11:44:44 2021

@author: hjp
"""

import collections
from datetime import *
from random import choice, randint
import re

from faker import Faker
import json
import random


class ComplexEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, datetime):
            return obj.isoformat()
        elif isinstance(obj, date):
            return obj.isoformat()
        else:
            return json.JSONEncoder.default(self, obj)

class FakeUsers:

    def __init__(self, lan = 'en_US'):
        self.lan = lan
        self.fake = Faker(lan)
        self.platform_list = ['bili', 'ios', 'huawei', 'oppo', 'vivo', 'xiaomi', '4399',
                'one plus', '应用宝', '网易yofun', '网易mumu', '努比亚', '魅族']

    def get_name(self, n = 1, first_name = True, last_name = True):
        """
        generate given number of unique first name
        """
        if first_name and last_name:
            if n == 1:
                return self.fake.name();
            else:
                names = set(self.fake.unique.name() for _ in range(n))
                return names
        elif first_name:
            if n == 1:
                return self.fake.first_name();
            else:
                names = set(self.fake.unique.first_name() for _ in range(n))
                return names
        else:
            if n == 1:
                return self.fake.last_name();
            else:
                names = set(self.fake.unique.last_name() for _ in range(n))
                return names

    def get_uid(self, n = 1, time = "-5y"):

        """
        generate given number of uid, uid format: date + time + random 10 digit
        param:
            n: number of uid to generate
            time: datetime to generate uid, dafault to '-5y', which means past five years
        """
        if n == 1:
            prefix = self.fake.past_datetime(time).strftime('%Y%m%d%H%M%S')
            suffix = "{:0>10}".format(''.join(str(choice(range(10))) for _ in range(choice(range(5,10)))))
            return prefix + suffix

        prefixes = set(self.fake.unique.past_datetime(time).strftime('%Y%m%d%H%M%S') for _ in range(n))
        uid = []
        for prefix in prefixes:
            suffix = "{:0>10}".format(''.join(str(choice(range(10))) for _ in range(choice(range(5,10)))))
            uid.append(prefix + suffix)

        return uid

    def get_level(self, n = 1, min = 0, max = 120, unique = False):
        '''
        generate given number of levels, include the endpoint
        '''
        if n == 1:
            return randint(min, max)
        if unique:
            return set(self.fake.unique.random_int(min, max) for _ in range(n))
        return [self.fake.random_int(min, max) for _ in range(n)]

    def get_age(self, n =1, min = 1, max = 100, unique = False):
        '''
        generate given number of ages, include the endpoint
        '''
        return self.get_level(n, min, max, unique)

    def get_platform(self, n = 1, platform_list = None):
        if platform_list == None:
            platform_list = self.platform_list

        if n == 1:
            return self.fake.random_element(elements = platform_list)
        return self.fake.random_elements(elements = platform_list, length = n)

    def get_ctime(self, n = 1, start = '-5y', end = 'now', unique = False):

        if n ==1 :
            return self.fake.date_time_between_dates(start, end)
        if unique:
            return set(self.fake.unique.date_time_between_dates(start, end) for _ in range(n))
        return [self.fake.unique.date_time_between_dates(start, end) for _ in range(n)];

    def get_ctime_and_mtime(self, n = 1, start = '-5y', end = 'now', mend = 'now', unique = False):
        '''
        mtime will after ctime
        param:
            start: satrt of ctime
            end: end of ctime
            mend: end of mtime, must after end
        '''
        if n ==1 :
            ctime = self.fake.date_time_between_dates(start, end)
            mtime = self.fake.date_time_between_dates(ctime, mend)
            return (ctime, mtime)
        if unique:
            ctimes = set(self.fake.unique.date_time_between_dates(start, end) for _ in range(n))
            return set((ctime, self.fake.date_time_between_dates(ctime, mend)) for ctime in ctimes)

        ctimes = [self.fake.unique.date_time_between_dates(start, end) for _ in range(n)]
        return [(ctime, self.fake.unique.date_time_between_dates(ctime, mend)) for ctime in ctimes];

    def get_format_ctime(self, n = 1, format = '%Y-%m-%d %H:%M:%S', start = '-5y', end = 'now', unique = False):
        '''
        generate string type ctime with specified format
        param:
            format: format of string, default style '2021-09-10 21:30:13'
        '''
        ctimes = self.get_ctime(n, start, end, unique)
        if n == 1:
            return ctimes.strftime(format)

        return [ctime.strftime(format) for ctime in ctimes]

    def get_format_ctime_and_mtime(self, n = 1, format = '%Y-%m-%d %H:%M:%S', start = '-5y', end = 'now', mend = 'now', unique = False):
        '''
        generate string type ctime with specified format
        param:
            format: format of string, default style '2021-09-10 21:30:13'
        '''
        # ctimes = self.get_ctime(n, start, end, unique)
        # if type(ctimes) == datetime:
        #     return ctimes.strftime(format)

        # return [ctime.strftime(format) for ctime in ctimes]

        times = self.get_ctime_and_mtime(n, start, end, mend, unique)

        if n ==1 :
            return (times[0].strftime(format), times[1].strftime(format))
        return [(time[0].strftime(format), time[1].strftime(format)) for time in times];

    def get_users(self, n=1, id=False, uid=True, name=True, auth_level=True, game_platform=True, ctime=False, mtime=False):

        if n == 1:
            return (self.get_uid(), self.get_name(), self.get_level(), self.get_platform())

        names = list(self.get_name(n))
        uids = list(self.get_uid(n))
        levels = list(self.get_level(n))
        platforms = self.get_platform(n)

        if ctime and mtime:
            times = self.get_ctime_and_mtime(n)
            User = collections.namedtuple('user', ['name', 'uid', 'level', 'platform', 'ctime', 'mtime'])
            return [User(names[i], uids[i], levels[i], platforms[i], times[i][0], times[i][1]) for i in range(n)]

        User = collections.namedtuple('user', ['name', 'uid', 'auth_level', 'game_platform'])
        return [User(names[i], uids[i], levels[i], platforms[i]) for i in range(n)]

    def get_user_name(self, n = 1, unique = False):
        
        if n == 1:
            return self.fake.user_name()
        if unique:
            return set(self.fake.unique.user_name() for _ in range(n))
        return [self.fake.user_name() for _ in range(n)]
    
    def get_phone_number(self, n = 1, unique = True):
        
        fake = Faker("zh_CN")
        if n == 1:
            return fake.phone_number()
        if unique:
            return set(fake.phone_number() for _ in range(n))
        return [fake.phone_number() for _ in range(n)]
        
    def get_gender(self, n = 1):
        
        elements=('man', 'woman')
        if n == 1:
            return self.fake.random_element(elements)
        return [self.fake.random_element(elements) for _ in range(n)]
    def get_address(self, n = 1, unique = True):
        
        fake = Faker("zh_CN")
        if n == 1:
            address = fake.address()
            index = address.rfind("县") if address.rfind("县") != -1 else address.rfind("市")
            return (address[0:index+1], address[index+1:])
        if unique:
            addresses = set(fake.address() for _ in range(n))
            res = []
            for address in addresses:
                index = address.rfind("县") if address.rfind("县") != -1 else address.rfind("市")
                res.append((address[0:index+1], address[index+1:]))
            return res
        addresses = [fake.address() for _ in range(n)]
        res = []
        for address in addresses:
            index = address.rfind("县") if address.rfind("县") != -1 else address.rfind("市")
            res.append((address[0:index+1], address[index+1:]))
        return res
    
    def get_full_address(self, n = 1, unique = True):
        Address = collections.namedtuple('address', ['name', 'phone', 'region', 'detail', 'ctime', 'mtime'])
        
        local_fake = FakeUsers('zh_CN');
        
        if n == 1:
            address = self.get_address()
            time = self.get_ctime_and_mtime()
            return [Address(local_fake.get_name(), self.get_phone_number(), address[0], address[1], time[0], time[1])]
        res = []
        names = list(local_fake.get_name(n))
        phones = list(self.get_phone_number(n))
        address = self.get_address(n)
        time = self.get_ctime_and_mtime(n)
        for i in range(n):
            res.append(Address(names[i], phones[i], address[i][0], address[i][1], time[i][0], time[i][1]))
        return res
    
    def get_user_info(self, n = 1):
        
        User = collections.namedtuple('user', ['uid', 'gender', 'name', 'ctime', 'mtime'])
        if n==1:
            times = self.get_ctime_and_mtime()
            return User(self.get_uid(), self.get_gender(), self.get_user_name(), times[0], times[1])
        
        uids = list(self.get_uid(n))
        genders = list(self.get_gender(n))
        names = list(self.get_user_name(n))
        times = self.get_ctime_and_mtime(n)
        return [User(uids[i], genders[i], names[i], times[i][0], times[i][1]) for i in range(n)]
    
    def get_user_and_address_info(self, n = 1, an = 1):
        '''
        Parameters
        ----------
        n : TYPE, int
            DESCRIPTION. The default is 1.
        an : TYPE, int
            DESCRIPTION. 生成用户及收货地址信息，默认1

        Returns
        -------
        Users.

        '''
        User = collections.namedtuple('user', ['uid', 'gender', 'name', 'address','ctime', 'mtime'])
        Address = collections.namedtuple('address', ['uid', 'name', 'phone', 'region', 'detail', 'ctime', 'mtime'])

        # an = an if an == 1 else self.fake.random_int(1,an)
        m = n if n != 1 else n + 1
        users = self.get_user_info(m)
        raw_addresses = [self.get_full_address(
            an if an == 1 else self.fake.random_int(1, an)) for _ in range(m)]
        addresses = []
        for i in range(m):
            address = [Address(users[i].uid, raw_address.name,
                               raw_address.phone, raw_address.region, raw_address.detail, raw_address.ctime, raw_address.mtime) for raw_address in raw_addresses[i]]
            addresses.append(address)

        res = []
        for i in range(m):
            info = User(users[i].uid, users[i].gender, users[i].name, addresses[i], users[i].ctime, users[i].mtime)
            res.append(info)

        return res if m == n else res[0]
        
        
    def get_cpdm_time(self, n = 1, start = '-5y', end = 'now', psep = 5, dsep = 24):
        '''

        Parameters
        ----------
        n : int, optional
            数量. The default is 1.
        start : str, optional
            开始时间. The default is '-5y'.
        end : str, optional
            截止时间. The default is 'now'.
        psep : int, optional
            付款间隔，默认10分钟内
        dsep: int, optional
            发货时间，小时

        Returns
        -------
        tuple
            (ctime, ptime, dtime, mtime).
            ptime: pay time 下单时间
            dtime: deliver time 发货时间
        '''
        CPDM = collections.namedtuple('cpdm', ['ctime', 'ptime', 'dtime', 'mtime'])
        if n ==1 :
            ctime = self.fake.date_time_between_dates(start, end)
            ptime = ctime + timedelta(minutes=self.fake.random_int(1, psep))
            dtime = ctime + timedelta(hours=self.fake.random_int(2, dsep))
            return CPDM(ctime, ptime, dtime, ctime)

        ctimes = [self.fake.unique.date_time_between_dates(start, end) for _ in range(n)]
        return [CPDM(ctime, ctime + timedelta(minutes=self.fake.random_int(1, psep), seconds=self.fake.random_int(1, 59)), ctime + timedelta(hours=self.fake.random_int(2, dsep), seconds=self.fake.random_int(1, 59)), ctime) for ctime in ctimes]
        
        
    def get_id_by_time(self, time = datetime.now()):
        
        print(type(time))
        return time.strftime('%Y%m%d%H%M%S%f')
        
        
    def get_order(self, n = 1):
        
        Order = collections.namedtuple('order', ['uid', 'count', 'order_id', 'trade_id', 'product_id', 'total_price', 'address', 'ctime', 'ptime', 'dtime', 'mtime'])
        
        if n == 1:
            flag = True
            n = 2

        uids = self.get_uid(n)
        nums = []
        for i in range(1, 7):
            for j in range(7 - i):
                nums.append(i)
        counts = [self.fake.random_element(nums) for _ in range(n)]
        cpdms = self.get_cpdm_time(n)
        order_ids = [cpdm.ctime.strftime('%Y%m%d%H%M%S%f')[:-6]+str(datetime.now().microsecond) for cpdm in cpdms]
        trade_ids = [cpdm.ptime.strftime('%Y%m%d%H%M%S%f')[:-6]+str(datetime.now().microsecond) for cpdm in cpdms]
        product_ids = [self.fake.ean(13) for _ in range(n)]
        total_prices = ["{:.2f}".format(50 * random.paretovariate(1)) for i in range(n)]
        addresses = ["name:{}, phone:{}, region:{}, detail:{}, ctime:{}, mtime:{}".format(address.name, address.phone, address.region, address.detail, address.ctime, address.mtime) for address in self.get_full_address(n)]
        orders = [Order(uids[i], counts[i], order_ids[i], trade_ids[i], product_ids[i], total_prices[i], addresses[i], cpdms[i][0], cpdms[i][1], cpdms[i][2], cpdms[i][3]) for i in range(n)]
        
        if flag:
            return orders[0]
        return orders

    def get_order_with_uid(self, uid):

        if isinstance(uid, str) or isinstance(uid, int):
            return self.get_order()._replace(uid=uid)
        elif isinstance(uid, list):
            return [self.get_order()._replace(uid=id) for id in uid]
        else:
            print("TypeError, uid must be str or  str list")

    def strfaddress(self, address):
        return "name:{}, phone:{}, region:{}, detail:{}, ctime:{}, mtime:{}".format(
            address.name, address.phone, address.region, address.detail, address.ctime, address.mtime)

    def get_order_with_uid_address_info(self, user_address_info, n=1):

        if isinstance(user_address_info, tuple):
            if isinstance(user_address_info.address, list):
                addresses = [self.strfaddress(address) for address in user_address_info.address]
                res = []
                for address in addresses:
                    orders = [self.get_order()._replace(uid=user_address_info.uid, address=address) for _ in range(self.fake.random_int(1, n))]
                    res.extend(orders)

                return res
            else:
                address = self.strfaddress(user_address_info.address)
                return self.get_order()._replace(uid=user_address_info.uid, address=address)
            
        elif isinstance(user_address_info, list):
            res = []
            for uai in user_address_info:
                if isinstance(uai.address, list):
                    for i in range(self.fake.random_int(1, n)):
                        res.append([self.get_order()._replace(
                        uid=uai.uid, address=self.strfaddress(address)) for address in uai.address])
                else:
                    res.append([self.get_order()._replace(
                        uid=uai.uid, address=self.strfaddress(uai.address))])
            return res
        else:
            print("TypeError, user_address_info must be namedtuple or  namedtuple list")

    def get_user_and_address_and_order_info(self, n= 1, an = 1, on = 1):

        User = collections.namedtuple(
            'user', ['uid', 'gender', 'name', 'address', 'orders', 'ctime', 'mtime'])
        flag = False
        if n == 1:
            flag = True
            n = 2

        res = []
        user_address_info = self.get_user_and_address_info(n, an)
        for user in user_address_info:
            orders = self.get_order_with_uid_address_info(user, on)
            info = User(user.uid, user.gender, user.name, user.address,
                    orders, user.ctime, user.mtime)
            res.append(info)
        # res = [User(user.uid, user.gender, user.name, user.address,
        #             [self.get_order_with_uid_address_info(user, an) for _ in range(n)], user.ctime, user.mtime) for user in user_address_info]

        if flag:
            return res[0]
        return res



faker = FakeUsers()
# fake = Faker("zh_CN")
# print(fake.phone_number())
# print(fake.simple_profile())

# address = fake.address()
# print(address)
# index = address.rfind("县")
# if index == -1:
#     index = address.rfind("市")
# print(index)
# print(address[0:index+1], address[index+1:-1], sep = ' ')

# faker.get_ctime_and_mtime()
# print(faker.get_format_ctime_and_mtime())
# print(faker.get_address(5))
# print(faker.get_user_info(1))

fake = Faker()
# fake.date()

# print(faker.get_full_address(20))

# print(faker.get_cpdm_time())
# print(faker.get_id_by_time(datetime.now()))
# addresses = faker.get_full_address(5)
# print(addresses)
# str_addr = ["name:{}, phone:{}, region:{}, detail:{}, ctime:{}, mtime:{}".format(address.name, address.phone, address.region, address.detail, address.ctime, address.mtime) for address in addresses]

# print(faker.get_order(1))
# cpdms = [faker.get_cpdm_time() for i in range(1)]
# print(type(cpdms[0].ctime))
# faker.get_id_by_time(cpdms[0].ctime)
# print(cpdms[0].ctime.strftime('%Y%m%d%H%M%S%f'))
# print(faker.get_id_by_time())
# ids = [faker.get_id_by_time(cpdm.ctime) for cpdm in cpdms]
# print(ids)

# print(fake.past_datetime('-2m').strftime('%Y%m%d%H%M%S%f'))

# print(*faker.get_user_and_address_info(2), sep='\n')


# print(type(faker.get_user_and_address_info(2)) == list)

# print(*faker.get_order_with_uid(faker.get_uid(5)), sep='\n')
# print(faker.get_order())

# print(faker.get_user_and_address_and_order_info())

# print(json.dumps(faker.get_order()._asdict(), cls=ComplexEncoder, ensure_ascii=False))

# print(faker.get_user_info()._replace(uid='1102', gender='male'))

# uai = faker.get_user_and_address_info()
# oua = faker.get_order_with_uid_address_info(uai)
# print(uai)
# print(*oua, sep='\n')

# print(type(faker.get_user_and_address_info()))
# print(isinstance(faker.get_user_and_address_info(), collections.namedtuple))

# address = {'uid': '201903301815360000266966', 'name': '彭雷', 'phone': '15708814300', 'region': '山东省波市',
#     'detail': '和平王路Q座 395622', 'ctime': '2016-12-05T14:57:39.000+00:00', 'mtime': '2017-11-24T20:11:10.000+00:00'}
# Address = collections.namedtuple(
#     'address', ['uid', 'name', 'phone', 'region', 'detail', 'ctime', 'mtime'])

# format_address = Address(**address)
# format_address._replace(ctime=datetime.fromisoformat(
#     address['ctime']), mtime=datetime.fromisoformat(format_address.mtime))
# print(format_address)
# users = ["lili", "epal"]
# test = []

# test.extend(users)
# print(test)

#coding=UTF-8
'''
Created on 2017年2月1日
将redis上的数据同步到mariaDB上
@author: aring
'''
import sys
import MySQLdb
import time
import redis
from mysqlDB import cursor
from _sqlite3 import Row
from datetime import date

class DataSync(object):
    
    def connect(self):
        #获取数据库连接
        self.DbConn = MySQLdb.Connect(
            host='123.207.246.80',
            port=3306,
            user='movie',
            passwd='movie',
            db='movie_GT',
            charset='utf8'
            )
        if self.DbConn is None:
            raise Exception,'MiaraDB连接失败'
   
        self.redis=redis.StrictRedis(host='123.207.246.80', port=6379, db=0,password='123456')
        if self.redis is None:
            raise Exception,'redis连接失败'

    
    def doSync(self):
        cursor = self.DbConn.cursor()
        try:
            sql_select = 'select ID from MOVIE'
            sql_update = 'update MOVIE set NUMBER=%s where ID=%s'            
            cursor.execute(sql_select)
            rs = cursor.fetchall()
            for row in rs:
                number = self.redis.get(row[0])
                if number is None:
                    number = 0
                sql = sql_update % (number,row[0])
                print sql
                cursor.execute(sql)
                self.DbConn.commit()
        except Exception,e:
            raise e
        finally:
            self.DbConn.close()
        
if __name__ == '__main__':
    sync = DataSync()
    print '同步脚本启动成功...'
    while True:

        print '开始同步数据'
        try:
            sync.connect()
            sync.doSync()
        except Exception,e:
            print e
        print '同步数据结束'
        time.sleep(10)
        
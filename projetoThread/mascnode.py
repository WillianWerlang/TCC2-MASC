##!/usr/bin/env python
## -*- coding: utf8 -*-
import RPi.GPIO as GPIO
#import Adafruit_GPIO as GPIO
import MFRC522
import signal
## para leitor de temperatura DHT11
import Adafruit_DHT
import time
from time import gmtime, strftime
## para chamada webService 
import requests
import json
from pprint import pprint
## para lat lon
import xml.etree.ElementTree as etree
##import pygtk
##pygtk.require("2.0")
##import gtk
import threading
import httplib
import urllib
import re
import urllib2, sys

##from BeautifulSoup import BeautifulSoup
from bs4 import BeautifulSoup

from threading import Thread
from datetime import datetime
from urllib2 import urlopen
from subprocess import call

## modulos para conexao e parseamento 
import urllib2, json 
import pymongo
from _codecs import decode
import json, re
from bson import json_util

import sys

sensor = Adafruit_DHT.DHT11
pino_sensor = 25
continue_reading = True
##markup = '<a href="http://example.com/">I linked to <i>example.com</i></a>'
##BeautifulSoup(markup, "html.parser")
GPIO.setwarnings(False)
##BeautifulSoup(markup)

global cur_mascnode
global cur_ip
global cur_dateTime
global cur_position
last_temperature = []
last_prox = []
last_rfid = [] 
global interval_DHT11
global interval_Proximity
global interval_RFID
global interval_Bmp085
global interval_LDR
global interval_Beacon
global interval_MQ7
global interval_MG81
global interval_ML8511
global interval_Flame
global interval_ADXL345 
global interval_Position 
global interval_Ip
global interval_Data
    
def set_cur_mascnode(valor):
    global cur_mascnode
    cur_mascnode = valor
    pass

def get_cur_mascnode():
    global cur_mascnode
    return cur_mascnode    

def set_cur_ip(valor):
    global cur_ip
    cur_ip = valor
    pass

def get_cur_ip():
    global cur_ip
    return cur_ip

def set_cur_dateTime(valor):
    global cur_dateTime
    cur_dateTime = valor
    pass

def get_cur_dateTime():
    global cur_dateTime
    return cur_dateTime

def set_cur_position(valor):
    global cur_position
    cur_position = valor
    pass

def get_cur_position():
    global cur_position
    return cur_position

def set_last_temperature(valor):
    global last_temperature
    last_temperature = valor
    pass

def get_last_temperature():
    global last_temperature
    return last_temperature

def set_last_prox(valor):
    global last_prox
    last_prox = valor
    pass

def get_last_prox():
    global last_prox
    return last_prox

def set_last_rfid(valor):
    global last_rfid
    last_rfid = valor
    pass

def get_last_rfid():
    global last_rfid
    return last_rfid

def set_interval_DHT11(valor):
    global interval_DHT11
    interval_DHT11 = valor
    pass

def get_interval_DHT11():
    global interval_DHT11
    return interval_DHT11 

def set_interval_Proximity(valor):
    global interval_Proximity
    interval_Proximity = valor
    pass

def get_interval_Proximity():
    global interval_Proximity
    return interval_Proximity 

def set_interval_RFID(valor):
    global interval_RFID
    interval_RFID = valor
    pass

def get_interval_RFID():
    global interval_RFID
    return interval_RFID

def set_interval_Position(valor):
    global interval_Position
    interval_Position = valor
    pass

def get_interval_Position():
    global interval_Position
    return interval_Position

def set_interval_Ip(valor):
    global interval_Ip
    interval_Ip = valor
    pass

def get_interval_Ip():
    global interval_Ip
    return interval_Ip

def set_interval_Data(valor):
    global interval_Data
    interval_Data = valor
    pass

def get_interval_Data():
    global interval_Data
    return interval_Data

## Capture SIGINT for cleanup when the script is aborted
def end_read(signal,frame):
    global continue_reading
    print "Ctrl+C captured, ending read."
    continue_reading = False
    GPIO.cleanup()
    
def loadFile():
    global data
    global my_data
    ##ler arquivo de configuração
    with open("config1.json", "rb") as f:
        # read the entire input; in a real application,
        # you would want to read a chunk at a time
        bsondata = f.read()
    
        # convert the TenGen JSON to Strict JSON
        # here, I just convert the ObjectId and Date structures,
        # but it's easy to extend to cover all structures listed at
        # http://www.mongodb.org/display/DOCS/Mongo+Extended+JSON
        jsondata = re.sub(r'ObjectId\s*\(\s*\"(\S+)\"\s*\)',
                          r'{"$oid": "\1"}',
                          bsondata)
        jsondata = re.sub(r'Date\s*\(\s*(\S+)\s*\)',
                          r'{"$date": \1}',
                          jsondata)
    
        # now we can parse this as JSON, and use MongoDB's object_hook
        # function to get rich Python data structures inside a dictionary
        data = json.loads(jsondata, object_hook=json_util.object_hook)
    
        # just print the output for demonstration, along with the type
        #print(data)
        #print(type(data))
    
        # serialise to JSON and print
        #print(json_util.dumps(data))
        
        #obj = open('config1_.json', 'wb')
        #obj.write(json_util.dumps(data))
        #obj.close
    
      
    try:
        #json_util.dumps(data)
        #my_data = json.loads(open("config1_.json").read())
        my_data=data
        ##print my_data
        #json_encoded = json.dumps(my_data)
        #print json_encoded
        ##print my_data['Sensors']['Types'][0]['DHT11']['sleep']
        set_cur_mascnode(data['MascNode'])
        set_interval_DHT11(float(my_data['Sensors']['Types'][0]['DHT11']['sleep']))
        set_interval_Proximity(float(my_data['Sensors']['Types'][1]['Proximity']['sleep']))
        set_interval_RFID(float(my_data['Sensors']['Types'][2]['RFID']['sleep']))
        set_interval_Position(float(my_data['Data']['Intervals'][0]['Position']['interval']))
        set_interval_Ip(float(my_data['Data']['Intervals'][1]['Ip']['interval']))
        set_interval_Data(float(my_data['Data']['Intervals'][2]['SendData']['interval']))
                    
    #except Exception as inst:
    except ValueError, errorLoadJson:
        print "erro json file"
        print errorLoadJson
    #print d.decode('utf-8')
    #print type(inst)     ## the exception instance
    #print inst.args      ## arguments stored in .args
    #print inst    

class positionPyhton(Thread):
    def __init__(self, v):
        Thread.__init__(self)
        self.v = v  
    def run(self):
        while True: 
            site= "http://www.yougetsignal.com/tools/network-location/php/get-network-location-json.php"
            hdr = {'User-Agent': 'Mozilla/5.0'}
            req = urllib2.Request(site,headers=hdr)
            page = urllib2.urlopen(req)
            soup = BeautifulSoup(page,"html.parser")
            set_cur_position(soup)
            ##print "==============cur_position......:",get_cur_position()
            time.sleep(get_interval_Position())
             
class ipPyhton(Thread): 
    def __init__(self, v):
        Thread.__init__(self)
        self.v = v 
    def run(self):
        while True:
            xmldoc = etree.fromstring(urlopen('http://www.speedtest.net/speedtest-config.php','r').read())
            set_cur_ip("{} Provider {}".format(xmldoc.find('client').attrib['ip'],xmldoc.find('client').attrib['isp']))
            set_cur_position("lat {} lon {}".format(xmldoc.find('client').attrib['lat'],xmldoc.find('client').attrib['lon']))
            #print "==============cur_ip............:"+get_cur_ip()
            time.sleep(get_interval_Ip())
            
         
## Welcome message

class Ultrassonic(Thread):
    def __init__(self, v):
        Thread.__init__(self)
        self.v = v    
    def run(self):
        while True:
            ##GPIO.cleanup()
            ##GPIO.setmode(GPIO.BCM)
            TRIG = 16 # 23
            ECHO = 18 # 24
            GPIO.setup(TRIG,GPIO.OUT)
            GPIO.setup(ECHO,GPIO.IN)
            ##GPIO.output(TRIG,True)
            ##GPIO.input(ECHO,True)
            ##GPIO.output(TRIG, False)
            ##print "Waiting For Sensor To Settle"
            time.sleep(get_interval_Proximity())
             
            GPIO.output(TRIG, True)
            time.sleep(0.00001)
            GPIO.output(TRIG, False)
             
            while GPIO.input(ECHO)==0:
                pulse_start = time.time()
             
            while GPIO.input(ECHO)==1:
                pulse_end = time.time()
             
            pulse_duration = pulse_end - pulse_start 
            distance = pulse_duration * 17150
            distance = round(distance, 2)
            ##print "Distancia:",distance,"cm\n"
            #distance = strftime("%S", gmtime())
            
            proxs.append(distance)
            ##print "==============last_prox",get_last_prox()
            time.sleep(get_interval_Proximity())

class Temp(Thread):
    def __init__(self, v):
        Thread.__init__(self)
        self.v = v    
    def run(self):
        #time.sleep(1)
        while True: 
            ##print "Multi 5"
            ## Efetua a leitura do sensor
            umid, temp = Adafruit_DHT.read_retry(sensor, pino_sensor);
            ## Caso leitura esteja ok, mostra os valores na tela         
            ##print ("Temperatura = {0:0.1f}  Umidade = {1:0.1f}\n").format(temp, umid);
            temperatures.append(("{0:0.1f} Humidity {1:0.1f}").format(temp, umid))
            #print "==============last_temperature..:",get_last_temperature()
            time.sleep(get_interval_DHT11()) 
            
class RR(Thread):
    def __init__(self, v):
        Thread.__init__(self)
        self.v = v    
        signal.signal(signal.SIGINT, end_read)
        MIFAREReader = MFRC522.MFRC522()
    def run(self):
        #time.sleep(1)
        while True:
            
            print "entrou"
            ## Scan for cards
            (status,TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)
        
            ## If a card is found
            if status == MIFAREReader.MI_OK:
                print "Card detected"    
            
            ## Get the UID of the card
            (status,uid) = MIFAREReader.MFRC522_Anticoll()
            ##print "veja"+str(uid)
            ## If we have the UID, continue
            if status == MIFAREReader.MI_OK:
                ## Print UID
                #print "Card read UID: "+str(uid[0])+","+str(uid[1])+","+str(uid[2])+","+str(uid[3])
                #set_last_rfid(str(uid[0])+","+str(uid[1])+","+str(uid[2])+","+str(uid[3]))
                #print "==============last_rfid"+get_last_rfid()
                ## This is the default key for authentication
                key = [0xFF,0xFF,0xFF,0xFF,0xFF,0xFF]
                
                #rfids.append(int(strftime("%s", gmtime()))/2)
                rfids.append(str(uid[0])+","+str(uid[1])+","+str(uid[2])+","+str(uid[3]))
                
                
                ## Select the scanned tag
                MIFAREReader.MFRC522_SelectTag(uid)
        
                ## Authenticate
                status = MIFAREReader.MFRC522_Auth(MIFAREReader.PICC_AUTHENT1A, 8, key, uid)
        
                ## Check if authenticated
                if status == MIFAREReader.MI_OK:
                    MIFAREReader.MFRC522_Read(8)
                    MIFAREReader.MFRC522_StopCrypto1()
                #else:
                    #print "Authentication error"                 
# set_cur_mascnode(0)
#comentar
#set_cur_ip(0)
set_cur_dateTime(datetime.now())
#comentar
#set_cur_position(0)
# set_last_temperature(99)
# set_last_prox(0)
# set_last_rfid(0)
# set_interval_DHT11(1)
# set_interval_Proximity(99)
# set_interval_RFID(1)
# set_interval_Position(99)
# set_interval_Ip(99)
# set_interval_Data(99)

loadFile()
#tempo para setar variáveis
time.sleep(2) 

GPIO.setmode(GPIO.BOARD) 

tt = Temp(1)
tt.setDaemon = True
tt.daemon=True
tt.start()

#este dá may files 
tu = Ultrassonic(1)
tu.setDaemon = True
tu.daemon=True
tu.start()

#este não precisa
# tpp = positionPyhton(1)
# tpp.setDaemon = True
# tpp.daemon=True
# tpp.start()

#este dá may files
tip = ipPyhton(1)
tip.setDaemon = True
tip.daemon=True
tip.start()
 
# trr = RR(1)
# trr.setDaemon = True
# trr.daemon=True
# trr.start()


## This loop keeps checking for chips. If one is near it will get the UID and authenticate
controlMainProgram=1
cards = []
temperatures = []
proxs = []    
rfids = []

##Comandos do RFID
## Hook the SIGINT

## Create an object of the class MFRC522


time.sleep(1)  
    
while continue_reading:
    time.sleep(1)
    controlMainProgram = controlMainProgram +1
    print "Debug Mn",get_cur_mascnode(),
    print "\nDebug DT",get_cur_dateTime().strftime('%Y/%m/%d %H:%M:%S'),
    print "\nDebug Ip",get_cur_ip(),
    print "\nDebug Po",str(get_cur_position()),
    print "\nDebug Te",temperatures,
    print "\nDebug Pr",proxs,
    print "\nDebug RI",rfids,

    set_interval_DHT11(float(my_data['Sensors']['Types'][0]['DHT11']['sleep']))
    set_interval_Proximity(float(my_data['Sensors']['Types'][1]['Proximity']['sleep']))
    set_interval_RFID(float(my_data['Sensors']['Types'][2]['RFID']['sleep']))
    set_interval_Position(float(my_data['Data']['Intervals'][0]['Position']['interval']))
    set_interval_Ip(float(my_data['Data']['Intervals'][1]['Ip']['interval']))
    set_interval_Data(float(my_data['Data']['Intervals'][2]['SendData']['interval']))
    
    print "\nDebug iDHT",get_interval_DHT11(),
    print " iPro",get_interval_Proximity(),
    print " iRFI",get_interval_RFID(),
    print " iPos",get_interval_Position(),
    print " iIP_",get_interval_Ip(),
    print " iDat",get_interval_Data()
        
    #controlMainProgram=controlMainProgram*10;
    if controlMainProgram==int(get_interval_Data()):
        set_cur_dateTime(datetime.now())
        print "\n enviar"
        
        set_last_prox(proxs)
        set_last_temperature(temperatures)
        set_last_rfid(rfids)
        
        data = {
            "Mn" : get_cur_mascnode(),
            "DT" : get_cur_dateTime().strftime('%Y/%m/%d %H:%M:%S'),
            "IP" : get_cur_ip(),
            "Po" : str(get_cur_position()),
            "Te" : get_last_temperature(),
            "Pr" : get_last_prox(),
            "RI" : get_last_rfid(),
            }
        data_json = json.dumps(data)
        headers = {'Content-type': 'application/json'}
        url = 'http://dissys-marcelot.rhcloud.com/rest/post'
        response = requests.post(url, data=data_json, headers=headers)
        #close()
        continue_reading = True
        controlMainProgram = 1
        
        print "dados enviados",data_json;
        
        proxs = []
        temperatures = []
        rfids = []
        
        #GPIO.cleanup()
        
        try:
            f = open('myfile.json','w')
            f.write(response.json) # python will convert \n to os.linesep
            f.close() 
            #f = open('data.json', 'w')
            #f.write(json.dump(response.json))
            
        #with open('data.json', 'w') as outfile:
        #        json.dump(response.json, outfile)
                  
        except Exception as inst:
            print type(inst) 

        #print "\nresposta",response.json
        print "\nresposta",str(response)
        
        loadFile()
        
        
    #sys.exit(1)   
     
    print "\n ---------->controlMainProgram=",controlMainProgram,"\n"    
    #print "\n"                
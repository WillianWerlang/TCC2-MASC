import threading
from threading import Thread
import httplib
import urllib
import re
import urllib2, sys
from bs4 import BeautifulSoup

site= "http://www.yougetsignal.com/tools/network-location/php/get-network-location-json.php"
hdr = {'User-Agent': 'Mozilla/5.0'}
req = urllib2.Request(site,headers=hdr)
page = urllib2.urlopen(req)
soup = BeautifulSoup(page,"html.parser")
#set_cur_position(soup)
print "==============cur_position......:",soup
#time.sleep(get_interval_Position()) 
            

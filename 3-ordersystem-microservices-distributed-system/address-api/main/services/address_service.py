# -*- coding: utf-8 -*-
"""

AddressService class - This class holds the method related to Address manipulations.

"""

from typing import get_origin
from main.models.address import Address

from cassandra.cluster import Cluster
from cassandra.auth import PlainTextAuthProvider

class AddressService():
    __model__ = Address

    def __init__(self):
        # Creating a parent class ref to access parent class methods.
        self.parentClassRef = super(AddressService, self)
    
    def all(self):
        cloud_config = {
            'secure_connect_bundle': '/path/to/secure-connect-dbname.zip'
        }
        auth_provider = PlainTextAuthProvider(username='cassandra', password='cassandra')
        cluster = Cluster(auth_provider=auth_provider)
        session = cluster.connect()
        session.set_keyspace('ebank_data')
        # or you can do this instead
        session.execute('USE ebank_data')
        rows = session.execute('SELECT  id, streetaddress, postalcode, city, stateprovince, country, created, gpslatlong FROM address')
        
        addressList = []
        for user_row in rows:
            #print ("id, streetaddress, postalcode, city, stateprovince, country, created, gpslatlong")
            #print (user_row.id, user_row.streetaddress, user_row.postalcode, user_row.city, user_row.stateprovince, user_row.country, user_row.created, user_row.gpslatlong)
            addressList.append({"id": user_row.id, "streetaddress": user_row.streetaddress, "postalcode": user_row.postalcode, "city": user_row.city, "stateprovince": user_row.stateprovince, "country": user_row.country, "created": user_row.created, "gpslatlong": user_row.gpslatlong})
        #addressTuple = tuple(addressList)
        #print(addressTuple)
        return addressList

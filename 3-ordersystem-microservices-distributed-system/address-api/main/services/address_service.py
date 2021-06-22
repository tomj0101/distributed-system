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
    
    def all():
        cloud_config = {
            'secure_connect_bundle': '/path/to/secure-connect-dbname.zip'
        }
        auth_provider = PlainTextAuthProvider(username='cassandra', password='cassandra')
        cluster = Cluster(auth_provider=auth_provider)
        session = cluster.connect()
        session.set_keyspace('ebank_data')
        # or you can do this instead
        session.execute('USE ebank_data')
        rows = session.execute('SELECT  id, streetAddress, postalCode, city, stateProvince FROM address')
        for user_row in rows:
            print (user_row.id, user_row.streetAddress, user_row.postalCode, user_row.city, user_row.stateProvince) 

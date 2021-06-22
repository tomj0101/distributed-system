# -*- coding: utf-8 -*-
class Address():
    """Model for address table"""
    id = ""
    streetAddress = ""
    postalCode = ""
    city = ""
    stateProvince = ""
    country = ""
    created = ""
    gpsLatLong = ""

    def __init__(self, id, streetAddress, postalCode, city, stateProvince, country, created, gpsLatLong):
        super().__init__()
        self.id = id
        self.streetAddress = streetAddress
        self.postalCode = postalCode
        self.city = city
        self.stateProvince = stateProvince
        self.country = country
        self.created = created
        self.gpsLatLong = gpsLatLong

# -*- coding: utf-8 -*-
class Address():
    """Model for address table"""
    id = ""
    streetAddress = ""
    postalCode = ""
    city = ""
    stateProvince = ""

    def __init__(self, id, streetAddress, postalCode, city, stateProvince):
        super().__init__()
        self.id = id
        self.streetAddress = streetAddress
        self.postalCode = postalCode
        self.city = city
        self.stateProvince = stateProvince

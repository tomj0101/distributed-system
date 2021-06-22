# -*- coding: utf-8 -*-
"""
    file: settings.py
    notes:  Configure Settings for application
    - In a real project use yaml files or properties files!
"""

import os

class Config(object):
    """ Common config options """
    APPNAME = 'Address_API'
    SUPPORT_EMAIL = 'tomj0101'
    VERSION = '0.3.0'
    APPID = 'address_api_docker'
    SECRET_KEY = os.urandom(24)
    TESTING = False
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    SQLALCHEMY_NATIVE_UNICODE = True
    SQLALCHEMY_COMMIT_ON_TEARDOWN = True
    DB_NAME = os.getenv('HERE')
    DB_USER = os.getenv('HERW')
    DB_PASS = os.getenv('HERE')
    DB_SERVICE = os.getenv('HERE')
    DB_PORT = os.getenv('DB_PORT')

class DevelopmentConfig(Config):
    """ Dev environment config options """
    FLASK_ENV='development'
    DEBUG = True
    PROFILE = True

class TestingConfig(Config):
    """ Testing environment config options """
    DEBUG = False
    STAGING = True
    TESTING = True


class ProductionConfig(Config):
    """ Prod environment config options """
    FLASK_ENV = 'production'
    DEBUG = False
    STAGING = False


config = {
    'development': DevelopmentConfig,
    'testing': TestingConfig,
    'production': ProductionConfig,
    'default': DevelopmentConfig
}

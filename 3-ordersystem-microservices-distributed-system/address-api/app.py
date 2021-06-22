'''
Address API
REST Method:
    POST    /api/addresses
    PUT     /api/addresses/:id
    PATCH   /api/addresses/:id
    GET     /api/addresses
    GET     /api/addresses/:id
    DELETE  /api/addresses/:id
    GET  /api/_search/addresses?query=:query

    flask run
'''
# -*- coding: utf-8 -*-
from flask import Flask, Blueprint, jsonify, request
from main.services.address_service import AddressService
address_service = AddressService()

#route = Blueprint('user', __name__)
app = Flask(__name__)

# healhcheck
@app.route("/api")
def hello():
    return "Running Address API!!"

@app.route("/api/ping")
def ping():
    return jsonify({"status": 200, "msg":"This message is coming from Address API!"})

# address api
@app.route("/api/addresses", methods=["POST"])
def post_addresses():
    return jsonify({"status": 200, "msg":"POST to Address API!"})

# PUT and PATCH are not the same, but for the lab I will putting both here!
@app.route("/api/addresses/<id>", methods=["PUT","PATCH"])
def put_and_patch_addresses(id):
    if request.method == "PUT":
        print("PUT... put code here")
    if request.method == "PATCH":
        print("PATCH... patch code here")
    return jsonify({"status": 200, "msg":"PUT and PATCH to Address API!", "id":id})

# the only one with realcode
@app.route("/api/addresses", methods=["GET"])
def get_all_addresses():
    address = address_service.all()
    return jsonify(address)

@app.route("/api/addresses/<id>", methods=["GET"])
def get_addresses_by_id(id):
    return jsonify({"status": 200, "msg":"GET to Address API!", "id":id})

@app.route("/api/addresses/<id>", methods=["DELETE"])
def delete_addresses(id):
    return jsonify({"status": 200, "msg":"DELETE to Address API!", "id":id})

@app.route("/api/_search/addresses", methods=["GET"])
def search_addresses(id):
    return jsonify({"status": 200, "msg":"SEARCH to Address API!", "id":id})

if __name__ == "__main__":
    app.run(host='0.0.0.0')
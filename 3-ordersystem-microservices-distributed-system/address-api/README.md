# addressapi
address api: develop with Python + Cassandra.

## Run in Dev

To start your application in the dev profile, run:

```
flask run
Open http://localhost:5000/api
```

## Run in Prod mode
uWSGI

uWSGI is a fast application server written in C. It is very configurable which makes it more complicated to setup than gunicorn.

Running uWSGI HTTP Router:
```
uwsgi --socket 0.0.0.0:5000 --protocol=http -w wsgi:app
--- OR ---
./entrypoint.sh

Open http://localhost:5000/api
```

### Python3 init install for prod server
```
sudo apt install nginx
sudo systemctl disable nginx # Just do this in you dev machine for avoid auto-start

sudo apt install python3-pip python3-dev build-essential libssl-dev libffi-dev python3-setuptools

pip3 install virtualenv

python3 -m virtualenv addressapienv
source addressapienv/bin/activate
pip install wheel
deactivate
```
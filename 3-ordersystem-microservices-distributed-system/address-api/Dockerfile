FROM python:3.6.2

MAINTAINER Tom "Tomj0101"

ENV PYTHONUNBUFFERED 1

RUN mkdir -p /usr/src/app

# Update working directory
WORKDIR /usr/src/app

# copy everything from this directory to server/flask docker container
COPY . /usr/src/app/

# Give execute permission to below file, so that the script can be executed by docker.
RUN chmod 777 /usr/src/app/entrypoint.sh

# Install the Python libraries
RUN pip install --no-cache-dir -r requirements.txt

EXPOSE 8081

# run server
CMD ["./entrypoint.sh"]